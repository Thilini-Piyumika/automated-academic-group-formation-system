package com.nibm.academic_group_formation_tool.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.nibm.academic_group_formation_tool.model.Student;

@Service
public class GroupService {

    private LinkedList<LinkedList<Student>> lastGeneratedGroups;

    private double lastW1, lastW2, lastW3, lastThreshold;
    private int lastStrategy, lastGroupSize;

    // NEW: totals
    private int lastTotalStudents;
    private int lastEligibleStudents;
    private int lastReviewStudents;

    public LinkedList<LinkedList<Student>> createGroups(List<Student> students,
                                                        int groupSize,
                                                        double threshold,
                                                        double w1,
                                                        double w2,
                                                        double w3,
                                                        int strategy) {

        lastW1 = w1;
        lastW2 = w2;
        lastW3 = w3;
        lastThreshold = threshold;
        lastStrategy = strategy;
        lastGroupSize = groupSize;

        // READINESS SCORE
        for (Student s : students) {
            double attendanceGPA = (s.getAttendance() / 100.0) * 4.0;
            double totalWeight = w1 + w2 + w3;

            double score = ((attendanceGPA * w1) +
                    (s.getThisYearGpa() * w2) +
                    (s.getPreviousYearGpa() * w3)) / totalWeight;

            score = Math.round(score * 100.0) / 100.0;
            s.setReadinessScore(score);

            if (score >= 3.5)
                s.setCategory("BEST");
            else if (score >= 2.5)
                s.setCategory("AVERAGE");
            else
                s.setCategory("NEEDS_SUPPORT");
        }

        List<Student> eligible = new ArrayList<>();
        List<Student> review = new ArrayList<>();

        for (Student s : students) {
            if (s.getAttendance() >= threshold)
                eligible.add(s);
            else
                review.add(s);
        }

        // SAVE TOTALS
        lastTotalStudents = students.size();
        lastEligibleStudents = eligible.size();
        lastReviewStudents = review.size();

        // SORT BY SCORE DESC
        eligible.sort((a, b) ->
                Double.compare(b.getReadinessScore(), a.getReadinessScore()));

        List<Student> best = new ArrayList<>();
        List<Student> avg = new ArrayList<>();
        List<Student> worst = new ArrayList<>();

        for (Student s : eligible) {
            if (s.getCategory().equals("BEST")) best.add(s);
            else if (s.getCategory().equals("AVERAGE")) avg.add(s);
            else worst.add(s);
        }

        LinkedList<LinkedList<Student>> groups;

        if (strategy == 1)
            groups = bestBest(best, avg, worst, groupSize);
        else if (strategy == 2)
            groups = bestAverage(best, avg, worst, groupSize);
        else
            groups = mixed(best, avg, worst, groupSize);

        if (!review.isEmpty())
            groups.add(new LinkedList<>(review));

        lastGeneratedGroups = groups;
        return groups;
    }

    public LinkedList<LinkedList<Student>> getLastGroups() { return lastGeneratedGroups; }
    public double getW1() { return lastW1; }
    public double getW2() { return lastW2; }
    public double getW3() { return lastW3; }
    public double getThreshold() { return lastThreshold; }
    public int getStrategy() { return lastStrategy; }
    public int getGroupSize() { return lastGroupSize; }

    // NEW getters for totals
    public int getTotalStudents() { return lastTotalStudents; }
    public int getEligibleStudents() { return lastEligibleStudents; }
    public int getReviewStudents() { return lastReviewStudents; }

    // STRATEGY 1
    private LinkedList<LinkedList<Student>> bestBest(List<Student> best, List<Student> avg, List<Student> worst, int size) {
        LinkedList<LinkedList<Student>> groups = new LinkedList<>();
        List<Student> pool = new ArrayList<>();
        pool.addAll(best);
        pool.addAll(avg);
        pool.addAll(worst);

        LinkedList<Student> current = new LinkedList<>();

        for (Student s : pool) {
            current.add(s);
            if (current.size() == size) {
                groups.add(new LinkedList<>(current));
                current.clear();
            }
        }

        if (!current.isEmpty()) groups.add(current);
        return groups;
    }

    private LinkedList<LinkedList<Student>> bestAverage(List<Student> best,
                                                        List<Student> avg,
                                                        List<Student> worst,
                                                        int size) {

        LinkedList<LinkedList<Student>> groups = new LinkedList<>();

        while (!best.isEmpty() || !avg.isEmpty() || !worst.isEmpty()) {

            LinkedList<Student> g = new LinkedList<>();

            while (g.size() < size &&
                    (!best.isEmpty() || !avg.isEmpty() || !worst.isEmpty())) {

                if (!best.isEmpty() && !avg.isEmpty()) {
                    g.add(best.remove(0));
                    if (g.size() < size) g.add(avg.remove(0));
                }
                else if (!best.isEmpty() && !worst.isEmpty()) {
                    g.add(best.remove(0));
                    if (g.size() < size) g.add(worst.remove(0));
                }
                else if (!avg.isEmpty() && !worst.isEmpty()) {
                    g.add(avg.remove(0));
                    if (g.size() < size) g.add(worst.remove(0));
                }
                else if (!best.isEmpty()) g.add(best.remove(0));
                else if (!avg.isEmpty()) g.add(avg.remove(0));
                else if (!worst.isEmpty()) g.add(worst.remove(0));
            }

            groups.add(g);
        }

        return groups;
    }

    // STRATEGY 3
    private LinkedList<LinkedList<Student>> mixed(List<Student> best, List<Student> avg, List<Student> worst, int size) {
        LinkedList<LinkedList<Student>> groups = new LinkedList<>();

        while (!best.isEmpty() || !avg.isEmpty() || !worst.isEmpty()) {
            LinkedList<Student> g = new LinkedList<>();

            if (!best.isEmpty()) g.add(best.remove(0));
            if (!avg.isEmpty()) g.add(avg.remove(0));
            if (!worst.isEmpty()) g.add(worst.remove(0));

            while (g.size() < size) {
                if (!best.isEmpty()) g.add(best.remove(0));
                else if (!avg.isEmpty()) g.add(avg.remove(0));
                else if (!worst.isEmpty()) g.add(worst.remove(0));
                else break;
            }

            groups.add(g);
        }

        return groups;
    }
}
