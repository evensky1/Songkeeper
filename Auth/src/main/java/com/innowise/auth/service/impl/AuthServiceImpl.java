package com.innowise.auth.service.impl;

import com.innowise.auth.dto.AuthRequest;
import com.innowise.auth.dto.AuthResponse;
import com.innowise.auth.dto.RegisterRequest;
import com.innowise.auth.entity.Role;
import com.innowise.auth.entity.RoleName;
import com.innowise.auth.entity.User;
import com.innowise.auth.repository.UserRepository;
import com.innowise.auth.security.JwtService;
import com.innowise.auth.service.AuthService;
import com.innowise.auth.service.RoleService;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {

        var user = User.builder()
            .email(registerRequest.email())
            .password(passwordEncoder.encode(registerRequest.password()))
            .roles(new HashSet<>(Collections.singletonList(
                roleService.findByName(RoleName.USER))))
            .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);

        return new AuthResponse(jwtToken);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        var authToken = new UsernamePasswordAuthenticationToken(
            authRequest.email(),
            authRequest.password()
        );

        authenticationManager.authenticate(authToken);

        var user = userRepository.findByEmail(authRequest.email())
            .orElseThrow(() -> new UsernameNotFoundException("Such email was not found"));

        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }
}
