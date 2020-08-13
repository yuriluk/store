package org.example.store.exception;

public class ControllerException extends RuntimeException {
    public ControllerException() {
        super();
    }

    public ControllerException(String message) {
        super(message);
    }
}
