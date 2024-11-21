package org.aba2.calendar.common.domain.token.helper;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.aba2.calendar.common.domain.token.ifs.TokenHelperIfs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String SECRET_KEY;

    @Value("${token.access-token.plus-hour}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${token.refresh-token.plus-hour}")
    private Long REFRESH_TOKEN_EXPIRATION;

    public String createAccessToken(String userId) {
        return createToken(userId, ACCESS_TOKEN_EXPIRATION);
    }

    public String createRefreshToken(String userId) {
        return createToken(userId, REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(String userId, long expiration) {

        var expiredLocalDatetime = LocalDateTime.now().plusHours(expiration);

        var expiredAt = Date.from(
                expiredLocalDatetime.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder() // parser() 대신 parserBuilder() 사용
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 만료된 토큰을 별도로 처리할 수 있음
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            // 다른 예외 처리
            return false;
        }
    }
}
