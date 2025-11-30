package com.oluwaseun.moviesdatabase.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genres")
@Getter
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    @Setter
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies = new HashSet<>();

    public Genre(String name) {
        this.name = name;
    }
}