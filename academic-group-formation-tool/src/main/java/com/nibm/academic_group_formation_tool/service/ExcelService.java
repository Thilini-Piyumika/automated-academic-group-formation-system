package com.nibm.academic_group_formation_tool.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nibm.academic_group_formation_tool.model.Student;

@Service
public class ExcelService {

    public List<Student> readExcel(InputStream inputStream) throws Exception {
        List<Student> students = new ArrayList<>();

        Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        // Row 0 = headers, start from 1
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String id = row.getCell(0).getStringCellValue();
            double attendance = row.getCell(1).getNumericCellValue();
            double thisYearGpa = row.getCell(2).getNumericCellValue();
            double previousYearGpa = row.getCell(3).getNumericCellValue();

            students.add(new Student(id, attendance, thisYearGpa, previousYearGpa));
        }

        workbook.close();
        return students;
    }
}
