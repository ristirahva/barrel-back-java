package org.km.exception.handler;

import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Глобальный обработчик исключений приложения.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Сущность не найдена
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Попытка удалить родительскую сущность
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(DeleteParentEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDeleteParentEntityException(DeleteParentEntityException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
