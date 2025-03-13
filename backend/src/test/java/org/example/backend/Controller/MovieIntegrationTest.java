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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @DirtiesContext
    void deleteMovie_whenFound_removesMovie() throws Exception
    {
        // GIVEN
        MovieData movie = new MovieData("1", "Test Movie", "Test Director", 2000);
        movieRepo.save(movie);

        // WHEN & THEN
        mvc.perform(delete("/api/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMovie_whenNotFound_returnsNotFound() throws Exception
    {
        // WHEN & THEN
        mvc.perform(delete("/api/movie/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    @DirtiesContext
    void updateMovie_whenFound_returnMovie() throws Exception {
        // GIVEN
        MovieData movie = new MovieData("1", "Number 1", "Alan Smithee", 1000);
        movieRepo.save(movie);
        // WHEN
        mvc.perform(put("/api/movie/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                  {
                                    "id": "1",
                                    "title": "Pi",
                                    "director": "Darren Aronofsky",
                                    "releaseYear": 1999
                                  }
                                """
                        )
                )
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                  {
                    "id": "1",
                    "title": "Pi",
                    "director": "Darren Aronofsky",
                    "releaseYear": 1999
                  }
                """));
    }

    @Test
    void updateMovie_whenNotFound_throwNoSuchElementException() throws Exception {
        // GIVEN
        String targetId = "1";
        // movie repo is empty

        // WHEN
        mvc.perform(put("/api/movie/" + targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                  {
                                    "id": "1",
                                    "title": "Pi",
                                    "director": "Darren Aronofsky",
                                    "releaseYear": 1999
                                  }
                                """
                        )
                )
                // THEN
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No movie found with the id " + targetId));
    }

    @Test
    @DirtiesContext
    void updateMovie_whenFound_throwIllegalArgumentException() throws Exception {
        // GIVEN
        MovieData movie = new MovieData("1", "Number 1", "Alan Smithee", 1000);
        movieRepo.save(movie);
        // WHEN
        mvc.perform(put("/api/movie/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                  {
                                    "id": "2",
                                    "title": "Pi",
                                    "director": "Darren Aronofsky",
                                    "releaseYear": 1999
                                  }
                                """
                        )
                )
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ID in path and body do not match"));
    }

}