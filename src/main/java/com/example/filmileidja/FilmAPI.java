package com.example.filmileidja;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class FilmAPI {

    private final FilmRepository repository;

    public FilmAPI(FilmRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/v1/filmid")
    List<Film> kõikFilmid() {
        return repository.findAll();
    }
}
