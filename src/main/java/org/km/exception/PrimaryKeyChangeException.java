package org.km.exception;

public class PrimaryKeyChangeException extends CrudException {
    public PrimaryKeyChangeException(String message) {
        super(message);
    }
}
