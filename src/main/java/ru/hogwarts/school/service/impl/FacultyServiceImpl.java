package ru.hogwarts.school.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {


    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public ResponseEntity<Faculty> findFaculty(long id) {
        Faculty foundFaculty = facultyRepository.findById(id).get();
        return nullCheck(foundFaculty);
    }

    @Override
    public ResponseEntity<Faculty> editFaculty(Faculty faculty) {

        Faculty editfaculty = facultyRepository.save(faculty);
        return nullCheck(editfaculty);
    }

    @Override
    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        return facultyRepository.findByColorOrNameIgnoreCase(color, name);
    }

    @Override
    public Collection<Student> findAllStudentsInFaculty(long facultyId) {
        return facultyRepository.findById(facultyId).get().getStudents();
    }

    public ResponseEntity<Faculty> nullCheck(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    public ResponseEntity<Student> nullCheck(Student student) {
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

}
