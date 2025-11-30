package com.oluwaseun.moviesdatabase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oluwaseun.moviesdatabase.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @EntityGraph(attributePaths = { "genres", "actors" })
    Page<Movie> findAll(Pageable pageable);

    @EntityGraph(attributePaths = { "genres", "actors" })
    Page<Movie> findByReleaseYear(Integer year, Pageable pageable);

    @EntityGraph(attributePaths = { "genres", "actors" })
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @EntityGraph(attributePaths = { "genres", "actors" })
    @Query("select m from Movie m join m.genres g where g.id = :genreId")
    Page<Movie> findByGenreId(Long genreId, Pageable pageable);

    @EntityGraph(attributePaths = { "genres", "actors" })
    @Query("select m from Movie m join m.actors a where a.id = :actorId")
    Page<Movie> findByActorId(Long actorId, Pageable pageable);
}