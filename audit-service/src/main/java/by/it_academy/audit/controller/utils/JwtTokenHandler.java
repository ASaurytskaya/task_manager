package by.it_academy.audit.controller.utils;

import by.it_academy.audit.config.properites.JwtProperty;
import by.it_academy.audit.core.UserRole;
import by.it_academy.audit.core.UserStatus;
import by.it_academy.audit.service.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtTokenHandler {
    private final JwtProperty property;

    public JwtTokenHandler(JwtProperty property) {
        this.property = property;
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public UserRole getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();
        String role = claims.get("role", String.class);
        return UserRole.valueOf(role);
    }

    public UserStatus getStatus(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();
        String status = claims.get("status", String.class);
        return UserStatus.valueOf(status);
    }

    public UUID getUUID(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();
        String id = claims.get("uuid", String.class);
        return UUID.fromString(id);
    }

    public String getFio(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get("fio", String.class);
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
