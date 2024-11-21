package org.aba2.calendar.common.domain.token.business;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.token.service.TokenService;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;


    public String createAccessToken(String userId) {
        return tokenService.getAccessToken(userId);
    }

    public String createRefreshToken(String userId) {
        return tokenService.getRefreshToken(userId);
    }

    public String getUserIdFromToken(String token) {
        return tokenService.getUserId(token);
    }


    public boolean validationToken(String token) {
        return tokenService.validationToken(token);
    }

    public String getTokenFromCookie(HttpServletRequest request, String cookieName) {
        return tokenService.getTokenFromCookie(request, cookieName);
    }

    public void cookieSettingToken(String userId, HttpServletResponse response, int accessTime, int refreshTime) {
        var accessToken = createAccessToken(userId);
        var refreshToken = createRefreshToken(userId);

        var accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * accessTime);

        var refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * refreshTime);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }
}
