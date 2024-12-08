package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
public class StudentController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);


    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable("id") long id) {
        return studentService.findStudent(id);
    }

    @PutMapping("/update/{id}")
    public void editStudent(@PathVariable("id") long id, @RequestBody Student student) {
        studentService.editStudent(id, student);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);

    }

    @GetMapping("/find/all")
    public Collection<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/find/allStudentsStartingWithA")
    public Collection<String> getStudentsWithNameStartingWith() {
        return studentService.getStudentsWithNameStartingWithA();
    }

    @GetMapping("/getAverageAge2")
    public double getStudentsAverageAge2() {
        return studentService.getStudentsAverageAge2();
    }

    @GetMapping("/getValueUpd")
    public int getValueUpd() {
        long start = System.currentTimeMillis();
        int value = IntStream.iterate(1, a -> a + 1).limit(1_000_000).parallel().reduce(0, Integer::sum);
        //int value = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        long finish = System.currentTimeMillis();
        logger.info("getValueUpd took {} ms", finish - start);
        return value;
    }

    @GetMapping("/getValuOld")
    public int getValueOld() {
        long start = System.currentTimeMillis();
        //int value = Stream.iterate(1, a -> a + 1).limit(1_000_000).parallel().reduce(0, (a, b) -> a + b );
        int value = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        long finish = System.currentTimeMillis();
        logger.info("getValueOLD took {} ms", finish - start);
        return value;
    }

    @GetMapping("/find/age/{age}")
    public Collection<Student> findByAge(@PathVariable("age") int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/find/age/between")
    public Collection<Student> findByAgeBetween(@RequestParam("ageMin") int ageMin,
                                                @RequestParam("ageMax") int ageMax) {
        return studentService.findByAgeBetween(ageMin, ageMax);
    }

    @GetMapping("/findStudentFaculty/{id}")
    public Faculty findStudentFaculty(@PathVariable("id") long id) {

        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/getStudentsCount")
    public int getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping("/getAverageAge")
    public double getStudentsAverageAge() {
        return studentService.getStudentsAverageAge();
    }

    @GetMapping("/getLastFive")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }
}
