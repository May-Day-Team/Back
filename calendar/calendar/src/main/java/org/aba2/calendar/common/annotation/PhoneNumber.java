package org.aba2.calendar.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.aba2.calendar.common.validator.PhoneNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Size(min = 7, max = 11, message = "전화번호 양식에 맞지 않습니다.")
@NotBlank(message = "전화번호는 필수 값입니다.")
public @interface PhoneNumber {

    String message() default "전화번호 양식에 맞지 않습니다.";

    String regexp() default "^(\\d*)$";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
