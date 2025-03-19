package org.example.backend.DTOs;

public record MovieDto(
        String title,
        String director,
        int releaseYear
) {
}

