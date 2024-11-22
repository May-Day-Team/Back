package org.aba2.calendar.common.domain.user.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.annotation.Password;
import org.aba2.calendar.common.annotation.PhoneNumber;
import org.aba2.calendar.common.annotation.UserId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @UserId
    private String userId;

    @Password
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private LocalDateTime birthdate;

    @Email
    private String email;

    @PhoneNumber
    private String phoneNumber;


}
