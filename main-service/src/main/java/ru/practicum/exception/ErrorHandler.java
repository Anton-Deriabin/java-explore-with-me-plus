package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        return new ErrorResponse("NOT_FOUND", e.getReason(), e.getMessage());
    }

    @ExceptionHandler(NameExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNameExist(final NameExistException e) {
        return new ErrorResponse("CONFLICT", e.getReason(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(final MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    Object rejectedValue = ((FieldError) error).getRejectedValue();
                    return "Field: " + fieldName + ". Error: " + errorMessage + ". Value: " + rejectedValue;
                })
                .toList();
        String firstErrorMessage = errorMessages.get(0);
        if (firstErrorMessage.contains("eventDate") &&
                firstErrorMessage.contains("Event date must be at least two hours in the future")) {
            ErrorResponse response = new ErrorResponse(
                    "CONFLICT",
                    "Conflict with event date",
                    firstErrorMessage
            );
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        ErrorResponse response = new ErrorResponse(
                "BAD_REQUEST",
                "Incorrectly made request.",
                firstErrorMessage
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
