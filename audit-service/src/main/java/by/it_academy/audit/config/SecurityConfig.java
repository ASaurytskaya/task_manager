package by.it_academy.audit.config;

import by.it_academy.audit.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.exceptionHandling((exceptionHandling) -> {
            exceptionHandling.authenticationEntryPoint(
                    (request, response, ex) -> {
                        response.setStatus(
                                HttpServletResponse.SC_UNAUTHORIZED
                        );
                    }
            )
                    .accessDeniedHandler((request, response, ex) -> {
                        response.setStatus(
                                HttpServletResponse.SC_FORBIDDEN
                        );
                    });
        });

        http.authorizeHttpRequests(requests -> requests

                .requestMatchers(HttpMethod.GET, "/api/v1/audit", "/api/v1/audit/**").
                    hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.addFilterBefore(
                filter, UsernamePasswordAuthenticationFilter.class
        );


        return http.build();
    }
}