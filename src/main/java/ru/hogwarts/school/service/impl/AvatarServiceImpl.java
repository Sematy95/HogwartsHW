package ru.hogwarts.school.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.dto.AvatarView;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {


    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final Path avatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             StudentRepository studentRepository,
                             @Value("${image.path}") Path avatarsDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public long uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student is no found"));

        Path path = savaAvatarLocal(avatarFile);

        Avatar avatar = new Avatar(
                path.toString(),
                avatarFile.getSize(),
                avatarFile.getContentType(),
                avatarFile.getBytes(),
                student
        );
        avatarRepository.findByStudentId(studentId)
                .ifPresent((x) -> {
                    try {
                        Files.delete(Path.of(x.getFilePath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    avatar.setId(x.getId());
                });

        return avatarRepository.save(avatar).getId();

    }

    private Path savaAvatarLocal(MultipartFile avatarFile) throws IOException {

        createDirectoryIfNotExist();

        if (avatarFile.getOriginalFilename() == null) {
            throw new RuntimeException("File is empty");
        }
        Path path = Path.of(avatarsDir.toString(), UUID.randomUUID() + getExtension(avatarFile.getOriginalFilename()));

        avatarFile.transferTo(path);

        return path;
    }

    private String getExtension(String path) {
        return path.substring(path.lastIndexOf("."));
    }

    private void createDirectoryIfNotExist() throws IOException {
        if (Files.notExists(avatarsDir)) {
            Files.createDirectory(avatarsDir);
        }
    }

    @Override
    public Avatar getAvatarFromDb(Long studentId) {

        return avatarRepository.findByStudentId(studentId).orElseThrow(RuntimeException::new);
    }

    @Override
    public AvatarView getAvatarFromLocal(Long studentId) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId(studentId)
                .orElseThrow(RuntimeException::new);
        byte[] bytes = Files.readAllBytes(Path.of(avatar.getFilePath()));
        return new AvatarView(MediaType.parseMediaType(avatar.getMediaType()), bytes);

    }

    @Override
    public List<Avatar> getAllAvatar(int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

}
