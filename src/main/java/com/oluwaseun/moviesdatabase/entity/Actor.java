package com.oluwaseun.moviesdatabase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Past
    @jakarta.persistence.Convert(converter = com.oluwaseun.moviesdatabase.convert.LocalDateAttributeConverter.class)
    private LocalDate birthDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    public Actor() {
    }

    public Actor(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getActors().add(this);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getActors().remove(this);
    }
}
