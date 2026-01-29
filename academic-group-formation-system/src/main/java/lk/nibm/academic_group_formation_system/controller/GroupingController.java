package lk.nibm.academic_group_formation_system.controller;

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
                new Student("S6", "F", 92, 3.9, 3.8, LeadershipPreference.L1)
        );

        return service.createGroups(students, 3, 2);
    }
}
