package ru.hogwarts.school.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    @Override
    public Student add(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public ResponseEntity<Student> findStudent(long id) {
        Student student = studentRepository.findById(id).get();
        return nullCheck(student);
    }

    @Override
    public void editStudent(long id, Student student) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Студента с таким номером нет");
        }
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    @Override
    public Faculty getStudentFaculty(long studentId) {
        return studentRepository.findById(studentId).orElseThrow(NullPointerException::new).getFaculty();

    }

    @Override
    public int getStudentsCount() {
        return studentRepository.getStudentsCount();
    }

    @Override
    public int getStudentsAverageAge() {
        return studentRepository.getStudentsAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    public ResponseEntity<Student> nullCheck(Student student) {
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    public ResponseEntity<Faculty> nullCheck(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

}
