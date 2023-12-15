package nmoney.com.controller;

import nmoney.com.entity.Teacher;
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

    public WelcomeController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
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
}
