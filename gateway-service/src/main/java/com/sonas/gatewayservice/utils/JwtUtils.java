package com.sonas.gatewayservice.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private String jwtSecret = "secretKey:123456";

    public long jwtExpDate = 7200 * 1000;


    public boolean validateToken(String token) {
        boolean isValid = false;
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            isValid = true;
            return isValid;
        } catch (SignatureException e) {
            logger.error("Signature invalid", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token expired", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Jwt Claims is empty", e.getMessage());
        }
        return isValid;
    }
}
