package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {

    private final FacultyServiceImpl facultyService = new FacultyServiceImpl();


    @Test
    @DisplayName("Добавление факультета - положительный тест")
    void add() {
        //test
        Faculty expected = new Faculty(1L, "Griffindor", "Red");
        Faculty actual = facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        Faculty actual1 = facultyService.findFaculty(1).getBody();

        //check
        assertEquals(expected, actual);
        assertEquals(expected, actual1);
    }

    @Test
    @DisplayName("Поиск факультета по id - положительный тест")
    void findFacultyPositive() {
        //test
        facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        Faculty actual = facultyService.findFaculty(1L).getBody();
        Faculty expected = new Faculty(1L, "Griffindor", "Red");

        //check
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Поиск факультета по id - негативный тест - нет такого id ")
    void findFacultyNegative() {
        //test
        ResponseEntity<Faculty> actual = facultyService.findFaculty(2L);
        ResponseEntity<Faculty> expected = ResponseEntity.notFound().build();
        //check
        assertEquals(actual, expected);

    }

    @Test
    @DisplayName("Редактирование факультета - положительный тест")
    void editFaculty() {
        Faculty expected = facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        //test
        Faculty forReplace = new Faculty(1L, "Empire", "Black");
        Faculty actual = facultyService.editFaculty(forReplace).getBody();
        Faculty actual1 = facultyService.findFaculty(1L).getBody();

        //check
        assertEquals(actual, expected);
        assertEquals(forReplace, actual1);

    }

    @DisplayName("Редактирование факультета - негативный тест - не нашли id")
    @Test
    void editFacultyNegative() {
        facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        //test

        ResponseEntity<Faculty> actual = facultyService.editFaculty(new Faculty(2L, "Griffindor", "Red"));
        ResponseEntity<Faculty> expected = ResponseEntity.notFound().build();

        //check
        assertEquals(actual, expected);
    }

    @DisplayName("Положительный тест - удалили факультет по id")
    @Test
    void deleteFacultyPositive() {
        //Test
        Faculty expected = facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        Faculty actual = facultyService.deleteFaculty(1L).getBody();

        //check
        assertEquals(actual, expected);
        assertFalse(facultyService.findAll().contains(expected));

    }

    @DisplayName("Негативный тест на удаление - не нашли id")
    @Test
    void deleteFacultyNegative() {
        //Test
        facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        ResponseEntity<Faculty> actual = facultyService.deleteFaculty(2L);
        ResponseEntity<Faculty> expected = ResponseEntity.notFound().build();

        //check
        assertEquals(actual, expected);


    }

    @Test
    void findAll() {
        Faculty var1 = facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        Faculty var2 = facultyService.add(new Faculty(2L, "Horde", "Black"));
        Faculty var3 = facultyService.add(new Faculty(3L, "Alliance", "White"));

        Collection<Faculty> actual = facultyService.findAll();

        //check
        assertTrue(actual.contains(var1) & actual.contains(var2) & actual.contains(var3));

    }

    @Test
    void findByColor() {
        Faculty var1 = facultyService.add(new Faculty(1L, "Griffindor", "Red"));
        Faculty var2 = facultyService.add(new Faculty(2L, "Horde", "Red"));
        Faculty var3 = facultyService.add(new Faculty(3L, "Alliance", "White"));

        Collection<Faculty> actual = facultyService.findByColor("Red");
        //check
        assertTrue(actual.contains(var1) & actual.contains(var2) & !actual.contains(var3));

    }
}