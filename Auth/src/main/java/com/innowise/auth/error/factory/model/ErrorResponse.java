package com.innowise.auth.error.factory.model;

import java.time.Instant;

public record ErrorResponse(
    int statusCode,
    String message,
    Instant timestamp
) { }
