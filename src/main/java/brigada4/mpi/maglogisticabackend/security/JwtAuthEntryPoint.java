package brigada4.mpi.maglogisticabackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");

        // Добавляем более информативное сообщение
        String message = getErrorMessage(authException, request);
        body.put("message", message);
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

    private String getErrorMessage(AuthenticationException ex, HttpServletRequest request) {
        if (ex instanceof BadCredentialsException) {
            String invalidToken = (String) request.getAttribute("invalid");
            String userNotFound = (String) request.getAttribute("not_found");

            if (invalidToken != null) {
                return "Недействительный JWT-токен";
            } else if (userNotFound != null) {
                return "Пользователь не найден";
            } else {
                return "Неверные данные";
            }
        } else if (ex instanceof AuthenticationCredentialsNotFoundException) {
            String expired = (String) request.getAttribute("expired");
            if (expired != null) {
                return "JWT-токен устарел";
            }
        }
        return ex.getMessage();
    }
}