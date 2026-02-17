package lk.nibm.academic_group_formation_system.service;

import lk.nibm.academic_group_formation_system.datastructures.BST;
import lk.nibm.academic_group_formation_system.datastructures.CollaborationGraph;
import lk.nibm.academic_group_formation_system.model.GroupingStrategy;
import lk.nibm.academic_group_formation_system.model.LeadershipPreference;
import lk.nibm.academic_group_formation_system.model.Student;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class GroupingService {

    private final CollaborationGraph graph = new CollaborationGraph();

    // ==============================
    // READ STUDENTS FROM EXCEL
    // ==============================
    public List<Student> readStudentsFromExcel(MultipartFile file) {

        List<Student> students = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                // Skip header row
                if (row.getRowNum() == 0) continue;

                if (row.getCell(0) == null) continue;

                String id = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                double attendance = row.getCell(2).getNumericCellValue();
                double currentGpa = row.getCell(3).getNumericCellValue();
                double previousGpa = row.getCell(4).getNumericCellValue();

                String leadershipText = row.getCell(5).getStringCellValue().trim();
                LeadershipPreference leadership =
                        LeadershipPreference.valueOf(leadershipText);

                students.add(new Student(
                        id,
                        name,
                        attendance,
                        currentGpa,
                        previousGpa,
                        leadership
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    // ==============================
    // MAIN GROUP CREATION LOGIC
    // ==============================
    public Map<String, List<Student>> createGroups(
            List<Student> students,
            int groupSize,
            int repetitionThreshold,
            GroupingStrategy strategy) {

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

        Map<String, List<Student>> groups = new LinkedHashMap<>();
        int groupNumber = 1;

        while (bestFit.size() + averageFit.size() + needsSupport.size() >= groupSize) {

            List<Student> group = formGroupByStrategy(
                    strategy, groupSize, bestFit, averageFit, needsSupport);

            if (group.size() < groupSize) break;

            List<String> ids = extractIds(group);

            while (graph.violatesThreshold(ids, repetitionThreshold)) {

                Student removed = group.remove(group.size() - 1);

                switch (removed.getCategory()) {
                    case "Best Fit" -> bestFit.addFirst(removed);
                    case "Average Fit" -> averageFit.addFirst(removed);
                    case "Needs Support" -> needsSupport.addFirst(removed);
                }

                group = formGroupByStrategy(
                        strategy, groupSize, bestFit, averageFit, needsSupport);
                ids = extractIds(group);
            }

            graph.updateGraph(ids);
            groups.put("Group " + groupNumber++, group);
        }

        return groups;
    }

    // ==============================
    // HELPER METHODS
    // ==============================

    private List<String> extractIds(List<Student> group) {
        List<String> ids = new ArrayList<>();
        for (Student s : group) {
            ids.add(s.getId());
        }
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
