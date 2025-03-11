package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Data.MovieRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService
{
    private final MovieRepo movieRepo;
}
