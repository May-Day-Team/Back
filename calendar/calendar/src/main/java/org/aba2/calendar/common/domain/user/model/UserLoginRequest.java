package org.aba2.calendar.common.domain.user.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.annotation.Password;
import org.aba2.calendar.common.annotation.UserId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @UserId
    private String userId;

    @Password
    private String password;

}
