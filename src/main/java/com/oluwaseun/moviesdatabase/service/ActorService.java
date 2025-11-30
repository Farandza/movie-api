package com.oluwaseun.moviesdatabase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oluwaseun.moviesdatabase.entity.Actor;
import com.oluwaseun.moviesdatabase.entity.Movie;
import com.oluwaseun.moviesdatabase.exception.ResourceNotFoundException;
import com.oluwaseun.moviesdatabase.repository.ActorRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(Actor actor) {
        validateActor(actor);
        return actorRepository.save(actor);
    }

    public Page<Actor> getActors(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return actorRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return actorRepository.findAll(pageable);
    }

    public Actor getActorById(Long id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor not found with id " + id));
    }

    public Actor updateActor(Long id, Actor updatedData) {
        Actor existing = getActorById(id);

        if (updatedData.getName() != null)
            existing.setName(updatedData.getName());
        if (updatedData.getBirthDate() != null)
            existing.setBirthDate(updatedData.getBirthDate());
        if (updatedData.getMovies() != null)
            existing.setMovies(updatedData.getMovies());

        return actorRepository.save(existing);
    }

    public void deleteActor(Long id, boolean force) {
        Actor actor = getActorById(id);

        if (!force && !actor.getMovies().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete actor '" + actor.getName() + "' because they are associated with movies");
        }

        if (force) {
            actor.getMovies().forEach(m -> m.getActors().remove(actor));
            actor.getMovies().clear();
        }

        actorRepository.delete(actor);
    }

    public List<Movie> getMoviesByActor(Long actorId) {
        Actor actor = getActorById(actorId);
        return List.copyOf(actor.getMovies());
    }

    private void validateActor(Actor actor) {
        if (actor.getName() == null || actor.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Actor name cannot be null or empty");
        }

        if (actor.getBirthDate() != null && actor.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }
}
