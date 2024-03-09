package com.example.filmileidja;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;

@Data
@Document
public class Film {
    @Id
    private String id;
    private String pealkiri;
    private Duration pikkus;
    private HashSet<String> žanrid;
    private String poster;

    public Film() {}

    public Film(String pealkiri, Duration pikkus, HashSet<String> žanrid, String poster) {
        this.pealkiri = pealkiri;
        this.pikkus = pikkus;
        this.žanrid = žanrid;
        this.poster = "images/" + poster;
    }

    public Film(String pealkiri, Duration pikkus, String žanrid, String poster) {
        this.pealkiri = pealkiri;
        this.pikkus = pikkus;
        this.žanrid = new HashSet<>(List.of(žanrid.split(", ")));
        this.poster = "images/" + poster;
    }
}