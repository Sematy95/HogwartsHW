package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
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

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for adding faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public ResponseEntity<Faculty> findFaculty(long id) {
        logger.info("Was invoked method for searching faculty by id {}", id);
        Faculty foundFaculty = facultyRepository.findById(id).get();
        return nullCheck(foundFaculty);
    }

    @Override
    public ResponseEntity<Faculty> editFaculty(Faculty faculty) {
        logger.info("Was invoked method for editing faculty");

        Faculty editfaculty = facultyRepository.save(faculty);
        return nullCheck(editfaculty);
    }

    @Override
    public void deleteFaculty(long id) {
        logger.info("Was invoked method for deleting faculty{}", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> findAll() {
        logger.info("Was invoked method for showing all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for searching faculty by color");
        return facultyRepository.findByColor(color);
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        logger.info("Was invoked method for searching faculty by color or name");
        return facultyRepository.findByColorOrNameIgnoreCase(color, name);
    }

    @Override
    public Collection<Student> findAllStudentsInFaculty(long facultyId) {
        logger.info("Was invoked method for showing all students in faculty");
        return facultyRepository.findById(facultyId).orElseThrow(() -> {
                    logger.error("findAllStudentsInFaculty - faculty is not found by facultyId {}", facultyId);
                    return new FacultyNotFoundException("Faculty isw not found");
                })
                .getStudents();
    }

    @Override
    public String getLongestFacultyName() {
        logger.info("Was invoked method for getting longest faculty name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName).
                max(Comparator.comparingInt(String::length)).orElseThrow(() -> {
                    logger.error("getLongestFacultyName - faculties are not found");
                    return new FacultyNotFoundException("Faculties are not found");
                });
    }

    public ResponseEntity<Faculty> nullCheck(Faculty faculty) {
        if (faculty == null) {
            logger.error("Faculty is null");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    public ResponseEntity<Student> nullCheck(Student student) {
        if (student == null) {
            logger.error("Student is null");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

}
