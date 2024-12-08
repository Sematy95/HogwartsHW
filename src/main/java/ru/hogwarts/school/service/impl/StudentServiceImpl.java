package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for adding student");
        return studentRepository.save(student);
    }

    @Override
    public ResponseEntity<Student> findStudent(long id) {
        logger.info("Was invoked method for searching student by id");
        Student student = studentRepository.findById(id).get();
        return nullCheck(student);
    }

    @Override
    public void editStudent(long id, Student student) {
        logger.info("Was invoked method for editing student");
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Студента с таким номером нет");
        }
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        logger.info("Was invoked method for deleting student");
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findAll() {
        logger.info("Was invoked method for showing all students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for searching student by age");
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        logger.info("Was invoked method for searching student by age in range");
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    @Override
    public Faculty getStudentFaculty(long studentId) {
        logger.info("Was invoked method for showing student's faculty");
        return studentRepository.findById(studentId).orElseThrow(() -> {
            logger.error("getStudentFaculty - student is not found by studentId {}", studentId);
            return new StudentNotFoundException("Faculty isw not found");
        }).getFaculty();

    }

    @Override
    public int getStudentsCount() {
        logger.info("Was invoked method for showing student's count");
        return studentRepository.getStudentsCount();
    }

    @Override
    public double getStudentsAverageAge() {
        logger.info("Was invoked method for showing student's average age");
        return studentRepository.getStudentsAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for showing last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<String> getStudentsWithNameStartingWithA() {
        List<String> studentsStartingWithA = studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase).sorted().toList();
        logger.info("Was invoked method for printing all students with name starting with A ");
        return studentsStartingWithA;
    }

    @Override
    public double getStudentsAverageAge2() {
        logger.info("Was invoked method2 for showing student's average age");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average().orElseThrow(() -> {
                    logger.error("getStudentsAverageAge2 - no values");
                    return new StudentNotFoundException("No values found");
                });
    }

    public ResponseEntity<Student> nullCheck(Student student) {
        if (student == null) {
            logger.error("Student is null");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    public ResponseEntity<Faculty> nullCheck(Faculty faculty) {
        if (faculty == null) {
            logger.error("Faculty is null");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

}
