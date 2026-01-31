package lk.nibm.academic_group_formation_system.service;

import lk.nibm.academic_group_formation_system.datastructures.BST;
import lk.nibm.academic_group_formation_system.datastructures.CollaborationGraph;
import lk.nibm.academic_group_formation_system.model.GroupingStrategy;
import lk.nibm.academic_group_formation_system.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupingService {

    private final CollaborationGraph graph = new CollaborationGraph();

    public Map<String, List<Student>> createGroups(List<Student> students,
                                                   int groupSize,
                                                   int repetitionThreshold) {

        // 1. Calculate readiness score
        for (Student s : students) {
            double score =
                    (s.getAttendance() * 0.4) +
                            (s.getCurrentGpa() * 0.3) +
                            (s.getPreviousGpa() * 0.3);
            s.setReadinessScore(score);
        }

        // 2. Rank using BST
        BST bst = new BST();
        students.forEach(bst::insert);
        List<Student> sorted = bst.inOrderTraversal();

        // 3. Categorize
        int n = sorted.size();
        for (int i = 0; i < n; i++) {
            if (i < n / 3) sorted.get(i).setCategory("Needs Support");
            else if (i < 2 * n / 3) sorted.get(i).setCategory("Average Fit");
            else sorted.get(i).setCategory("Best Fit");
        }

        LinkedList<Student> bestFit = new LinkedList<>();
        LinkedList<Student> averageFit = new LinkedList<>();
        LinkedList<Student> needsSupport = new LinkedList<>();

        for (Student s : sorted) {
            switch (s.getCategory()) {
                case "Best Fit" -> bestFit.add(s);
                case "Average Fit" -> averageFit.add(s);
                case "Needs Support" -> needsSupport.add(s);
            }
        }

        // 4. Create groups
        Map<String, List<Student>> groups = new LinkedHashMap<>();
        LinkedList<Student> pool = new LinkedList<>(sorted);

        int groupNumber = 1;

        while (pool.size() >= groupSize) {
            List<Student> group = new ArrayList<>();

            while (group.size() < groupSize) {
                group.add(pool.removeLast()); // take high-ranked first
            }

            // repetition check
            List<String> ids = extractIds(group);
            while (graph.violatesThreshold(ids, repetitionThreshold)) {
                Student removed = group.remove(group.size() - 1);
                pool.addFirst(removed);
                group.add(pool.removeLast());
                ids = extractIds(group);
            }

            graph.updateGraph(ids);
            groups.put("Group " + groupNumber++, group);
        }

        return groups;
    }

    private List<String> extractIds(List<Student> group) {
        List<String> ids = new ArrayList<>();
        for (Student s : group) ids.add(s.getId());
        return ids;
    }

    private List<Student> formGroupByStrategy(
            GroupingStrategy strategy,
            int groupSize,
            LinkedList<Student> best,
            LinkedList<Student> average,
            LinkedList<Student> needs) {

        List<Student> group = new ArrayList<>();

        switch (strategy) {

            case BEST_BEST -> {
                while (group.size() < groupSize && !best.isEmpty()) {
                    group.add(best.removeLast());
                }
            }

            case BEST_AVERAGE -> {
                if (!best.isEmpty()) group.add(best.removeLast());
                while (group.size() < groupSize && !average.isEmpty()) {
                    group.add(average.removeLast());
                }
            }

            case AVERAGE_NEEDS_SUPPORT -> {
                if (!average.isEmpty()) group.add(average.removeLast());
                while (group.size() < groupSize && !needs.isEmpty()) {
                    group.add(needs.removeLast());
                }
            }

            case NEEDS_SUPPORT_ONLY -> {
                while (group.size() < groupSize && !needs.isEmpty()) {
                    group.add(needs.removeLast());
                }
            }
        }

        return group;
    }
}

