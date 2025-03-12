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

    public boolean deleteMovie(String id)
    {
        if (movieRepo.existsById(id))
        {
            movieRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public MovieData saveMovie(MovieData newMovie){
        if (newMovie.id().isEmpty() || newMovie.title().isEmpty() || newMovie.director().isEmpty()){
            throw new IllegalArgumentException("Movie with empty arguments are not allowed");
        }

        if (movieRepo.findById(newMovie.id()).isPresent()){
            throw new IllegalArgumentException("Movie with ID " + newMovie.id() + " already exists");
        }
        return movieRepo.save(newMovie);
    }
}
