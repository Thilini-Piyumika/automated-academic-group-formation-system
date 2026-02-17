package lk.nibm.academic_group_formation_system.controller;

import lk.nibm.academic_group_formation_system.model.GroupingStrategy;
//import lk.nibm.academic_group_formation_system.model.LeadershipPreference;
import org.springframework.web.multipart.MultipartFile;
import lk.nibm.academic_group_formation_system.model.Student;
import lk.nibm.academic_group_formation_system.service.GroupingService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/groups")
public class GroupingController {

    private final GroupingService service;

    public GroupingController(GroupingService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public Map<String, List<Student>> generateGroups(@RequestParam("file") MultipartFile file) {

        List<Student> students = service.readStudentsFromExcel(file);

        return service.createGroups(
                students,
                3,
                2,
                GroupingStrategy.BEST_AVERAGE
        );
    }
}



