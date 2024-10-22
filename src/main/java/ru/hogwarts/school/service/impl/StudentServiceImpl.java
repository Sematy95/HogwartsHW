package ru.hogwarts.school.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long lastId = 0;

    @Override
    public Student add(Student student) {
        student.setId(++lastId);
        studentMap.put(lastId, student);
        return student;
    }

    @Override
    public ResponseEntity<Student> findStudent(long id) {
        Student student = studentMap.get(id);
        return nullCheck(student);
    }

    @Override
    public ResponseEntity<Student> editStudent(Student student) {
        if (!studentMap.containsKey(student.getId())) {
            return ResponseEntity.notFound().build();
        }
        Student foundStudent = studentMap.put(student.getId(), student);
        return nullCheck(foundStudent);
    }

    @Override
    public ResponseEntity<Student> deleteStudent(long id) {
        Student student = studentMap.remove(id);
        return nullCheck(student);
    }

    @Override
    public Collection<Student> findAll() {
        return Collections.unmodifiableCollection(studentMap.values());
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return studentMap.values().stream().
                filter(e -> e.getAge() == age).
                toList();
    }

    private ResponseEntity<Student> nullCheck(Student student) {
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
}
