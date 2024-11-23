package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student add(Student student);

    ResponseEntity<Student> findStudent(long id);

    void editStudent(long id, Student student);

    void deleteStudent(long id);

    Collection<Student> findAll();

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Faculty getStudentFaculty(long studentId);
}
