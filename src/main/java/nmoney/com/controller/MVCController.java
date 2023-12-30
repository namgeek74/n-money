package nmoney.com.controller;

import nmoney.com.entity.Department;
import nmoney.com.repository.DepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MVCController {
    private DepartmentRepository departmentRepository;

    public MVCController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/get-mvc")
    void getMVC() {
        System.out.println("Start");
        List<Department> departmentList = departmentRepository.findAll();
        List<Department> departmentList1 = departmentRepository.getDepartmentLeftJoinFetch();
        System.out.println("end");
    }
}
