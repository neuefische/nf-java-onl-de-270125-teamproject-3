package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Data.MovieData;
import org.example.backend.Data.MovieRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService
{
    private final MovieRepo movieRepo;

    public List<MovieData> getMovies() {
        return movieRepo.findAll();
    }

    public Optional<MovieData> getMovie(String id) {
        return movieRepo.findById(id);
    }
}
