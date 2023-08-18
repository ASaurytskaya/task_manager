package by.it_academy.user.controller.utils;

import by.it_academy.user.config.properites.JWTProperty;
import by.it_academy.user.service.exceptions.TokenInvalidException;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {
    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }

    public String generateAccessToken(UserDetails user) {
        return generateAccessToken(user.getUsername());
    }

    public String generateAccessToken(String name) {
        return Jwts.builder()
                .setSubject(name)
                .setIssuer(property.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, property.getSecret())
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
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
