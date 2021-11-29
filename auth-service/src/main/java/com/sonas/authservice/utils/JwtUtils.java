package com.sonas.authservice.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "secretKey:123456";

    public static final long TOKEN_EXPIRE_TIME = 7200 * 1000;

    public String createJwt(Map<String, Object> claims, String subject) {
            return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME * 1000))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createJwt(claims, userDetails.getUsername());
    }
}

