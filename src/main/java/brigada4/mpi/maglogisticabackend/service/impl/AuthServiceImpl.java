package brigada4.mpi.maglogisticabackend.service.impl;

import brigada4.mpi.maglogisticabackend.exception.InvalidCredentialsException;
import brigada4.mpi.maglogisticabackend.payload.request.AuthRequest;
import brigada4.mpi.maglogisticabackend.payload.response.AuthResponse;
import brigada4.mpi.maglogisticabackend.security.CustomUserDetailsImpl;
import brigada4.mpi.maglogisticabackend.security.JwtUtils;
import brigada4.mpi.maglogisticabackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return new AuthResponse(jwt, userDetails.getId(), roles);

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Неправильный логин или пароль");
        } catch (DisabledException e) {
            throw new InvalidCredentialsException("Аккаунт отключен");
        } catch (LockedException e) {
            throw new InvalidCredentialsException("Аккаунт пользователя заблокирован");
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Аутентификация не удалась");
        }
    }
}
