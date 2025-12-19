package org.km.exception;

/**
 * Базовое исключение приложения
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}
