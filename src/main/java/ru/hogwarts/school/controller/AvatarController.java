package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.dto.AvatarView;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long uploadAvatar(@RequestParam("studentId") long studentId,
                             @RequestBody MultipartFile file) throws IOException {

        return avatarService.uploadAvatar(studentId, file);
    }

    @GetMapping("/get/from-db")
    public ResponseEntity<byte[]> getAvatarFromDb(@RequestParam("studentId") long studentId) {
        Avatar avatar = avatarService.getAvatarFromDb(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());

    }

    @GetMapping("/get/from-local")
    public ResponseEntity<byte[]> getAvatarFromLocal(@RequestParam("studentId") long studentId) throws IOException {
        AvatarView avatarView = avatarService.getAvatarFromLocal(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(avatarView.getMediaType())
                .body(avatarView.getContent());
    }

}
