package edu.isgb.school.repositories;

import edu.isgb.school.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findByLastName(String lastName);
    List<Instructor> findBySchoolId(Long schoolId);
    Optional<Instructor> findByEmail(String email);
}