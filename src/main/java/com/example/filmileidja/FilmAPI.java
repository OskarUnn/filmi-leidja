package com.example.filmileidja;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
class FilmAPI {

    private final FilmRepository filmRepository;
    private final SeanssRepository seanssRepository;

    public FilmAPI(FilmRepository filmRepository, SeanssRepository seanssRepository) {
        this.filmRepository = filmRepository;
        this.seanssRepository = seanssRepository;
    }

    @GetMapping("/api/v1/filmid")
    List<Film> kõikFilmid() {
        return filmRepository.findAll();
    }

    @PostMapping("/api/v1/kinokava")
    List<Seanss> soovitatudKinokava(@RequestBody OtsinguKitsendus kitsendus) {
        return kitsendus.sobivadSeanssid(seanssRepository);
    }

    @GetMapping("/api/v1/seanss/{id}")
    Optional<Seanss> valitudSeanss(@PathVariable("id") String id) {
        return seanssRepository.findById(id);
    }

    @GetMapping("/api/v1/parimadKohad/{id}")
    List<String> soovitaKohad(@PathVariable("id") String id, @RequestParam int kohti) {
        Optional<Seanss> seanss = seanssRepository.findById(id);

        if (seanss.isEmpty()) {
            return null;
        }
        return seanss.get().parimadKohad(kohti);
    }

    @GetMapping("/api/v1/žanrid")
    HashSet<String> žanrid() {
        List<Film> filmid = filmRepository.findAll();
        HashSet<String> žanrid = new HashSet<>();

        for (int i = 0; i < filmid.size(); i++) {
            Film film = filmid.get(i);
            žanrid.addAll(film.getŽanrid());
        }

        return žanrid;
    }
}

class OtsinguKitsendus {
    private List<String> žanrid;
    private LocalTime algus;
    private LocalDate kuupäev;

    public OtsinguKitsendus(List<String> žanrid, String algus, String kuupäev) {
        this.žanrid = žanrid;
        if (kuupäev == null) {
            this.kuupäev = LocalDate.now();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.kuupäev = LocalDate.parse(kuupäev, formatter);
        }
        if (algus == null) {
            this.algus = LocalTime.now();
        } else {
            this.algus = LocalTime.of(Integer.parseInt(algus), 0);
            if (this.kuupäev.isEqual(LocalDate.now()) && this.algus.isBefore(LocalTime.now())) {
                this.algus = LocalTime.now();
            }
        }
    }

    public List<Seanss> sobivadSeanssid(SeanssRepository seanssRepository) {
        LocalDateTime vahemikAlgus = kuupäev.atTime(this.algus);
        LocalDateTime vahemikLõpp = this.kuupäev.atTime(LocalTime.MAX);

        if (this.žanrid.isEmpty()) {
            return seanssRepository.findByVahemik(vahemikAlgus, vahemikLõpp);
        }

        return seanssRepository.findByŽanridAndVahemik(this.žanrid, vahemikAlgus, vahemikLõpp);
    }

    @Override
    public String toString() {
        return "OtsinguKitsendus{" +
                "žanrid=" + žanrid +
                ", algus=" + algus +
                '}';
    }
}