package com.oluwaseun.moviesdatabase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oluwaseun.moviesdatabase.entity.Genre;
import com.oluwaseun.moviesdatabase.entity.Movie;
import com.oluwaseun.moviesdatabase.exception.ResourceNotFoundException;
import com.oluwaseun.moviesdatabase.repository.GenreRepository;

import java.util.List;

@Service
@Transactional
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre createGenre(Genre genre) {
        validateGenre(genre);
        return genreRepository.save(genre);
    }

    public Page<Genre> getAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id " + id));
    }

    public Genre updateGenre(Long id, Genre updatedData) {
        Genre existing = getGenreById(id);
        if (updatedData.getName() != null)
            existing.setName(updatedData.getName());
        return genreRepository.save(existing);
    }

    public void deleteGenre(Long id, boolean force) {
        Genre genre = getGenreById(id);

        if (!force && !genre.getMovies().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete genre '" + genre.getName() + "' because it has associated movies");
        }

        if (force) {
            genre.getMovies().forEach(m -> m.getGenres().remove(genre));
            genre.getMovies().clear();
        }

        genreRepository.delete(genre);
    }

    public List<Movie> getMoviesByGenre(Long genreId) {
        Genre genre = getGenreById(genreId);
        return List.copyOf(genre.getMovies());
    }

    private void validateGenre(Genre genre) {
        if (genre.getName() == null || genre.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Genre name cannot be null or empty");
        }
    }
}
