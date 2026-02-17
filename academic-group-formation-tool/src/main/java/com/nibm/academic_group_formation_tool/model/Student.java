package com.nibm.academic_group_formation_tool.model;

public class Student {
    private String studentId;
    private double attendance;
    private double thisYearGpa;
    private double previousYearGpa;
    private double readinessScore;
    private String category;

    public Student(String studentId, double attendance, double thisYearGpa, double previousYearGpa) {
        this.studentId = studentId;
        this.attendance = attendance;
        this.thisYearGpa = thisYearGpa;
        this.previousYearGpa = previousYearGpa;
    }

    public String getStudentId() { return studentId; }
    public double getAttendance() { return attendance; }
    public double getThisYearGpa() { return thisYearGpa; }
    public double getPreviousYearGpa() { return previousYearGpa; }

    public void setReadinessScore(double score) {
        this.readinessScore = score;
    }

    public double getReadinessScore() {
        return readinessScore;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

