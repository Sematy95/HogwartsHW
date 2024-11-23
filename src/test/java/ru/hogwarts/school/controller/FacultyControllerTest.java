package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public
class FacultyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @MockBean
    private ru.hogwarts.school.repositories.FacultyRepository FacultyRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FacultyRepository facultyRepository;


    @Test
    @DisplayName("Корректно ищет факультет по id")
    void findFaculty() throws Exception {

        Long id = 1L;
        Faculty faculty = new Faculty(id, "Griffindor", "Red");
        String content = objectMapper.writeValueAsString(faculty);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        // test & check

        mockMvc.perform(get("/faculty/find/" + faculty.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

        verify(facultyRepository, times(1)).findById(id);


    }

    @Test
    @DisplayName("Корректно добавляет факультет")
    void add() throws Exception {
        Faculty faculty = new Faculty(1L, "Griffindor", "Red");
        String content = objectMapper.writeValueAsString(faculty);

        when(FacultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        //test & check
        mockMvc.perform(post("/faculty/add")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

        verify(facultyRepository, times(1)).save(any(Faculty.class));

    }

    @Test
    @DisplayName("Корректно обновляет факультет")
    void editFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty(facultyId, "Griffindor", "Red");
        String content = objectMapper.writeValueAsString(faculty);

        when(FacultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        //test & check
        mockMvc.perform(put("/faculty/update/" + facultyId)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

        verify(facultyRepository, times(1)).save(any(Faculty.class));


    }

    @Test
    @DisplayName("Корректно удаляет студента по id")
    void deleteFaculty() throws Exception {
        doNothing().when(facultyRepository).deleteById(anyLong());
        mockMvc.perform(delete("/faculty/delete/" + nextLong(1, 10)))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Корректно выводит список факультетов")
    void findAll() throws Exception {
        Faculty faculty1 = new Faculty(1L, "Griffindor", "Red");
        Faculty faculty2 = new Faculty(2L, "Griffindor2", "Red2");
        Faculty faculty3 = new Faculty(32L, "Griffindor3", "Red3");

        List<Faculty> facultyCollection = List.of(faculty1, faculty2, faculty3);

        when(facultyRepository.findAll()).thenReturn(facultyCollection);

        // test & check

        mockMvc.perform(get("/faculty/find/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andDo(print());

        verify(facultyRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Корректно выводит факультеты по цветам")
    void findByColor() throws Exception {

        String color = "Red";
        Faculty faculty1 = new Faculty(1L, "Griffindor", color);
        Faculty faculty2 = new Faculty(2L, "Griffindor2", color);

        List<Faculty> facultyCollection = List.of(faculty1, faculty2);


        when(facultyRepository.findByColor(anyString())).thenReturn(facultyCollection);

        // test & check

        mockMvc.perform(get("/faculty/find/color?" + color)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists());


        verify(facultyRepository, times(1)).findByColor(color);
    }


    @Test
    @DisplayName("Корректно выводит факультеты по цветам или имени")
    void findByColorOrName() throws Exception {
        Faculty faculty1 = new Faculty(1L, "Griffindor", "Red");
        Faculty faculty2 = new Faculty(2L, "Griffindor2", "Red2");

        List<Faculty> facultyCollection = List.of(faculty1, faculty2);
        when(facultyRepository.findByColorOrNameIgnoreCase(anyString(), anyString())).thenReturn(facultyCollection);

        // test & check

        mockMvc.perform(get("/faculty/find/colorOrName?color=Red&name=Griffindor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists());


        verify(facultyRepository, times(1)).findByColorOrNameIgnoreCase(anyString(), anyString());
    }

    @Test
    @DisplayName("Корректно выводит список студентов факультета")
    void findAllStudentsInFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Griffindor", "Red");
        Student student1 = new Student(1L, "andrew", 29);
        Student student2 = new Student(2L, "misha", 29);
        faculty.setStudents(List.of(student1, student2));
        Optional<Student> studentOptional = Optional.of(student1);
        Optional<Student> studentOptional2 = Optional.of(student2);
        Collection<Student> studentCollection = List.of(student1, student2);
        List<Optional<Student>> studentCollection2 = List.of(studentOptional, studentOptional2);
        System.out.println("faculty.getStudents() = " + faculty.getStudents());


        //when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        //when(facultyRepository.findById(faculty.getId()).get()).thenReturn(faculty);
        //when(facultyRepository.findById(faculty.getId()).get().getStudents()).thenReturn(faculty.getStudents());
        when(facultyRepository.findById(faculty.getId()).get().getStudents()).thenReturn(studentCollection);


        // test & check

        mockMvc.perform(get("/faculty/find/studentsInFaculty/" + faculty.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").exists());


        verify(facultyRepository, times(1)).findById(faculty.getId()).get().getStudents();

    }

}
