package com.example.filmileidja;

import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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
    List<Seanss> koguKinokava(@RequestBody OtsinguKitsendus kitsendus) {
        System.out.println(kitsendus);
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

    public OtsinguKitsendus(List<String> žanrid, LocalTime algus) {
        this.žanrid = žanrid;
        this.algus = algus;
    }

    public List<Seanss> sobivadSeanssid(SeanssRepository seanssRepository) {

        if (this.žanrid.isEmpty()) {
            return seanssRepository.findAll();
        }
        return seanssRepository.findByŽanrid(this.žanrid);
    }

    @Override
    public String toString() {
        return "OtsinguKitsendus{" +
                "žanrid=" + žanrid +
                ", algus=" + algus +
                '}';
    }
}