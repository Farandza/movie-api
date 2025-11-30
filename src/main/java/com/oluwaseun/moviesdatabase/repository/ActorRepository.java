package com.oluwaseun.moviesdatabase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oluwaseun.moviesdatabase.entity.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Page<Actor> findByNameContainingIgnoreCase(String name, Pageable pageable);
}