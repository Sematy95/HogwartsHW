package ru.hogwarts.school.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long lastId = 0;

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(++lastId);
        facultyMap.put(lastId, faculty);
        return faculty;
    }

    @Override
    public ResponseEntity<Faculty> findFaculty(long id) {
        Faculty faculty = facultyMap.get(id);
        return nullCheck(faculty);

    }

    @Override
    public ResponseEntity<Faculty> editFaculty(Faculty faculty) {
        if (!facultyMap.containsKey(faculty.getId())) {
            return ResponseEntity.notFound().build();
        }
        Faculty foundfaculty = facultyMap.put(faculty.getId(), faculty);
        return nullCheck(foundfaculty);
    }

    @Override
    public ResponseEntity<Faculty> deleteFaculty(long id) {
        Faculty faculty = facultyMap.remove(id);
        return nullCheck(faculty);

    }

    @Override
    public Collection<Faculty> findAll() {
        return Collections.unmodifiableCollection(facultyMap.values());
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        return facultyMap.values().stream().
                filter(e -> Objects.equals(e.getColor(), color)).
                toList();
    }

    private ResponseEntity<Faculty> nullCheck(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

}
