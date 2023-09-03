package by.it_academy.audit.controller.filter;

import by.it_academy.audit.controller.utils.JwtTokenHandler;
import by.it_academy.audit.core.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtTokenHandler jwtHandler;

    public JwtFilter(JwtTokenHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(AUTH_HEADER);
        if (isEmpty(header) || !header.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if(!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        CustomUserDetails.CustomUserDetailsBuilder builder = new CustomUserDetails.CustomUserDetailsBuilder();
        UserDetails userDetails = builder.
                            setId(jwtHandler.getUUID(token)).
                            setMail(jwtHandler.getUsername(token)).
                            setFio(jwtHandler.getFio(token)).
                            setUserRole(jwtHandler.getRole(token)).
                            setUserStatus(jwtHandler.getStatus(token)).build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails == null ? List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
