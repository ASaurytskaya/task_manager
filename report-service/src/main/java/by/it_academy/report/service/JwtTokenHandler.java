package by.it_academy.report.service;

import by.it_academy.report.config.properties.JWTProperty;
import by.it_academy.report.core.UserRole;
import by.it_academy.report.core.UserStatus;
import by.it_academy.report.core.dto.CustomUserDetails;
import by.it_academy.report.service.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {
    private static final String BEARER = "Bearer ";

    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }

    public String getJwtToken(CustomUserDetails user) {
        return BEARER + generateAccessToken(user);
    }

    public String generateAccessToken(CustomUserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getUserRole());
        claims.put("status", user.getUserStatus());
        claims.put("uuid", user.getId());
        claims.put("fio", user.getFio());

        return generateAccessToken(user.getUsername(), claims);
    }

    public String generateAccessToken(String name, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(name)
                .setIssuer(property.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, property.getSecret()).compact();
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
