package com.levelup.member.util.jwt;

import com.levelup.common.exception.AuthenticationErrorCode;
import com.levelup.common.exception.CustomAuthenticationException;
import io.jsonwebtoken.*;
import lombok.Getter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Component
public class TokenProvider {

    private Date expireDate;

    public String createAccessToken(Long memberId) {
        Pair<String, Key> key = JwtKey.getRandomKey();
        Claims subject = Jwts.claims().setSubject(String.valueOf(memberId)); //subject
        this.expireDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)); //ExpiredJwtException

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) //header

                .setClaims(subject) //subject
                .setIssuer("level up") //iis
                .setIssuedAt(new Date()) //iat
                .setExpiration(expireDate) //exp

                .signWith(key.getSecond()) //sig
                .compact();
    }

    public String createAccessToken(String email) {
        Pair<String, Key> key = JwtKey.getRandomKey();
        Claims subject = Jwts.claims().setSubject(String.valueOf(email)); //subject
        this.expireDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)); //ExpiredJwtException

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) //header

                .setClaims(subject) //subject
                .setIssuer("level up") //iis
                .setIssuedAt(new Date()) //iat
                .setExpiration(expireDate) //exp

                .signWith(key.getSecond()) //sig
                .compact();
    }
    public AuthenticationErrorCode validateToken(String token) throws RuntimeException {
        try {
            Jwts.parserBuilder()
                    .setSigningKeyResolver(SigningKeyResolver.instance) //JWT 만들었을 때 사용했던 비밀키를 넣어줘야됨
                    .build()
                    .parseClaimsJws(token);
            return AuthenticationErrorCode.SUCCESS;

        } catch (ExpiredJwtException e) {
            return AuthenticationErrorCode.EXPIRED_TOKEN;
        } catch (SignatureException e) {
            return AuthenticationErrorCode.INVALID_SIGNATURE;
        } catch (IllegalArgumentException e) {
            return AuthenticationErrorCode.NULL_TOKEN;
        } catch (MalformedJwtException e) {
            return AuthenticationErrorCode.INVALID_TOKEN;
        }
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(com.levelup.member.util.jwt.SigningKeyResolver.instance) //JWT 만들었을 때 사용했던 비밀키를 넣어줘야됨
                .build()
                .parseClaimsJws(token)//토큰 파싱 -> 토큰 검증 실패 시 에외 발생 (SignatureException) JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.
                .getBody()
                .getSubject();
    }

    public Claims getBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(com.levelup.member.util.jwt.SigningKeyResolver.instance)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(com.levelup.member.util.jwt.SigningKeyResolver.instance)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public Date getIssuedAt(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt();
    }
}
