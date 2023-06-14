package com.innowise.auth.dto;

public record AuthRequest(
    String email,
    String password
) {

}
