package ru.practicum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FuturePlusTwoHoursValidator.class)
public @interface FuturePlusTwoHours {
    String message() default "Event date must be at least two hours in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
