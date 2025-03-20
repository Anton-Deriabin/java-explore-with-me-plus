package ru.practicum.exception;

public class NameExistException extends RuntimeException {
    public NameExistException(String message) {
        super(message);
    }
}
