package org.aba2.calendar.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aba2.calendar.common.annotation.UserId;

import java.util.regex.Pattern;

public class UserIdValidator implements ConstraintValidator<UserId, String> {

    private String regexp;

    @Override
    public void initialize(UserId constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.matches(value, regexp);
    }
}
