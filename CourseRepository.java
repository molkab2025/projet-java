package edu.isgb.school.repositories;

import edu.isgb.school.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c JOIN c.instructors i WHERE i.id = :instructorId")
    List<Course> findCoursesByInstructorId(@Param("instructorId") Long instructorId);

    Optional<Course> findByCode(String code);
}