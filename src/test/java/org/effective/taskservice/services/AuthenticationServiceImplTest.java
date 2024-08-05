package org.effective.taskservice.services;

import org.effective.taskservice.domain.dto.AuthenticationRequest;
import org.effective.taskservice.domain.dto.RegisterRequest;
import org.effective.taskservice.domain.enums.Role;
import org.effective.taskservice.domain.models.Person;
import org.effective.taskservice.repositories.PersonRepo;
import org.effective.taskservice.security.details.PersonDetails;
import org.effective.taskservice.services.implementations.AuthenticationServiceImpl;
import org.effective.taskservice.services.implementations.JwtServiceImpl;
import org.effective.taskservice.util.exceptions.EmailNotUniqueException;
import org.effective.taskservice.util.exceptions.PersonNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    @Mock
    PersonRepo personRepo;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtServiceImpl jwtService;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    AuthenticationServiceImpl authService;
    @DisplayName(value = "Регистрация: уникальный email - возвращает токен")
    @Test
    void register_newEmail_returnsToken() {
        //given
        var request = new RegisterRequest("username",
                "email", "password");
        var userDetails =
                PersonDetails.builder()
                        .person(Person.builder()
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password("encoded")
                                .role(Role.USER)
                                .build())
                        .build();
        when(personRepo.findByEmail("email")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded");
        when(jwtService.generateToken(userDetails)).thenReturn("token");
        //when
        var response = authService.register(request);
        //then
        assertNotNull(response);
        verify(personRepo, times(1)).save(userDetails.getPerson());
    }
    @DisplayName(value = "Регистрация: неуникальный email - выбрасывает исключение")
    @Test
    void register_usedEmail_throwsException() {
        //given
        var request = new RegisterRequest("username",
                "email", "password");
        var userDetails =
                PersonDetails.builder()
                        .person(Person.builder()
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password("encoded")
                                .role(Role.USER)
                                .build())
                        .build();
        when(personRepo.findByEmail("email")).thenReturn(Optional.of(userDetails.getPerson()));
        //when
        var response = assertThrows(EmailNotUniqueException.class, () -> authService.register(request));
        //then
        assertNotNull(response);
        verify(personRepo, times(0)).save(userDetails.getPerson());
    }
    @DisplayName(value = "Аутентификация: ненайденный email - выбрасывает исключение")
    @Test
    void authenticate_withNewEmail_throwsException() {
        var request = new AuthenticationRequest(
                "email", "password");
        when(personRepo.findByEmail("email")).thenReturn(Optional.empty());
        var response = assertThrows(PersonNotFoundException.class, () -> authService.authenticate(request));
        assertNotNull(response);
    }
    @DisplayName(value = "Аутентификация: найденный email - возвращает токен")
    @Test
    void authenticate_withSavedEmail_returnsToken() {
        var request = new AuthenticationRequest(
                "email", "password");
        when(personRepo.findByEmail("email")).thenReturn(Optional.of(Person.builder()
                .email(request.getEmail())
                .password("encoded")
                .role(Role.USER)
                .build()));
        var response = assertDoesNotThrow(() -> authService.authenticate(request));
        assertNotNull(response);
    }
}