package org.example.backend.DTOs;

import java.time.Instant;

public record CustomErrorMessage(String message, Instant timestamp) {}