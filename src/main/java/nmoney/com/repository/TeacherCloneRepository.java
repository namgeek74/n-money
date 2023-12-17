package nmoney.com.repository;

import nmoney.com.entity.Teacher;
import nmoney.com.entity.TeacherClone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherCloneRepository extends JpaRepository<TeacherClone, Integer> {
    @Query(nativeQuery = true, value = "EXEC GetAllTeacher")
    List<TeacherClone> getAllTeacherByStoredProcedure();


    @Query(nativeQuery = true, value = "EXEC GetAllTeacherByName :name")
    List<TeacherClone> getAllTeacherByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM Teacher WHERE name = :name")
    List<TeacherClone> getAllTeacherByNameNativeQuery(String name);

    // Java Persistence Query Language
    @Query("SELECT tc FROM TeacherClone tc WHERE teacherName = :name")
    List<TeacherClone> getAllTeacherByNameJPQL(String name);

    @Query("SELECT tc.teacherAge FROM TeacherClone tc WHERE teacherName = :name")
    List<Integer> getAgeByNameJPQL(String name);

    @Query("SELECT tc FROM TeacherClone tc")
    List<TeacherClone> findAllList(Pageable pageable);

    @Query("SELECT tc FROM TeacherClone tc")
    Slice<TeacherClone> findAllSlice(Pageable pageable);
}
