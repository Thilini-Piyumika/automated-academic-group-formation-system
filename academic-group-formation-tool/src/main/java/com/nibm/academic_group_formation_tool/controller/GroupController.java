package com.nibm.academic_group_formation_tool.controller;

import com.nibm.academic_group_formation_tool.service.ExcelService;
import com.nibm.academic_group_formation_tool.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.nibm.academic_group_formation_tool.model.Student;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private GroupService groupService;

    @PostMapping("/upload")
    public List<List<Student>> uploadExcel(
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
}

