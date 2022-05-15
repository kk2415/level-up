package com.together.levelup.security;

import com.together.levelup.domain.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    private static final String SECRET_KEY = "NMA8JPctFuna59f5";

    /**
     *  { // header
     *      "alg" : "HS521",
     *  }.
     *
     *  { // payload
     *      "sub" : "40288093784915d20174916a40c0001",
     *      "iis" : "demo app",
     *      "iat" : 15973367,
     *      "exp" : 15973367,
     *  }.
     *
     *  Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNdJJNWKRv50_l7bPLQPwhMobT4BOG6Q3JYjhDrKFLBSaUxz
     * */

    public String create(Member member) {
        Date expireDate = Date.from(Instant.now()
                .plus(1, ChronoUnit.DAYS));

        //JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) //header 및 전자서명 SECRET_KEY
                .setSubject(String.valueOf(member.getId())) //sub
                .setIssuer("demo app") //iis
                .setIssuedAt(new Date()) //iat
                .setExpiration(expireDate) //exp
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
