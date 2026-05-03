package edu.isgb.school.repositories;

import edu.isgb.school.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findBySchoolId(Long schoolId);
    Optional<Student> findByEmail(String email);
    List<Student> findByLastName(String lastName);
}