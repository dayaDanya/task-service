package org.effective.taskservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.effective.taskservice.domain.dto.AuthenticationRequest;
import org.effective.taskservice.domain.dto.AuthenticationResponse;
import org.effective.taskservice.domain.dto.RegisterRequest;
import org.effective.taskservice.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="AuthenticationController", description="Контроллер регистрации/авторизации")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid
            @RequestBody RegisterRequest request) {
        AuthenticationResponse response = service.register(request);
        return ResponseEntity.ok(response);

    }
    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет авторизовать пользователя"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid
            @RequestBody AuthenticationRequest request
    ) {

            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
    }
}
