package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty add(Faculty faculty);

    ResponseEntity<Faculty> findFaculty(long id);

    ResponseEntity<Faculty> editFaculty(Faculty faculty);

    ResponseEntity<Faculty> deleteFaculty(long id);

    Collection<Faculty> findAll();

    Collection<Faculty> findByColor(String color);
}
