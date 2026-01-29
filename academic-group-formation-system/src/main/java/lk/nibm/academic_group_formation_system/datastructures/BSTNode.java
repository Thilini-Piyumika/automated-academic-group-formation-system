package lk.nibm.academic_group_formation_system.datastructures;

import lk.nibm.academic_group_formation_system.model.Student;

public class BSTNode {
    Student student;
    BSTNode left;
    BSTNode right;

    public BSTNode(Student student) {
        this.student = student;
    }
}
