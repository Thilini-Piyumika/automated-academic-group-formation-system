package lk.nibm.academic_group_formation_system.model;

public class Student {

    private String id;
    private String name;
    private double attendance;
    private double currentGpa;
    private double previousGpa;
    private double readinessScore;
    private LeadershipPreference leadershipPreference;
    private String category;

    public Student(String id, String name, double attendance,
                   double currentGpa, double previousGpa,
                   LeadershipPreference leadershipPreference) {
        this.id = id;
        this.name = name;
        this.attendance = attendance;
        this.currentGpa = currentGpa;
        this.previousGpa = previousGpa;
        this.leadershipPreference = leadershipPreference;
    }

    public String getId() { return id; }
    public double getAttendance() { return attendance; }
    public double getCurrentGpa() { return currentGpa; }
    public double getPreviousGpa() { return previousGpa; }
    public LeadershipPreference getLeadershipPreference() { return leadershipPreference; }

    public double getReadinessScore() { return readinessScore; }
    public void setReadinessScore(double readinessScore) { this.readinessScore = readinessScore; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
