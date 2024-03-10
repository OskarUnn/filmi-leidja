package com.example.filmileidja;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Seanss {
    @Id
    private String id;
    private Film film;
    private LocalDateTime algus;
    private boolean[][] saal;

    public Seanss(Film film, LocalDateTime algus) {
        this.film = film;
        this.algus = algus;
        this.saal = KinoSaal.genereeriSaal();
    }
}