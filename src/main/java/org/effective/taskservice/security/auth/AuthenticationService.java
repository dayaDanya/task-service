package org.effective.taskservice.security.auth;

import lombok.RequiredArgsConstructor;
import org.effective.taskservice.domain.enums.Role;
import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.repositories.PersonRepo;
import org.effective.taskservice.security.config.JwtService;
import org.effective.taskservice.security.dto.AuthenticationRequest;
import org.effective.taskservice.security.dto.AuthenticationResponse;
import org.effective.taskservice.security.dto.RegisterRequest;
import org.effective.taskservice.util.ex.EmailNotUniqueException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PersonRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailNotUniqueException("Email is already used");
        }
        var userDetails =
                PersonDetails.builder()
                        .person(Person.builder()
                                .username(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build())
                        .build();
        repository.save(userDetails.getPerson());
        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(PersonDetails.builder()
                .person(user)
                .build());
        return AuthenticationResponse.builder()
                .response(jwtToken)
                .build();
    }

}
