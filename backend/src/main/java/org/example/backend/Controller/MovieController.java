package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Data.MovieData;
import org.example.backend.Service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
