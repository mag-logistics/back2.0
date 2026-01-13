package brigada4.mpi.maglogisticabackend.controllers;

import brigada4.mpi.maglogisticabackend.payload.request.AuthRequest;
import brigada4.mpi.maglogisticabackend.payload.response.AuthResponse;
import brigada4.mpi.maglogisticabackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken()).body(response);
    }

}
