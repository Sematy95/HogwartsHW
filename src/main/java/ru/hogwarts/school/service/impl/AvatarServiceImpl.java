package ru.hogwarts.school.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {


    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    @Value("${path.to.avatars.folder")
    private Path avatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public long uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student is no found"));
        savaAvatarLocal(avatarFile);

        Path path = savaAvatarLocal(avatarFile);

        System.out.println("path.toString() = " + path.toString());
        Avatar avatar = new Avatar(studentId,
                path.toString(),
                avatarFile.getSize(),
                avatarFile.getContentType(),
                avatarFile.getBytes(), student);
        return avatarRepository.save(avatar).getId();

    }

    private Path savaAvatarLocal(MultipartFile avatarFile) throws IOException {
        createDirectoryIfNotExist();
        if (avatarFile.getOriginalFilename()==null) {
            throw new RuntimeException("File is empty");
        }
        Path path = Path.of(avatarsDir.toString(), UUID.randomUUID()+getExtension(avatarFile.getOriginalFilename()));
        Files.write(path, avatarFile.getBytes());

//        try (BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(avatarFile.getBytes()));
//             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.toFile()))) {
//            bos.write(bis.readAllBytes());
//        } catch (Exception ignored) {
//        }

        return path;

    }

    private String getExtension (String path) {
        return path.substring(path.lastIndexOf("."));
    }

    private void createDirectoryIfNotExist() throws IOException {
        if (Files.notExists(avatarsDir)) {
            Files.createDirectory(avatarsDir);

        }
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        return null;
    }
}
