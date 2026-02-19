package com.nibm.academic_group_formation_tool.controller;

import com.nibm.academic_group_formation_tool.service.ExcelService;
import com.nibm.academic_group_formation_tool.service.GroupService;
import com.nibm.academic_group_formation_tool.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.nibm.academic_group_formation_tool.model.Student;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ExcelExportService exportService;

    @PostMapping("/upload")
    public LinkedList<LinkedList<Student>> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("size") int size,
            @RequestParam("threshold") double threshold,
            @RequestParam("w1") double w1,
            @RequestParam("w2") double w2,
            @RequestParam("w3") double w3,
            @RequestParam("strategy") int strategy) throws Exception {

        List<Student> students = excelService.readExcel(file.getInputStream());
        return groupService.createGroups(students, size, threshold, w1, w2, w3, strategy);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel(@RequestParam("detailed") boolean detailed) throws Exception {

        byte[] data = exportService.exportGroups(
                groupService.getLastGroups(),
                groupService.getW1(),
                groupService.getW2(),
                groupService.getW3(),
                groupService.getThreshold(),
                groupService.getStrategy(),
                groupService.getGroupSize(),
                groupService.getTotalStudents(),
                groupService.getEligibleStudents(),
                groupService.getReviewStudents(),
                detailed
        );

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=Groups.xlsx")
                .header("Content-Type", "application/octet-stream")
                .body(data);
    }
}


