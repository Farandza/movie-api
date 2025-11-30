package com.oluwaseun.moviesdatabase.controller;

import com.oluwaseun.moviesdatabase.entity.Movie;
import com.oluwaseun.moviesdatabase.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) {
        Movie created = movieService.createMovie(movie);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<Movie>> getAllMovies(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) Long actorId,
            Pageable pageable) {

        Page<Movie> page;
        if (year != null) {
            page = movieService.getMoviesByReleaseYear(year, pageable);
        } else if (genreId != null) {
            page = movieService.getMoviesByGenreId(genreId, pageable);
        } else if (actorId != null) {
            page = movieService.getMoviesByActorId(actorId, pageable);
        } else {
            page = movieService.getAllMovies(pageable);
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Movie>> searchMoviesByTitle(
            @RequestParam String title,
            Pageable pageable) {
        return ResponseEntity.ok(movieService.searchMoviesByTitle(title, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody Movie movie) {
        Movie updated = movieService.updateMovie(id, movie);
        return ResponseEntity.ok(updated);
    }
}