package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Корректно добавляет студента в БД")
    void add() {

        Student student = new Student("Санечек", 34);

        //test
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student/add", student, Student.class);
        System.out.println("studentResponseEntity = " + studentResponseEntity);

        assertThat(studentResponseEntity).isNotNull();
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody()).isNotNull();
        assertThat(studentResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
        assertThat(studentResponseEntity.getBody().getId()).isNotNull();
        Student actual = studentRepository.findById(studentResponseEntity.getBody().getId()).orElseGet(org.junit.jupiter.api.Assertions::fail);
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);


    }

    @Test
    @DisplayName("Корректно находит студента из БД")
    void findStudent() {

        Student student = new Student("Санечек2", 29);
        studentRepository.save(student);

        //test
        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity
                ("http://localhost:" + port + "/student/find/{id}",
                        Student.class,
                        student.getId());
        assertThat(studentResponseEntity).isNotNull();
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody()).isNotNull();
        assertThat(studentResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
        assertThat(studentResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("Корректно обновляет студента в БД")
    void editStudent() {
        Student student = new Student("Санечек4", 35);
        studentRepository.save(student);
        Student studentUpd = new Student(student.getId(), "Санечек5", 29);

        //test
        restTemplate.put("http://localhost:" + port + "/student/update/{id}", studentUpd, student.getId());


        //check
        Student actual = studentRepository.findById(student.getId()).orElseGet(org.junit.jupiter.api.Assertions::fail);
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(studentUpd);


    }

    @Test
    @DisplayName("Корректно удаляет студента")
    void deleteStudent() {
        Student student = new Student("Санечек3", 32);
        studentRepository.save(student);

        //test
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/delete/{id}",
                HttpMethod.DELETE,
                null,
                Student.class,
                student.getId());

        //check

        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentRepository.findAll()).doesNotContain(student);

    }

    @Test
    @DisplayName("Корректно выводит список всех студентов")
    void findAll() {

        Student student1 = new Student("Санечек16", 16);
        Student student2 = new Student("Санечек17", 17);
        studentRepository.save(student1);
        studentRepository.save(student2);


        ResponseEntity<Collection<Student>> studentsResponseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/student/find/all"
                        , HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Student>>() {
                        });

        assertThat(studentsResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentsResponseEntity).isNotNull();
        assertThat(studentsResponseEntity.getBody().contains(student1) && studentsResponseEntity.getBody().contains(student2));

    }

    @Test
    @DisplayName("Корректно ищет студентов по возрасту")

    void findByAge() {
        Student student1 = new Student("Санечек49", 49);
        Student student2 = new Student("Санечек50", 49);
        Student student3 = new Student("Санечек51", 55);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        int age = 49;

        ResponseEntity<Collection<Student>> studentsResponseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/student/find/age/{age}"
                        , HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Student>>() {
                        }, age);
        assertThat(studentsResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentsResponseEntity).isNotNull();
        assertThat(studentsResponseEntity.getBody().contains(student1) && studentsResponseEntity.getBody().contains(student2) && (!studentsResponseEntity.getBody().contains(student3)));
    }

    @Test
    @DisplayName("Корректно ищет студентов по возрастдиапазону возраста")

    void findByAgeBetween() {
        Student student1 = new Student("Санечек49", 49);
        Student student2 = new Student("Санечек50", 49);
        Student student3 = new Student("Санечек51", 55);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        ResponseEntity<Collection<Student>> studentsResponseEntity = restTemplate.exchange
                ("http://localhost:" + port + "/student/find/age/between?ageMin=48&ageMax=50"
                        , HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Student>>() {
                        });
        assertThat(studentsResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentsResponseEntity).isNotNull();
        assertThat(studentsResponseEntity.getBody().contains(student1) && studentsResponseEntity.getBody().contains(student2) && (!studentsResponseEntity.getBody().contains(student3)));

    }

}