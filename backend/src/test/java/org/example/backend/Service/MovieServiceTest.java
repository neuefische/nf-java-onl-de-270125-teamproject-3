package org.example.backend.Service;

import org.example.backend.Data.MovieData;
import org.example.backend.Data.MovieRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieRepo repo;
    private MovieService service;

    private MovieData movie1, movie2;

    @BeforeEach
    void setUp() {
        repo = mock(MovieRepo.class);
        service = new MovieService(repo);

        movie1 = new MovieData("1", "test", "tester", 2025);
        movie2 = new MovieData("2", "meow", "cat", 2000);
    }

    @Test
    void getMovies_whenEmpty_returnEmptyList() {
        // GIVEN
        List<MovieData> expected = List.of();
        when(repo.findAll()).thenReturn(expected);
        // WHEN
        List<MovieData> actual = service.getMovies();
        // THEN
        verify(repo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getMovies_whenNotEmpty_returnMoviesList() {
        // GIVEN
        List<MovieData> expected = List.of(movie1, movie2);
        when(repo.findAll()).thenReturn(expected);
        // WHEN
        List<MovieData> actual = service.getMovies();
        // THEN
        verify(repo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getMovie_whenNotFound_returnEmptyOptional() {
        // GIVEN
        Optional<MovieData> expected = Optional.empty();
        when(repo.findById("3")).thenReturn(expected);
        // WHEN
        Optional<MovieData> actual = service.getMovie("3");
        // THEN
        verify(repo).findById("3");
        assertEquals(expected, actual);
    }

    @Test
    void getMovie_whenFound_returnMovie() {
        // GIVEN
        Optional<MovieData> expected = Optional.ofNullable(movie1);
        when(repo.findById("1")).thenReturn(expected);
        // WHEN
        Optional<MovieData> actual = service.getMovie("1");
        // THEN
        verify(repo).findById("1");
        assertEquals(expected, actual);
    }
}