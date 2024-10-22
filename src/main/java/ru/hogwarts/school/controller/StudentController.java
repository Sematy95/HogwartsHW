package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @GetMapping("/find/{id}")
    ResponseEntity<Student> findStudent(@PathVariable("id") long id) {
        return studentService.findStudent(id);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Student> editStudent(@PathVariable("id") long id,
                                        @RequestBody Student student) {
        return studentService.editStudent(student);

    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Student> deleteFaculty(@PathVariable("id") long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/find/all")
    Collection<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/find/age/{age}")
    Collection<Student> findByAge(@PathVariable("age") int age) {
        return studentService.findByAge(age);
    }
}
