package com.nibm.academic_group_formation_tool.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.nibm.academic_group_formation_tool.model.Student;

@Service
public class GroupService {

    public List<List<Student>> createGroups(List<Student> students,
                                            int groupSize,
                                            double threshold,
                                            double w1,
                                            double w2,
                                            double w3,
                                            int strategy) {

        // STEP 1 — Calculate readiness score + category for ALL students
        for (Student s : students) {

            // Convert attendance (0–100) to GPA scale (0–4)
            double attendanceGPA = (s.getAttendance() / 100.0) * 4.0;

            double totalWeight = w1 + w2 + w3;
            double score = 0;

            if (totalWeight > 0) {
                score =
                        (
                                (attendanceGPA * w1) +
                                        (s.getThisYearGpa() * w2) +
                                        (s.getPreviousYearGpa() * w3)
                        ) / totalWeight;
            }

            s.setReadinessScore(score);

            // GPA-style categories
            if (score >= 3.5)
                s.setCategory("BEST");
            else if (score >= 2.5)
                s.setCategory("AVERAGE");
            else
                s.setCategory("NEEDS_SUPPORT");
        }

        // STEP 2 — Split into Eligible Queue & Review Queue (by attendance)
        List<Student> eligibleQueue = new ArrayList<>();
        List<Student> reviewQueue = new ArrayList<>();

        for (Student s : students) {
            if (s.getAttendance() >= threshold)
                eligibleQueue.add(s);
            else
                reviewQueue.add(s);
        }

        // STEP 3 — Build BST using ONLY eligible students (key = readinessScore)
        BSTNode root = null;
        for (Student s : eligibleQueue) {
            root = insert(root, s);
        }

        // STEP 4 — Sort eligible students from highest → lowest score
        List<Student> sorted = new ArrayList<>();
        reverseInorder(root, sorted);

        // STEP 5 — Split sorted eligible students by category
        List<Student> best = new ArrayList<>();
        List<Student> average = new ArrayList<>();
        List<Student> worst = new ArrayList<>();

        for (Student s : sorted) {
            if ("BEST".equals(s.getCategory()))
                best.add(s);
            else if ("AVERAGE".equals(s.getCategory()))
                average.add(s);
            else
                worst.add(s);
        }

        // STEP 6 — Apply chosen strategy
        List<List<Student>> groups;

        if (strategy == 1) {
            groups = bestBestStrategy(best, average, worst, groupSize);
        } else if (strategy == 2) {
            groups = bestAverageStrategy(best, average, worst, groupSize);
        } else {
            groups = mixedStrategy(best, average, worst, groupSize);
        }

        // STEP 7 — Add review queue as LAST single group
        if (!reviewQueue.isEmpty()) {
            groups.add(reviewQueue);
        }

        return groups;
    }

    // ==============================
    // STRATEGY 1 — Best–Best (Homogeneous)
    // BEST→BEST, then AVERAGE, then WORST
    // ==============================
    private List<List<Student>> bestBestStrategy(List<Student> best,
                                                 List<Student> average,
                                                 List<Student> worst,
                                                 int groupSize) {

        List<List<Student>> groups = new ArrayList<>();
        List<Student> pool = new ArrayList<>();

        // Priority order: BEST first, then AVERAGE, then WORST
        pool.addAll(best);
        pool.addAll(average);
        pool.addAll(worst);

        List<Student> current = new ArrayList<>();

        for (Student s : pool) {
            current.add(s);
            if (current.size() == groupSize) {
                groups.add(new ArrayList<>(current));
                current.clear();
            }
        }

        if (!current.isEmpty())
            groups.add(current);

        return groups;
    }

    // ==============================
    // STRATEGY 2 — Best–Average (Semi-balanced)
    // Try BEST+AVERAGE first, fallback to WORST
    // ==============================
    private List<List<Student>> bestAverageStrategy(
            List<Student> best,
            List<Student> average,
            List<Student> worst,
            int groupSize) {

        List<List<Student>> groups = new ArrayList<>();

        while (!best.isEmpty() || !average.isEmpty() || !worst.isEmpty()) {

            List<Student> group = new ArrayList<>();

            while (group.size() < groupSize &&
                    (!best.isEmpty() || !average.isEmpty() || !worst.isEmpty())) {

                // 1st priority → BEST
                if (!best.isEmpty() && group.size() < groupSize)
                    group.add(best.remove(0));

                // 2nd priority → AVERAGE
                if (!average.isEmpty() && group.size() < groupSize)
                    group.add(average.remove(0));

                // 3rd priority → BEST again
                if (!best.isEmpty() && group.size() < groupSize)
                    group.add(best.remove(0));

                // 4th priority → AVERAGE again
                if (!average.isEmpty() && group.size() < groupSize)
                    group.add(average.remove(0));

                // Last fallback → WORST
                if (!worst.isEmpty() && group.size() < groupSize)
                    group.add(worst.remove(0));
            }

            groups.add(group);
        }

        return groups;
    }

    // ==============================
    // STRATEGY 3 — MIXED (Balanced)
    // 1 BEST + 1 AVERAGE + 1 WORST + 1 ANY
    // ==============================
    private List<List<Student>> mixedStrategy(List<Student> best,
                                              List<Student> average,
                                              List<Student> worst,
                                              int groupSize) {

        List<List<Student>> groups = new ArrayList<>();

        while (!best.isEmpty() || !average.isEmpty() || !worst.isEmpty()) {

            List<Student> group = new ArrayList<>();

            // 1 BEST
            if (!best.isEmpty() && group.size() < groupSize)
                group.add(best.remove(0));

            // 1 AVERAGE
            if (!average.isEmpty() && group.size() < groupSize)
                group.add(average.remove(0));

            // 1 WORST
            if (!worst.isEmpty() && group.size() < groupSize)
                group.add(worst.remove(0));

            // Fill remaining slots with ANY available students
            while (group.size() < groupSize &&
                    (!best.isEmpty() || !average.isEmpty() || !worst.isEmpty())) {

                if (!best.isEmpty())
                    group.add(best.remove(0));
                else if (!average.isEmpty())
                    group.add(average.remove(0));
                else
                    group.add(worst.remove(0));
            }

            groups.add(group);
        }

        return groups;
    }

    // ==============================
    // BST INSERT
    // ==============================
    private BSTNode insert(BSTNode root, Student s) {
        if (root == null) return new BSTNode(s);

        if (s.getReadinessScore() < root.student.getReadinessScore())
            root.left = insert(root.left, s);
        else
            root.right = insert(root.right, s);

        return root;
    }

    // ==============================
    // REVERSE INORDER = Highest → Lowest
    // ==============================
    private void reverseInorder(BSTNode root, List<Student> list) {
        if (root == null) return;

        reverseInorder(root.right, list);
        list.add(root.student);
        reverseInorder(root.left, list);
    }
}
