package com.oluwaseun.moviesdatabase.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oluwaseun.moviesdatabase.entity.Actor;
import com.oluwaseun.moviesdatabase.entity.Movie;
import com.oluwaseun.moviesdatabase.service.ActorService;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping
    public ResponseEntity<Actor> createActor(@Valid @RequestBody Actor actor) {
        Actor created = actorService.createActor(actor);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Actor>> getAllActors(
            @RequestParam(required = false) String name,
            Pageable pageable) {
        Page<Actor> actors = actorService.getActors(name, pageable);
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.getActorById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        Actor updated = actorService.updateActor(id, actor);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActor(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean force) {
        actorService.deleteActor(id, force);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{actorId}/movies")
    public ResponseEntity<List<Movie>> getMoviesByActor(@PathVariable Long actorId) {
        return ResponseEntity.ok(actorService.getMoviesByActor(actorId));
    }
}
