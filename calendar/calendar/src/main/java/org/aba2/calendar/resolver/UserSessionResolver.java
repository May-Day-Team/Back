package org.aba2.calendar.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.annotation.UserSession;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크

        // 1. 어노테이션이 있는지 체크
        var annotation = parameter.hasParameterAnnotation(UserSession.class);
        // 2. 클래스가 있는지 체크
        var parameterType = parameter.getParameterType().equals(User.class);

        return annotation && parameterType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // supportsParameter True 반환시 여기 실행

        // request context holder 에서 정보 찾아오기
        var requestContext = RequestContextHolder.getRequestAttributes();

        assert requestContext != null;
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        assert userId != null;
        var userEntity = userService.findByIdWithThrow(userId.toString());

        // 사용자 정보 세팅
        return User.builder()
                .id(userEntity.getUserId())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .build()
                ;
    }
}

