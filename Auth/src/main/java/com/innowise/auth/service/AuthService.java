package com.innowise.auth.service;

import com.innowise.auth.dto.AuthRequest;
import com.innowise.auth.dto.AuthResponse;
import com.innowise.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse authenticate(AuthRequest authRequest);
}
