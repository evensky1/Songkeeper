package com.innowise.auth.dto;

public record RegisterRequest(
    String email,
    String password
) {

}
