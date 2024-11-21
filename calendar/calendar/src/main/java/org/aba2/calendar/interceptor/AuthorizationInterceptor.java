package org.aba2.calendar.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aba2.calendar.common.domain.token.business.TokenBusiness;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.errorcode.TokenErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Autorization Integerceptor url : {}", request.getRequestURI());

        // WEB, Chrome 경우 GET, POST OPTIONS = pass
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // js, html, png, resource 요청 = pass
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // 쿠키에서 토큰 가져오기
        String accessToken = tokenBusiness.getTokenFromCookie(request, "accessToken");
        String refreshToken = tokenBusiness.getTokenFromCookie(request, "refreshToken");

        // 액세스 토큰 검증
        if (accessToken != null && tokenBusiness.validationToken(accessToken)) {
            var userId = tokenBusiness.getUserIdFromToken(accessToken);
            request.setAttribute("userId", userId); // HttpServletRequest에 userId를 저장
            return true;
        }

        // 액세스 토큰이 유효하지 않을 경우, 리프레시 토큰 검증
        if (refreshToken != null && tokenBusiness.validationToken(refreshToken)) {
            String userId = tokenBusiness.getUserIdFromToken(refreshToken);

            // 새로운 액세스 토큰 생성
            String newAccessToken = tokenBusiness.createAccessToken(userId);

            // 새 액세스 토큰을 쿠키로 전달
            Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60 * 15); // 15분

            response.addCookie(accessTokenCookie);

            request.setAttribute("userId", userId); // HttpServletRequest에 userId를 저장
            return true;
        }

        // 둘 다 유효하지 않은 경우
        throw new ApiException(ErrorCode.BAD_REQUEST, "다시 로그인을 해주세요");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
