package org.aba2.calendar.common.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.user.business.UserBusiness;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.domain.user.model.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/info")
    public Api<UserResponse> info(
            @UserSession
            User user
    ) {
        var response = userBusiness.info(user);

        return Api.OK(response);
    }

    @GetMapping("/logout")
    public Api<String> logout(
            @UserSession
            User user,
            HttpServletResponse response
    ) {
        userBusiness.logout(user, response);

        return Api.OK("로그아웃 되었습니다.");
    }

}
