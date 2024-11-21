package org.aba2.calendar.common.domain.user.business;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.token.business.TokenBusiness;
import org.aba2.calendar.common.domain.user.converter.UserConverter;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.domain.user.model.UserLoginRequest;
import org.aba2.calendar.common.domain.user.model.UserRegisterRequest;
import org.aba2.calendar.common.domain.user.model.UserResponse;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.Objects;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;

    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;

    public UserResponse login(Api<UserLoginRequest> req, HttpServletResponse response) {

        System.out.println("hi");
        var user = req.getBody();

        if (Objects.isNull(user) || user.getUserId() == null || user.getPassword() == null) {
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var userId = user.getUserId();
        var password = user.getPassword();


        System.out.println("hi");
        var userEntity = userService.login(userId, password);


        tokenBusiness.cookieSettingToken(userId, response, 15, 60);

        System.out.println("hi");
        return userConverter.toResponse(userEntity);
    }

    public UserResponse register(Api<UserRegisterRequest> userRegisterRequestApi) {

        if (Objects.isNull(userRegisterRequestApi) || Objects.isNull(userRegisterRequestApi.getBody())) {
            // 들어온 데이터가 없거나 body 데이터가 없으면 에러 발생
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var data = userRegisterRequestApi.getBody();

        var entity = userConverter.toEntity(data);

        var saveEntity = userService.register(entity);

        return userConverter.toResponse(saveEntity);
    }

    public UserResponse info(User user) {

        var userId = user.getId();

        var entity = userService.findByIdWithThrow(userId);

        return userConverter.toResponse(entity);

    }
}
