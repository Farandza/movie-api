package com.oluwaseun.moviesdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oluwaseun.moviesdatabase.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByNameIgnoreCase(String name);
}