package org.aba2.calendar.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.aba2.calendar.common.validator.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Size(min = 6, max = 24, message = "비밀번호는 최소 6자, 최대 24자여야 합니다.")
@NotBlank(message = "Password는 필수입니다.")
public @interface Password {

    String message() default "비밀번호 형식에 맞지 않습니다. 대소문자와 숫자가 1글자 이상 들어가야하며 !@#$ 중 하나 이상 사용하셔야합니다.";

    String regexp() default "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$?]).{8,}$";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
