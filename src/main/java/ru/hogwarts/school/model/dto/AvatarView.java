package ru.hogwarts.school.model.dto;

import org.springframework.http.MediaType;

public class AvatarView {
    private final MediaType mediaType;
    private final byte[] content;

    public AvatarView(MediaType mediaType, byte[] content) {
        this.mediaType = mediaType;
        this.content = content;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public byte[] getContent() {
        return content;
    }
}
