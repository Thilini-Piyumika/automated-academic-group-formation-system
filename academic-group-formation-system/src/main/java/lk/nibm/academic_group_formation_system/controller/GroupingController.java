package lk.nibm.academic_group_formation_system.controller;

import lk.nibm.academic_group_formation_system.model.GroupingStrategy;
import lk.nibm.academic_group_formation_system.model.LeadershipPreference;
import lk.nibm.academic_group_formation_system.model.Student;
import lk.nibm.academic_group_formation_system.service.GroupingService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/groups")
public class GroupingController {

    private final GroupingService service;

    public GroupingController(GroupingService service) {
        this.service = service;
    }

    @GetMapping
    public Map<String, List<Student>> generateGroups() {

        List<Student> students = List.of(
                new Student("S1", "A", 85, 3.5, 3.4, LeadershipPreference.L1),
                new Student("S2", "B", 90, 3.8, 3.7, LeadershipPreference.L2),
                new Student("S3", "C", 75, 3.0, 3.1, LeadershipPreference.L3),
                new Student("S4", "D", 88, 3.6, 3.5, LeadershipPreference.L1),
                new Student("S5", "E", 70, 2.9, 3.0, LeadershipPreference.L2),

                new Student("S6", "F", 92, 3.9, 3.8, LeadershipPreference.L1),
                new Student("S7", "G", 80, 3.2, 3.3, LeadershipPreference.L3),
                new Student("S8", "H", 86, 3.4, 3.5, LeadershipPreference.L2),
                new Student("S9", "I", 78, 3.1, 3.0, LeadershipPreference.L3),
                new Student("S10", "J", 95, 4.0, 3.9, LeadershipPreference.L1),

                new Student("S11", "K", 83, 3.3, 3.2, LeadershipPreference.L2),
                new Student("S12", "L", 68, 2.8, 2.9, LeadershipPreference.L3),
                new Student("S13", "M", 91, 3.7, 3.6, LeadershipPreference.L1),
                new Student("S14", "N", 76, 3.0, 3.1, LeadershipPreference.L2),
                new Student("S15", "O", 84, 3.4, 3.3, LeadershipPreference.L3),

                new Student("S16", "P", 89, 3.6, 3.5, LeadershipPreference.L1),
                new Student("S17", "Q", 72, 2.9, 3.0, LeadershipPreference.L2),
                new Student("S18", "R", 87, 3.5, 3.4, LeadershipPreference.L1),
                new Student("S19", "S", 79, 3.1, 3.2, LeadershipPreference.L3),
                new Student("S20", "T", 93, 3.8, 3.7, LeadershipPreference.L1),

                new Student("S21", "U", 66, 2.7, 2.8, LeadershipPreference.L3),
                new Student("S22", "V", 81, 3.2, 3.1, LeadershipPreference.L2),
                new Student("S23", "W", 88, 3.6, 3.5, LeadershipPreference.L1),
                new Student("S24", "X", 74, 3.0, 3.1, LeadershipPreference.L2),
                new Student("S25", "Y", 90, 3.8, 3.7, LeadershipPreference.L1)
        );

        return service.createGroups(
                students,
                3,
                2,
                GroupingStrategy.BEST_AVERAGE
        );
    }
}

