package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable("id") long id) {
        return facultyService.findFaculty(id);
    }

    @PostMapping("/add")
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Faculty> editFaculty(@PathVariable("id") long id,
                                               @RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable("id") long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/all")
    public Collection<Faculty> findAll() {
        return facultyService.findAll();
    }

    @GetMapping("/find/color/{color}")
    public Collection<Faculty> findByColor(@PathVariable("color") String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping("/find/colorOrName")
    public Collection<Faculty> findByColorOrName(@RequestParam(required = false) String color,
                                                 @RequestParam(required = false) String name) {
        return facultyService.findByColorOrName(color, name);
    }

    public @GetMapping("/find/studentsInFaculty/{id}")
    Collection<Student> findAllStudentsInFaculty(@PathVariable("id") long id) {
        return facultyService.findAllStudentsInFaculty(id);
    }

    @GetMapping("/longestName")
    public ResponseEntity<String> getLongestFacultyName() {
        return ResponseEntity.ok(facultyService.getLongestFacultyName());
    }


}
