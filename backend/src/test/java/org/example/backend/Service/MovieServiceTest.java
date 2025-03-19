package org.example.backend.Service;

import org.example.backend.DTOs.MovieDto;
import org.example.backend.Data.MovieData;
import org.example.backend.Data.MovieRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieRepo repo;
    private MovieService service;
    private final IdService idService = mock(IdService.class);

    private MovieData movie1, movie2;
    private MovieDto movieDto1;

    @BeforeEach
    void setUp() {
        repo = mock(MovieRepo.class);
        service = new MovieService(repo, idService);

        movieDto1 = new MovieDto("test", "tester", 2025);
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

    @Test
    void deleteMovie_whenFound_deletesMovie()
    {
        // GIVEN
        when(repo.existsById("1")).thenReturn(true);

        // WHEN
        boolean deleted = service.deleteMovie("1");

        // THEN
        verify(repo).deleteById("1");
        assertTrue(deleted);
    }

    @Test
    void deleteMovie_whenNotFound_returnsFalse()
    {
        // GIVEN
        when(repo.existsById("1")).thenReturn(false);

        // WHEN
        boolean deleted = service.deleteMovie("1");

        // THEN
        verify(repo, never()).deleteById(anyString());
        assertFalse(deleted);
    }

    @Test
    void updateMovie_whenFound_returnMovie() {
        // GIVEN
        String targetId = "2";
        MovieData updatedMovie = new MovieData(targetId, "wuff", "dog", 2000);
        when(repo.existsById(targetId)).thenReturn(true);
        when(repo.save(updatedMovie)).thenReturn(updatedMovie);

        // WHEN
        MovieData actual = service.updateMovie(targetId, updatedMovie);

        // THEN
        MovieData expected = updatedMovie;
        assertEquals(expected, actual);
    }

    @Test
    void updateMovie_whenNotFound_throwNoSuchElementException() {
        // GIVEN
        String targetId = "3";
        MovieData updatedMovie = new MovieData(targetId, "wuff", "dog", 2000);
        when(repo.existsById(targetId)).thenReturn(false);
        when(repo.save(updatedMovie)).thenReturn(updatedMovie); //line actually not required for test

        // WHEN + THEN
        assertThrows(NoSuchElementException.class, () -> service.updateMovie(targetId, updatedMovie));
    }

    @Test
    void updateMovie_whenFound_throwIllegalArgumentException() {
        // GIVEN
        String targetId = "3";
        MovieData updatedMovie = new MovieData(targetId + "***", "wuff", "dog", 2000);
        when(repo.existsById(targetId)).thenReturn(true);
        when(repo.save(updatedMovie)).thenReturn(updatedMovie);

        // WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> service.updateMovie(targetId, updatedMovie));
    }

    @Test
    void saveMovie_whenSuccessful_thenReturnObject() {
        // GIVEN
        MovieData expected = movie1;
        when(idService.randomId()).thenReturn("1");
        when(repo.save(expected)).thenReturn(expected);
        // WHEN
        MovieData actual = service.saveMovie(movieDto1);
        // THEN
        verify(idService).randomId();
        verify(repo).save(movie1);
        assertEquals(expected, actual);
    }
}