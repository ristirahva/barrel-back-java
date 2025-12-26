package org.km.exception.handler;

import org.hibernate.exception.GenericJDBCException;
import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;

import org.km.exception.PrimaryKeyChangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Глобальный обработчик исключений приложения.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
     * Попытка удалить родительскую сущность.
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

    /**
     * Попытка изменить первичный ключ.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(PrimaryKeyChangeException.class)
    public ResponseEntity<ErrorResponse> handlePrimaryKeyChangeException(PrimaryKeyChangeException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработка исключений СУБД.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex) {
        log.error("Статус ошибки SQL: {}", ex.getSQLState());
        var httpStatus = ex.getSQLState().equals("P0002") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        var errorResponse = new ErrorResponse(httpStatus.value(), ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
