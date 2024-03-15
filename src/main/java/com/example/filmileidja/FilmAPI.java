package com.example.filmileidja;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
        if (kitsendus.žanrid.isEmpty()) {
            kitsendus.žanrid = new ArrayList<>(this.žanrid());
        }
        if (kitsendus.keeled.isEmpty()) {
            kitsendus.keeled = new ArrayList<>(this.keeled());
        }
        if (kitsendus.vanusepiirangud.isEmpty()) {
            kitsendus.vanusepiirangud = new ArrayList<>(this.vanusepiirangud());
        }

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

    @GetMapping("/api/v1/vanusepiirangud")
    HashSet<String> vanusepiirangud() {
        List<Film> filmid = filmRepository.findAll();
        HashSet<String> vanusepiirangud = new HashSet<>();

        for (int i = 0; i < filmid.size(); i++) {
            Film film = filmid.get(i);
            vanusepiirangud.add(film.getVanusepiirang());
        }

        return vanusepiirangud;
    }

    @GetMapping("/api/v1/keeled")
    HashSet<String> keeled() {
        List<Seanss> seanssid = seanssRepository.findAll();
        HashSet<String> keeled = new HashSet<>();

        for (int i = 0; i < seanssid.size(); i++) {
            Seanss seanss = seanssid.get(i);
            keeled.add(seanss.getKeel());
        }

        return keeled;
    }
}

class OtsinguKitsendus {
    public List<String> žanrid;
    private LocalTime algus;
    public List<String> keeled;
    public List<String> vanusepiirangud;

    public OtsinguKitsendus(List<String> žanrid, String algus, List<String> keeled, List<String> vanusepiirangud) {
        this.žanrid = žanrid;
        this.keeled = keeled;
        this.vanusepiirangud = vanusepiirangud;

        if (algus == null) {
            this.algus = null;
        } else {
            this.algus = LocalTime.of(Integer.parseInt(algus), 0);
        }
    }

    public List<Seanss> sobivadSeanssid(SeanssRepository seanssRepository) {

        List<Seanss> seanssid = seanssRepository.findByKitsendused(this.žanrid, this.keeled, this.vanusepiirangud);

        // filtreeri välja seanssid mis on juba alanud või mis on vastuolus algusaja kitsendusega
        Iterator<Seanss> iterator = seanssid.iterator();
        while (iterator.hasNext()) {
            Seanss seanss = iterator.next();
            LocalDateTime seanssiAlgus = seanss.getAlgus();
            if (seanssiAlgus.isBefore(LocalDateTime.now())) {
                iterator.remove();
            } else if (this.algus != null) {
                if (seanssiAlgus.toLocalTime().isBefore(this.algus)) {
                    iterator.remove();
                }
            }
        }

        if (this.algus != null) {
            seanssid.sort(Comparator.comparing(seanss -> seanss.getAlgus().toLocalTime()));
        }

        return seanssid;
    }

    @Override
    public String toString() {
        return "OtsinguKitsendus{" +
                "žanrid=" + žanrid +
                ", algus=" + algus +
                ", keeled=" + keeled +
                ", vanusepiirangud=" + vanusepiirangud +
                '}';
    }
}