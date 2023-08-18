package by.it_academy.audit.controller.utils;

import by.it_academy.audit.config.properites.JwtProperty;
import by.it_academy.audit.service.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {
    private final JwtProperty property;

    public JwtTokenHandler(JwtProperty property) {
        this.property = property;
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new TokenInvalidException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new TokenInvalidException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new TokenInvalidException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new TokenInvalidException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new TokenInvalidException("JWT claims string is empty");
        }
    }
}
