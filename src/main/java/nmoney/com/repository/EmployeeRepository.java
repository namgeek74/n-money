package nmoney.com.repository;

import nmoney.com.entity.Department;
import nmoney.com.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e.department FROM Employee e")
    List<Department> findListDepartment();

    List<Employee> findByDepartment(Department department);

    @Query("SELECT d FROM Employee e JOIN e.department d")
    List<Department> findDepartmentByJoin();

}
