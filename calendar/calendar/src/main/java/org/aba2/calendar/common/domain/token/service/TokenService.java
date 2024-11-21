package org.aba2.calendar.common.domain.token.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.token.helper.JwtTokenHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenHelper jwtTokenHelper;


    public String getAccessToken(String id) {
        return jwtTokenHelper.createAccessToken(id);
    }

    public String getRefreshToken(String id) {
        return jwtTokenHelper.createRefreshToken(id);
    }

    public String getUserId(String token) {
        return jwtTokenHelper.getUserIdFromToken(token);
    }

    public boolean validationToken(String token) {
        return jwtTokenHelper.validateToken(token);
    }

    public String getTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
