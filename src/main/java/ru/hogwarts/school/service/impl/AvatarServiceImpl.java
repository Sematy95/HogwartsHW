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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.lang.Boolean.*;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    @Value("${path.to.avatars.folder")
    private String avatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(Boolean.parseBoolean(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
            InputStream inputStream = avatarFile.getInputStream();
            OutputStream outputStream = Files.newOutputStream(filePath,CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(inputStream,1024);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream,1024);
        ){
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForDB(filePath);
        avatarRepository.save(avatar);

    }
    private byte[] generateDataForDB(Path filePath) throws IOException {
        try (
                InputStream inputStream=Files.newInputStream(filePath);
                BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream,1024);
                ByteArrayOutputStream byteArrayInputStream= new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bufferedInputStream);
        }




    }


    @Override
    public Avatar findAvatar(Long studentId) {
        return null;
    }
}
