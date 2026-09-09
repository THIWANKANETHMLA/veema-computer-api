package lk.ijse.veema_computer.security;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.veema_computer.constant.CommonResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JsonMapper jsonMapper
    ) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/register")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .requestCache(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR)
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/auth/register"
                        )
                        .permitAll()
                        .anyRequest()
                        .denyAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(
                                (request, response, exception) ->
                                        writeError(
                                                response,
                                                jsonMapper,
                                                HttpStatus.UNAUTHORIZED,
                                                "Authentication is required"
                                        )
                        )
                        .accessDeniedHandler(
                                (request, response, exception) ->
                                        writeError(
                                                response,
                                                jsonMapper,
                                                HttpStatus.FORBIDDEN,
                                                "Access is denied"
                                        )
                        )
                );

        return http.build();
    }

    private void writeError(
            HttpServletResponse response,
            JsonMapper jsonMapper,
            HttpStatus status,
            String message
    ) throws IOException {

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        CommonResponse<Object> error = new CommonResponse<>(
                status.value(),
                null,
                message
        );

        response.getWriter().write(
                jsonMapper.writeValueAsString(error)
        );
    }
}