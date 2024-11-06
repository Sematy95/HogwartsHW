package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.service.AvatarService;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long uploadAvatar(@RequestParam ("studentId") long studentId, @RequestBody MultipartFile file) {
        return 0l;
    }

    @GetMapping("/get/from-db")
    public byte[] getAvatarFromDb(@RequestParam ("studentId") long studentId) {
        return null;
    }

    @GetMapping("/get/from-local")
    public byte[] getAvatarFromLocal(@RequestParam ("studentId") long studentId) {
        return null;
    }

}
