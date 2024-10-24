package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private ru.hogwarts.school.repositories.StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    @Test
    @DisplayName("Добавление студента  - положительный тест")
    void add_positive() {
        when(studentRepository.save(new Student(1L, "Harry", 11))).thenReturn(new Student(1L, "Harry", 11));
        //test
        Student expected = new Student(1L, "Harry", 11);
        Student actual = studentServiceImpl.add(new Student(1L, "Harry", 11));

        //check
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Проверка значения на null - значение не null ")
    void nullCheck() {

        Student foundStudent = new Student(1L, "Harry", 11);

        //test
        ResponseEntity<Student> actual = studentServiceImpl.nullCheck(foundStudent);
        ResponseEntity<Student> expected = ResponseEntity.ok(foundStudent);
        //check
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Проверка значения на null - значение не null ")
    void nullCheckNegative() {

        Faculty foundFaculty = null;

        //test
        ResponseEntity<Student> actual = studentServiceImpl.nullCheck(null);
        ResponseEntity<Student> expected = ResponseEntity.notFound().build();
        //check
        assertEquals(actual, expected);
    }

}