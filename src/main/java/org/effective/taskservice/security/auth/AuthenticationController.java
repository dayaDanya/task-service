package org.effective.taskservice.security.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.effective.taskservice.security.dto.AuthenticationRequest;
import org.effective.taskservice.security.dto.AuthenticationResponse;
import org.effective.taskservice.security.dto.RegisterRequest;
import org.effective.taskservice.util.exceptions.EmailNotUniqueException;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO добавить обработку неправильной регистрации и аутентификации
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse response = service.register(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {

            System.out.println("we r here");
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);


    }
}
