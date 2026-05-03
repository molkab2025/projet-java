package edu.isgb.school.services;

import edu.isgb.school.entities.*;
import edu.isgb.school.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // 8a - Créer une School simple
    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

    // 8a - Créer une School avec Students, Instructors et Departments
    public School createSchoolWithDetails(School school) {
        if (school.getStudents() != null) {
            for (Student s : school.getStudents()) {
                s.setSchool(school);
            }
        }
        if (school.getInstructors() != null) {
            for (Instructor i : school.getInstructors()) {
                i.setSchool(school);
            }
        }
        if (school.getDepartments() != null) {
            for (Department d : school.getDepartments()) {
                d.setSchool(school);
            }
        }
        return schoolRepository.save(school);
    }

    // 8b - Retourner une School par id
    public Optional<School> getSchoolById(Long id) {
        return schoolRepository.findById(id);
    }

    // 8c - Créer un Student avec Address et School
    public Student createStudentWithAddressAndSchool(Student student, Address address, Long schoolId) {
        Address savedAddress = addressRepository.save(address);
        student.setAddress(savedAddress);

        Optional<School> school = schoolRepository.findById(schoolId);
        if (school.isPresent()) {
            student.setSchool(school.get());
        } else {
            throw new RuntimeException("School not found with id: " + schoolId);
        }
        return studentRepository.save(student);
    }

    // 8d - Lister tous les Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 8e - Créer un Instructor avec une liste de cours
    public Instructor createInstructorWithCourses(Instructor instructor, List<Course> courses) {
        List<Course> tempCourses = new ArrayList<>(instructor.getCourses());
        instructor.setCourses(new ArrayList<>());
        Instructor savedInstructor = instructorRepository.save(instructor);

        if (tempCourses != null) {
            for (Course course : tempCourses) {
                Course savedCourse = courseRepository.save(course);
                savedInstructor.getCourses().add(savedCourse);
                savedCourse.getInstructors().add(savedInstructor);
            }
        }
        return instructorRepository.save(savedInstructor);
    }

    // 8f - Lister les Instructors par nom
    public List<Instructor> getInstructorsByLastName(String lastName) {
        return instructorRepository.findByLastName(lastName);
    }

    // 8g - Retourner un Instructor par id
    public Optional<Instructor> getInstructorById(Long id) {
        return instructorRepository.findById(id);
    }

    // 8h - Retourner un Course par id
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // 8i - Lister les Courses d'un Instructor
    public List<Course> getCoursesByInstructorId(Long instructorId) {
        Optional<Instructor> instructor = instructorRepository.findById(instructorId);
        if (instructor.isPresent()) {
            return instructor.get().getCourses();
        }
        throw new RuntimeException("Instructor not found with id: " + instructorId);
    }

    // 8j - Ajouter un Course à un Instructor existant
    public Course addCourseToInstructor(Long instructorId, Course course) {
        Optional<Instructor> instructorOpt = instructorRepository.findById(instructorId);
        if (instructorOpt.isPresent()) {
            Instructor instructor = instructorOpt.get();
            Course savedCourse = courseRepository.save(course);
            instructor.getCourses().add(savedCourse);
            savedCourse.getInstructors().add(instructor);
            instructorRepository.save(instructor);
            return savedCourse;
        }
        throw new RuntimeException("Instructor not found with id: " + instructorId);
    }

    // Lister toutes les Schools
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }
}