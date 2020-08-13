package org.example.store.service.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        this("Resource with id=" + id + " does not exist.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
