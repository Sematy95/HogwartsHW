package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    private final StudentServiceImpl studentService = new StudentServiceImpl();

    @Test
    @DisplayName("Добавление студента - положительный тест")
    void add() {
        //test
        Student expected = new Student(1L, "Harry", 11);
        Student actual = studentService.add(new Student(1L, "Harry", 11));
        Student actual1 = studentService.findStudent(1).getBody();

        //check
        assertEquals(expected, actual);
        assertEquals(expected, actual1);
    }

    @Test
    @DisplayName("Поиск студента по id - положительный тест")
    void findStudentPositive() {
        //test
        studentService.add(new Student(1L, "Harry", 11));
        Student actual = studentService.findStudent(1L).getBody();
        Student expected = new Student(1L, "Harry", 11);

        //check
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Поиск студента по id - негативный тест - нет такого id ")
    void findStudentNegative() {

        //test
        ResponseEntity<Student> actual = studentService.findStudent(2L);
        ResponseEntity<Student> expected = ResponseEntity.notFound().build();
        //check
        assertEquals(actual, expected);

    }

    @Test
    @DisplayName("Редактирование студента - положительный тест")
    void editStudent() {
        Student expected = studentService.add(new Student(1L, "Harry", 11));
        //test
        Student forReplace = new Student(1L, "Harry Potter", 11);
        Student actual = studentService.editStudent(forReplace).getBody();
        Student actual1 = studentService.findStudent(1L).getBody();

        //check
        assertEquals(actual, expected);
        assertEquals(forReplace, actual1);

    }

    @DisplayName("Редактирование студента - негативный тест - не нашли id")
    @Test
    void editStudentNegative() {
        studentService.add(new Student(1L, "Harry", 11));
        //test

        ResponseEntity<Student> actual = studentService.editStudent(new Student(2L, "Harry Potter", 11));
        ResponseEntity<Student> expected = ResponseEntity.notFound().build();

        //check
        assertEquals(actual, expected);
    }

    @DisplayName("Положительный тест - удалили студента по id")
    @Test
    void deleteStudentPositive() {
        //Test
        Student expected = studentService.add(new Student(1L, "Harry", 11));
        Student actual = studentService.deleteStudent(1L).getBody();

        //check
        assertEquals(actual, expected);
        assertFalse(studentService.findAll().contains(expected));

    }

    @DisplayName("Негативный тест на удаление - не нашли id")
    @Test
    void deleteStudentNegative() {
        //Test
        studentService.add(new Student(1L, "Harry", 11));
        ResponseEntity<Student> actual = studentService.deleteStudent(2L);
        ResponseEntity<Student> expected = ResponseEntity.notFound().build();

        //check
        assertEquals(actual, expected);

    }

    @Test
    void findAll() {
        Student var1 = studentService.add(new Student(1L, "Harry", 11));
        Student var2 = studentService.add(new Student(2L, "Ron", 11));
        Student var3 = studentService.add(new Student(3L, "Antony", 11));

        Collection<Student> actual = studentService.findAll();

        //check
        assertTrue(actual.contains(var1) & actual.contains(var2) & actual.contains(var3));
    }

    @Test
    void findByAge() {
        Student var1 = studentService.add(new Student(1L, "Harry", 11));
        Student var2 = studentService.add(new Student(2L, "Ron", 11));
        Student var3 = studentService.add(new Student(3L, "Antony", 12));

        Collection<Student> actual = studentService.findByAge(11);
        //check
        assertTrue(actual.contains(var1) & actual.contains(var2) & !actual.contains(var3));
    }
}