package com.nibm.academic_group_formation_tool.service;

import com.nibm.academic_group_formation_tool.model.Student;

public class BSTNode {
    Student student;
    BSTNode left, right;

    public BSTNode(Student s) {
        this.student = s;
    }
}
