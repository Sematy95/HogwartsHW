package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    private ru.hogwarts.school.repositories.FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyServiceImpl;


    @Test
    @DisplayName("Добавление факультета  - положительный тест")
    void add_positive() {
        when(facultyRepository.save(new Faculty(1L, "Griffindor", "Red"))).thenReturn(new Faculty(1L, "Griffindor", "Red"));
        //test
        Faculty expected = new Faculty(1L, "Griffindor", "Red");
        Faculty actual = facultyServiceImpl.add(new Faculty(1L, "Griffindor", "Red"));

        //check
        assertEquals(expected, actual);

    }


    @Test
    @DisplayName("Проверка значения на null - значение не null ")
    void nullCheck() {

        Faculty foundFaculty = new Faculty(1L, "Griffindor", "Red");

        //test
        ResponseEntity<Faculty> actual = facultyServiceImpl.nullCheck(foundFaculty);
        ResponseEntity<Faculty> expected = ResponseEntity.ok(foundFaculty);
        //check
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Проверка значения на null - значение не null ")
    void nullCheckNegative() {

        Faculty foundFaculty = null;

        //test
        ResponseEntity<Faculty> actual = facultyServiceImpl.nullCheck(null);
        ResponseEntity<Faculty> expected = ResponseEntity.notFound().build();
        //check
        assertEquals(actual, expected);
    }

}