package by.it_academy.report.config;


import by.it_academy.report.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
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
                                response.sendError(401, "Для выполнения запроса на данный адрес требуется передать токен авторизации.");
                            }
                    )
                    .accessDeniedHandler((request, response, ex) -> {
                        response.setStatus(
                                HttpServletResponse.SC_FORBIDDEN
                        );
                        response.sendError(403, "Данному токену авторизации запрещено выполнять запрос на данный адрес.");
                    });
        });


        http.authorizeHttpRequests(requests -> requests
                .anyRequest().hasAnyRole("ADMIN")
        );

        http.addFilterBefore(
                filter, UsernamePasswordAuthenticationFilter.class
        );


        return http.build();
    }
}