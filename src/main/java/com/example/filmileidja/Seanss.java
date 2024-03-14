package com.example.filmileidja;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Data
@Document
public class Seanss {
    @Id
    private String id;
    private Film film;
    private LocalDateTime algus;
    private boolean[][] saal;
    private String keel;

    public Seanss(Film film, LocalDateTime algus) {
        this.film = film;
        this.algus = algus;
        this.saal = KinoSaal.genereeriSaal();

        String[] keeled = {"eesti", "inglise", "vene"};
        Random rand = new Random();
        this.keel = keeled[rand.nextInt(keeled.length)];
    }

    public List<String> parimadKohad(int kohtadeArv) {
        ArrayList<Iste> istmed = new ArrayList<>();

        for (int i = 0; i < this.saal.length; i++) {
            for (int j = 0; j < this.saal[0].length; j++) {
                if (!saal[i][j]) {
                    istmed.add(new Iste(i, j, saal));
                }
            }
        }

        ArrayList<Iste> parimadIstmed = new ArrayList<>();

        for (int i = 0; i < kohtadeArv; i++) {
            istmed.sort((o1, o2) -> {
                int väärtus1 = o1.kaugusKeskosast();
                int väärtus2 = o2.kaugusKeskosast();

                if (!parimadIstmed.isEmpty()) {
                    Iste viimane = parimadIstmed.getLast();
                    väärtus1 += o1.samasReas(viimane);
                    väärtus2 += o2.samasReas(viimane);
                }

                return väärtus1 - väärtus2;
            });
            parimadIstmed.add(istmed.removeFirst());
        }

        return parimadIstmed.stream().map(Iste::toString).collect(Collectors.toList());
    }
}

class Iste {
    public final int reaNr;
    private final int istmeNr;
    private final boolean[][] saal;

    public Iste(int reaNr, int istmeNr, boolean[][] saal) {
        this.reaNr = reaNr;
        this.istmeNr = istmeNr;
        this.saal = saal;
    }

    public int kaugusKeskosast() {
        int keskmineRida = this.saal.length / 2-1;
        int keskmineIste = this.saal[0].length / 2;
        return (Math.abs(this.reaNr - keskmineRida) + Math.abs(this.istmeNr - keskmineIste));
    }

    public int samasReas(Iste i) {
        if (reaNr == i.reaNr) {
            return -1;
        }
        return 2;
    }

    @Override
    public String toString() {
        return reaNr + "-" + istmeNr;
    }
}