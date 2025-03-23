package ru.practicum.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FuturePlusTwoHoursValidator implements ConstraintValidator<FuturePlusTwoHours, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean isValid(String eventDate, ConstraintValidatorContext context) {
        if (eventDate == null) {
            return true;
        }
        try {
            LocalDateTime eventDateTime = LocalDateTime.parse(eventDate, FORMATTER);
            LocalDateTime minAllowedDateTime = LocalDateTime.now().plusHours(2);
            return eventDateTime.isAfter(minAllowedDateTime) || eventDateTime.isEqual(minAllowedDateTime);
        } catch (Exception e) {
            return false;
        }
    }
}
