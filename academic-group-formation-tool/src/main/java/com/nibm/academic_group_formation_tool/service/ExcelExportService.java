package com.nibm.academic_group_formation_tool.service;

import com.nibm.academic_group_formation_tool.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

@Service
public class ExcelExportService {

    public byte[] exportGroups(LinkedList<LinkedList<Student>> groups,
                               double w1, double w2, double w3,
                               double threshold, int strategy, int size,
                               int totalStudents, int eligibleStudents, int reviewStudents,
                               boolean detailed) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Groups");

        int rowNum = 0;

        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Group");
        header.createCell(1).setCellValue("StudentID");

        if (detailed) {
            header.createCell(2).setCellValue("Attendance");
            header.createCell(3).setCellValue("GPA");
            header.createCell(4).setCellValue("Prev GPA");
            header.createCell(5).setCellValue("Score");
            header.createCell(6).setCellValue("Category");
        }

        int gIndex = 1;
        int totalGroups = groups.size();
        boolean hasReviewGroup = reviewStudents > 0;

        for (int i = 0; i < groups.size(); i++) {

            LinkedList<Student> group = groups.get(i);

            String groupName;

            // Only mark last group as Review Group if review exists
            if (hasReviewGroup && i == totalGroups - 1) {
                groupName = "Review Group";
            } else {
                groupName = "Group " + gIndex;
            }

            for (Student s : group) {
                Row r = sheet.createRow(rowNum++);
                r.createCell(0).setCellValue(groupName);
                r.createCell(1).setCellValue(s.getStudentId());

                if (detailed) {
                    r.createCell(2).setCellValue(s.getAttendance());
                    r.createCell(3).setCellValue(s.getThisYearGpa());
                    r.createCell(4).setCellValue(s.getPreviousYearGpa());
                    r.createCell(5).setCellValue(s.getReadinessScore());
                    r.createCell(6).setCellValue(s.getCategory());
                }
            }

            rowNum++;
            gIndex++;
        }

        // MODEL DETAILS
        Sheet details = workbook.createSheet("Model_Details");
        int r = 0;


        details.createRow(r++).createCell(0).setCellValue("Group Size: " + size);
        details.createRow(r++).createCell(0).setCellValue("Attendance Threshold: " + threshold + "%");

        details.createRow(r++).createCell(0).setCellValue("Total Students: " + totalStudents);
        details.createRow(r++).createCell(0).setCellValue("Eligible Students: " + eligibleStudents);
        details.createRow(r++).createCell(0).setCellValue("Review Students: " + reviewStudents);

        r++;

        details.createRow(r++).createCell(0).setCellValue("Attendance Weight (W1): " + w1);
        details.createRow(r++).createCell(0).setCellValue("Current GPA Weight (W2): " + w2);
        details.createRow(r++).createCell(0).setCellValue("Previous GPA Weight (W3): " + w3);

        r++;

        if (strategy == 1) {
            details.createRow(r++).createCell(0).setCellValue("Strategy 1 – Best–Best (Homogeneous)");
            details.createRow(r++).createCell(0).setCellValue("Description: BEST+BEST → AVERAGE+AVERAGE → WORST+WORST");
        } else if (strategy == 2) {
            details.createRow(r++).createCell(0).setCellValue("Strategy 2 – Best–Average (Semi Balanced)");
            details.createRow(r++).createCell(0).setCellValue("Description: BEST+AVG → AVG+WORST");
        } else {
            details.createRow(r++).createCell(0).setCellValue("Strategy 3 – Mixed (Balanced)");
            details.createRow(r++).createCell(0).setCellValue("Description: BEST+AVG+WORST");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}
