package com.example.filmileidja;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/v1/kinokava")
    List<Seanss> koguKinokava() {
        return seanssRepository.findAll();
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
}
