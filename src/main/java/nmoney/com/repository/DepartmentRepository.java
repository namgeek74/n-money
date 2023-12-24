package nmoney.com.repository;

import nmoney.com.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query(value = "SELECT d FROM Department d JOIN FETCH d.employees")
    List<Department> getDepartmentJoinFetch();

    @Query(value = "SELECT d FROM Department d LEFT JOIN FETCH d.employees")
    List<Department> getDepartmentLeftJoinFetch();
}
