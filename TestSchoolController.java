package edu.isgb.school.test;

import edu.isgb.school.entities.*;
import edu.isgb.school.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/school")
public class TestSchoolController {

    @Autowired
    private SchoolService schoolService;

    // POST - Créer une School simple
    @PostMapping("/school")
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        School saved = schoolService.createSchool(school);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // POST - Créer une School avec Students, Instructors, Departments
    @PostMapping("/school/full")
    public ResponseEntity<School> createSchoolFull(@RequestBody School school) {
        School saved = schoolService.createSchoolWithDetails(school);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET - Toutes les Schools
    @GetMapping("/school")
    public ResponseEntity<List<School>> getAllSchools() {
        return ResponseEntity.ok(schoolService.getAllSchools());
    }

    // GET - School par ID
    @GetMapping("/school/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
        Optional<School> school = schoolService.getSchoolById(id);
        return school.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Créer un Student avec Address et School
    @PostMapping("/student/{schoolId}")
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student,
            @PathVariable Long schoolId) {
        Student saved = schoolService.createStudentWithAddressAndSchool(
                student, student.getAddress(), schoolId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET - Tous les Students
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(schoolService.getAllStudents());
    }

    // POST - Créer un Instructor avec ses Courses
    @PostMapping("/instructor")
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
        Instructor saved = schoolService.createInstructorWithCourses(
                instructor, instructor.getCourses());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // GET - Instructors par nom
    @GetMapping("/instructors/lastname/{lastName}")
    public ResponseEntity<List<Instructor>> getByLastName(@PathVariable String lastName) {
        return ResponseEntity.ok(schoolService.getInstructorsByLastName(lastName));
    }

    // GET - Instructor par ID
    @GetMapping("/instructor/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) {
        Optional<Instructor> instructor = schoolService.getInstructorById(id);
        return instructor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Course par ID
    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = schoolService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Courses d'un Instructor
    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getCoursesByInstructorId(id));
    }

    // POST - Ajouter un Course à un Instructor existant
    @PostMapping("/instructor/{id}/course")
    public ResponseEntity<Course> addCourse(
            @PathVariable Long id,
            @RequestBody Course course) {
        Course saved = schoolService.addCourseToInstructor(id, course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}