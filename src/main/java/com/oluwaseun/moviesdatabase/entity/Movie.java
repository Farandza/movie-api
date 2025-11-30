package com.oluwaseun.moviesdatabase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Setter
    @Column(nullable = false)
    private String title;

    @Min(1888)
    @Setter
    private Integer releaseYear;

    @Min(1)
    @Setter
    private Integer duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    public Movie(String title, Integer releaseYear, Integer duration) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
    }
}