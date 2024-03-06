package org.effective.taskservice.services;

import lombok.RequiredArgsConstructor;
import org.effective.taskservice.domain.enums.Role;
import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.repositories.PersonRepo;
import org.effective.taskservice.security.details.PersonDetails;
import org.effective.taskservice.domain.dto.AuthenticationRequest;
import org.effective.taskservice.domain.dto.AuthenticationResponse;
import org.effective.taskservice.domain.dto.RegisterRequest;
import org.effective.taskservice.util.exceptions.EmailNotUniqueException;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
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
            throw new EmailNotUniqueException(request.getEmail());
        }
        var userDetails =
                PersonDetails.builder()
                        .person(Person.builder()
                                .username(request.getUsername())
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

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new PersonNotFoundException(request.getEmail()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(PersonDetails.builder()
                .person(user)
                .build());
        return AuthenticationResponse.builder()
                .response(jwtToken)
                .build();
    }

}
