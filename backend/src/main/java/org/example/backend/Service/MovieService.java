package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTOs.MovieDto;
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
    private final IdService idService;

    public List<MovieData> getMovies() {
        return movieRepo.findAll();
    }

    public Optional<MovieData> getMovie(String id) {
        return movieRepo.findById(id);
    }

    public boolean deleteMovie(String id)
    {
        if (movieRepo.existsById(id))
        {
            movieRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public MovieData saveMovie(MovieDto newMovie){
        String id = idService.randomId();
        MovieData movieToSave = new MovieData(id, newMovie.title(), newMovie.director(), newMovie.releaseYear());
        return movieRepo.save(movieToSave);
    }
}
