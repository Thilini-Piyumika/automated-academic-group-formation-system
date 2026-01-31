package lk.nibm.academic_group_formation_system.datastructures;

import lk.nibm.academic_group_formation_system.model.Student;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BST {

    private BSTNode root;

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private BSTNode insertRec(BSTNode node, Student student) {
        if (node == null) return new BSTNode(student);

        if (student.getReadinessScore() < node.student.getReadinessScore())
            node.left = insertRec(node.left, student);
        else
            node.right = insertRec(node.right, student);

        return node;
    }

    public List<Student> inOrderTraversal() {
        List<Student> list = new ArrayList<>();
        traverse(root, list);
        return list;
    }

    private void traverse(BSTNode node, List<Student> list) {
        if (node != null) {
            traverse(node.left, list);
            list.add(node.student);
            traverse(node.right, list);
        }
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
}
