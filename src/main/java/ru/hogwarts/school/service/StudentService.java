package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student add(Student student);

    ResponseEntity<Student> findStudent(long id);

    ResponseEntity<Student> editStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> findAll();

    Collection<Student> findByAge(int age);
}
