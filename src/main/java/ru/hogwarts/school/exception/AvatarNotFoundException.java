package ru.hogwarts.school.exception;

public class AvatarNotFoundException extends RuntimeException{
    public AvatarNotFoundException() {
    }

    public AvatarNotFoundException(String message) {
        super(message);
    }

    public AvatarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvatarNotFoundException(Throwable cause) {
        super(cause);
    }

    public AvatarNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
