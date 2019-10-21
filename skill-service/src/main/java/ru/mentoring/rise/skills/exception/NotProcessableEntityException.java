package ru.mentoring.rise.skills.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NotProcessableEntityException extends RuntimeException {

    public NotProcessableEntityException(String message) {
        super(message);
    }
}
