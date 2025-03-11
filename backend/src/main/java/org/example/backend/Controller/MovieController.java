package org.example.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Service.MovieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController
{
    private final MovieService movieService;

    //todo implement stuff
}
