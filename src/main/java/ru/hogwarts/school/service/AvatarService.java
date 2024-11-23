package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.dto.AvatarView;

import java.io.IOException;

public interface AvatarService {

    long uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    Avatar getAvatarFromDb(Long studentId);

    AvatarView getAvatarFromLocal(Long studentId) throws IOException;

}
