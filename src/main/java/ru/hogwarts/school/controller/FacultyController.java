package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    ResponseEntity<Faculty> findFaculty(@PathVariable("id") long id) {
        return facultyService.findFaculty(id);
    }

    @PostMapping("/add")
    Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<Faculty> editFaculty(@PathVariable("id") long id,
                                        @RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);

    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Faculty> deleteFaculty(@PathVariable("id") long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/all")
    Collection<Faculty> findAll() {
        return facultyService.findAll();
    }

    @GetMapping("/find/color/{color}")
    Collection<Faculty> findByColor(@PathVariable("color") String color) {
        return facultyService.findByColor(color);
    }


}
