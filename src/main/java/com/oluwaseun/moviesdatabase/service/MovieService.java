package com.oluwaseun.moviesdatabase.service;

import com.oluwaseun.moviesdatabase.entity.Movie;
import com.oluwaseun.moviesdatabase.exception.ResourceNotFoundException;
import com.oluwaseun.moviesdatabase.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Page<Movie> searchMoviesByTitle(String title, Pageable pageable) {
        return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Movie> getMoviesByReleaseYear(Integer year, Pageable pageable) {
        return movieRepository.findByReleaseYear(year, pageable);
    }

    public Page<Movie> getMoviesByGenreId(Long genreId, Pageable pageable) {
        return movieRepository.findByGenreId(genreId, pageable);
    }

    public Page<Movie> getMoviesByActorId(Long actorId, Pageable pageable) {
        return movieRepository.findByActorId(actorId, pageable);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    public Movie updateMovie(Long id, Movie updatedData) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        if (updatedData.getTitle() != null && !updatedData.getTitle().trim().isEmpty()) {
            movie.setTitle(updatedData.getTitle());
        }
        if (updatedData.getReleaseYear() != null) {
            movie.setReleaseYear(updatedData.getReleaseYear());
        }
        if (updatedData.getDuration() != null) {
            movie.setDuration(updatedData.getDuration());
        }

        return movieRepository.save(movie);
    }
}