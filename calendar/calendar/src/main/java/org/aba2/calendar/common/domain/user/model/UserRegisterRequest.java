package org.aba2.calendar.common.domain.user.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    private String userId;

    private String password;

    private String name;

    private LocalDateTime birthdate;

    private String email;

    private String phoneNumber;


}
