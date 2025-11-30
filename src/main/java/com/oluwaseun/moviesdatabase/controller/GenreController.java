package com.oluwaseun.moviesdatabase.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oluwaseun.moviesdatabase.entity.Genre;
import com.oluwaseun.moviesdatabase.entity.Movie;
import com.oluwaseun.moviesdatabase.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) {
        Genre created = genreService.createGenre(genre);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Genre>> getAllGenres(Pageable pageable) {
        return ResponseEntity.ok(genreService.getAllGenres(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.updateGenre(id, genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean force) {
        genreService.deleteGenre(id, force);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{genreId}/movies")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable Long genreId) {
        return ResponseEntity.ok(genreService.getMoviesByGenre(genreId));
    }
}
