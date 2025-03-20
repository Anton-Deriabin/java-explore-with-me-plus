package ru.practicum.exception;

import org.springframework.http.HttpStatus;
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
        return new ErrorResponse("CONFLICT", "Name already exists", e.getMessage());
    }

    @ExceptionHandler(CategoryNotEmptyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCategoryNotEmpty(final CategoryNotEmptyException e) {
        return new ErrorResponse("CONFLICT", "Category is not empty", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(final MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    Object rejectedValue = ((FieldError) error).getRejectedValue();
                    return "Field: " + fieldName + ". Error: " + errorMessage + ". Value: " + rejectedValue;
                })
                .toList();
        return new ErrorResponse(
                "BAD_REQUEST",
                "Incorrectly made request.",
                errorMessages.get(0)
        );
    }

}
