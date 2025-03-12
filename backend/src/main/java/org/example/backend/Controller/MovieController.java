package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.DTOs.CustomErrorMessage;
import org.example.backend.Data.MovieData;
import org.example.backend.Service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController
{
    private final MovieService movieService;

    //todo implement stuff

    @GetMapping
    public List<MovieData> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("{id}")
    public MovieData getMovie(@PathVariable String id) {
        Optional<MovieData> movie = movieService.getMovie(id);
        if(movie.isPresent()) {
            return movie.get();
        }
        throw new NoSuchElementException("Movie with ID: "+ id + " not found");
    }

    @PutMapping("{targetId}")
    // with fine-grained exception handling because IllegalArgumentException in movieService.updateTodo is specific
    public ResponseEntity<?> updateMovie(@PathVariable String targetId, @RequestBody MovieData updatedMovie) {
        try {
            MovieData data = movieService.updateMovie(targetId, updatedMovie);
            return ResponseEntity.ok(data);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body( new CustomErrorMessage(e.getMessage(), Instant.now()) );
        }
    }

}
