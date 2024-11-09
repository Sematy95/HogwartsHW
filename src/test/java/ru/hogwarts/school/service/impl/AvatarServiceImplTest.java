package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {

    private static final String TEST_PATH = "src/test/images";


    AvatarRepository avatarRepository  = Mockito.mock(AvatarRepository.class);


    StudentRepository studentRepository = Mockito.mock(StudentRepository.class);


    AvatarServiceImpl avatarService = new AvatarServiceImpl(avatarRepository,
            studentRepository,
            Path.of(TEST_PATH));

    @AfterEach
    public void setDown() throws IOException {

        Files.walk(Path.of(TEST_PATH))
                .sorted(Comparator.reverseOrder())
                .forEach(x -> {
                    try {
                        Files.delete(x);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    void uploadAvatar() throws IOException {
        Student student = new Student("Oleg", 29);
        Avatar avatar = new Avatar();
        Long id = nextLong(1,100);
        avatar.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(avatarRepository.save(any(Avatar.class))).thenReturn(avatar);

        MockMultipartFile multipartFile = new MockMultipartFile(
                "test",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]);

        //test
        avatarService.uploadAvatar(id, multipartFile);

        //check
        assertThat(avatar.getId()).isEqualTo(id);
        assertThat(Files.walk(Path.of(TEST_PATH))).hasSize(2);


    }

    @Test
    void getAvatarFromDb() {
    }

    @Test
    void getAvatarFromLocal() {
    }
}