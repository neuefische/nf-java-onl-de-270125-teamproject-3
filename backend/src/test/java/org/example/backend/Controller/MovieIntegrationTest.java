package org.example.backend.Controller;

import org.example.backend.Data.MovieData;
import org.example.backend.Data.MovieRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    MovieRepo movieRepo;

    @Test
    void getMovies_whenEmpty_returnEmptyList() throws Exception {
        // WHEN
        mvc.perform(get("/api/movie")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                  []
                """));
    }

    @Test
    @DirtiesContext
    void getMovies_whenFound_returnMovies() throws Exception {
        MovieData movie1 = new MovieData("1", "test", "tester", 2025);
        MovieData movie2 = new MovieData("2", "meow", "cat", 2000);
        movieRepo.save(movie1);
        movieRepo.save(movie2);
        // WHEN
        mvc.perform(get("/api/movie")
                .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                [
                  {
                    "id": "1",
                    "title": "test",
                    "director": "tester",
                    "releaseYear": 2025
                  }, 
                  {
                    "id": "2",
                    "title": "meow",
                    "director": "cat",
                    "releaseYear": 2000
                  }
                ]
                """));
    }

    @Test
    @DirtiesContext
    void getMovie_whenFound_returnMovie() throws Exception {
        MovieData movie1 = new MovieData("1", "test", "tester", 2025);
        movieRepo.save(movie1);
        // WHEN
        mvc.perform(get("/api/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                  {
                    "id": "1",
                    "title": "test",
                    "director": "tester",
                    "releaseYear": 2025
                  }
                """));
    }

    @Test
    void getMovie_whenNotFound_returnException() throws Exception {
        // WHEN
        mvc.perform(get("/api/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNotFound());
    }
}