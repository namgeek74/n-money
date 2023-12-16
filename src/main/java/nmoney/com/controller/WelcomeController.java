package nmoney.com.controller;

import nmoney.com.entity.Teacher;
import nmoney.com.entity.TeacherClone;
import nmoney.com.repository.TeacherCloneRepository;
import nmoney.com.repository.TeacherRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {
    private TeacherRepository teacherRepository;

    private TeacherCloneRepository teacherCloneRepository;

    public WelcomeController(
            TeacherRepository teacherRepository,
            TeacherCloneRepository teacherCloneRepository
    ) {
        this.teacherRepository = teacherRepository;
        this.teacherCloneRepository = teacherCloneRepository;
    }

    @GetMapping("/welcome-message")
    public String getWelcomeMessage() {
        return "Welcome to NMoney version 1!";
    }

    @GetMapping("/test-connection")
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        return teachers;
    }

    @GetMapping("/test-connection-2")
    public List<Teacher> getAllTeachersByStoredProcedure() {
        return teacherRepository.getAllTeacherByStoredProcedure();
    }

    @PostMapping("/test-connection-2")
    public List<Teacher> getAllTeachersByName(@RequestBody String name) {
        System.out.println("Name: " + name);
        return teacherRepository.getAllTeacherByName(name);
    }

    @PostMapping("/test-connection-3")
    public List<Teacher> getAllTeachersByNameNativeQuery(@RequestBody String name) {
        return teacherRepository.getAllTeacherByNameNativeQuery(name);
    }

    @PostMapping("/test-clone-1")
    public List<TeacherClone> getAllTeachersCloneByName(@RequestBody String name) {
        System.out.println("Name: " + name);
        return teacherCloneRepository.getAllTeacherByNameNativeQuery(name);
    }

    @PostMapping("/test-clone-2")
    public List<TeacherClone> getAllTeachersByNameJPQL(@RequestBody String name) {
        return teacherCloneRepository.getAllTeacherByNameJPQL(name);
    }

    @PostMapping("/test-clone-3")
    public List<Integer> getTeacherAgeByNameJPQL(@RequestBody String name) {
        return teacherCloneRepository.getAgeByNameJPQL(name);
    }
}
