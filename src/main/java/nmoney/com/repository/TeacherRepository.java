package nmoney.com.repository;

import nmoney.com.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query(nativeQuery = true, value = "EXEC GetAllTeacher")
    List<Teacher> getAllTeacherByStoredProcedure();


    @Query(nativeQuery = true, value = "EXEC GetAllTeacherByName :name")
    List<Teacher> getAllTeacherByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM Teacher WHERE name = :name")
    List<Teacher> getAllTeacherByNameNativeQuery(String name);
}
