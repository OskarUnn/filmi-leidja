package com.example.filmileidja;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;

@Data
@Document
public class Film {
    @Id
    private String id;
    private String pealkiri;
    private Duration pikkus;
    private int vanusePiirang;

    public Film(String pealkiri, Duration pikkus, int vanusePiirang) {
        this.pealkiri = pealkiri;
        this.pikkus = pikkus;
        this.vanusePiirang = vanusePiirang;
    }
}