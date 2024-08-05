package org.effective.taskservice.services;

import org.effective.taskservice.domain.dto.AuthenticationRequest;
import org.effective.taskservice.domain.dto.AuthenticationResponse;
import org.effective.taskservice.domain.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
