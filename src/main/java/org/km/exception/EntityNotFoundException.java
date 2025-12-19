package org.km.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends CrudException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
