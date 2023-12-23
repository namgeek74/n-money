package nmoney.com.controller;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nmoney.com.dto.PagePayload;
import nmoney.com.entity.Department;
import nmoney.com.entity.Employee;
import nmoney.com.entity.Teacher;
import nmoney.com.entity.TeacherClone;
import nmoney.com.repository.DepartmentRepository;
import nmoney.com.repository.EmployeeRepository;
import nmoney.com.repository.TeacherCloneRepository;
import nmoney.com.repository.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WelcomeController {
    private TeacherRepository teacherRepository;

    private EmployeeRepository employeeRepository;

    private TeacherCloneRepository teacherCloneRepository;

    private DepartmentRepository departmentRepository;

    public WelcomeController(
            TeacherRepository teacherRepository,
            TeacherCloneRepository teacherCloneRepository,
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository
    ) {
        this.teacherRepository = teacherRepository;
        this.teacherCloneRepository = teacherCloneRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
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

    @PostMapping("/test-pageable-1")
    public Page<TeacherClone> getTeacherPerPage(@RequestBody PagePayload payload) {
        Pageable request = PageRequest.of(payload.getPageNumber(), payload.getPageSize());
        return teacherCloneRepository.findAll(request);
    }

    @PostMapping("/test-pageable-2")
    public List<TeacherClone> getTeacherPagination(@RequestBody PageSizePayload payload) {
        Pageable request = PageRequest.of(payload.getPageNumber(), payload.getPageSize());
        return teacherCloneRepository.findAllList(request);
    }

    @PostMapping("/test-pageable-3")
    public Slice<TeacherClone> findAllSlice(@RequestBody PagePayload payload) {
        Pageable request = PageRequest.of(payload.getPageNumber(), payload.getPageSize());
        return teacherCloneRepository.findAllSlice(request);
    }

    @PostMapping("/test-pageable-4")
    public Page<TeacherClone> findAll(@RequestBody PagePayload payload) {
        Pageable request = PageRequest.of(payload.getPageNumber(), payload.getPageSize(), Sort.by("teacherName").ascending());
        return teacherCloneRepository.findAll(request);
    }

    @PostMapping("/test-pageable-5")
    public List<TeacherClone> findAllPaginationAndSort(@RequestBody PagePayload payload) {
        Pageable request = PageRequest.of(
                payload.getPageNumber(),
                payload.getPageSize(),
                Sort.by("teacherName").ascending().and(Sort.by("teacherAge").descending())
        );
        return teacherCloneRepository.findAll(request).toList();
    }

    @GetMapping("/test-sort-1")
    public List<TeacherClone> findAllSortByNameAscending() {
        return teacherCloneRepository.findAll(Sort.by("teacherName").ascending());
    }

    @GetMapping("/test-sort-2")
    public List<TeacherClone> findAllSortByAgeDescending() {
        return teacherCloneRepository.findAll(Sort.by("teacherAge").descending());
    }

    private static class PageSizePayload {
        private int pageNumber;
        private int pageSize;

        public PageSizePayload(int pageNumber, int pageSize) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }

        public PageSizePayload() {
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    @GetMapping("/test-jpa")
    public List<Department> findListDepartment() throws JsonProcessingException {
//        test();
        return employeeRepository.findListDepartment();
    }

    @GetMapping("/test-jpa-1")
    public List<Employee> findAllDepartment() {
        Optional<Department> department = departmentRepository.findById(1);
        if (department.isPresent()) {
//            System.out.println(department);
            List<Employee> employees = employeeRepository.findByDepartment(department.get());
            return employees;
        }
        return null;
    }

    public void test() throws JsonProcessingException {
        User user = new User(1, "John", new ArrayList<Item>());
        Item item = new Item(2, "book", user);
        user.getUserItems().add(item);

        final String itemJson = new ObjectMapper().writeValueAsString(item);
        final String userJson = new ObjectMapper().writeValueAsString(user);

        System.out.println(itemJson);
        System.out.println(userJson);
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public class User {
        public int id;
        public String name;
        @JsonBackReference
        public List<Item> userItems;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public class Item {
        public int id;
        public String itemName;
        @JsonManagedReference
        public User owner;
    }

    @GetMapping("/test-jpa-2")
    public List<Department> findDepartmentByJoin() {
        return employeeRepository.findDepartmentByJoin();
    }
}
