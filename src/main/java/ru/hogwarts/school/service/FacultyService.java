package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty add(Faculty faculty);

    ResponseEntity<Faculty> findFaculty(long id);

    ResponseEntity<Faculty> editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> findAll();

    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByColorOrName(String color, String name);

    Collection<Student> findAllStudentsInFaculty(long facultyId);
}
