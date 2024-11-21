package org.aba2.calendar.common.domain.user.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;

    private String password;

    private String name;

    private LocalDateTime birthdate;

    private String email;

    private String phoneNumber;

    private LocalDateTime createAt;

}
