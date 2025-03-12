package org.example.backend.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Data.MovieData;
import org.example.backend.Data.MovieRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public MovieData updateMovie(String targetId, MovieData updatedMovie) {

        if (!movieRepo.existsById(targetId)) {
            throw new NoSuchElementException(String.format("No movie found with the id %s", targetId));
        }

        // Ensure the id in the updatedMovie matches the path variable id.
        if (!targetId.equals(updatedMovie.id())) {
            throw new IllegalArgumentException("ID in path and body do not match");
        }
        return movieRepo.save(updatedMovie);
    }

}
