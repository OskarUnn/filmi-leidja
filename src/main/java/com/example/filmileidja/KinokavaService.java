package com.example.filmileidja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class KinokavaService {
    private static final Logger logger = LoggerFactory.getLogger(KinokavaService.class);

    private final FilmRepository filmRepository;
    private final SeanssRepository seanssRepository;

    public KinokavaService(FilmRepository filmRepository, SeanssRepository seanssRepository) {
        this.filmRepository = filmRepository;
        this.seanssRepository = seanssRepository;
    }

    public void koostaKinokava() {
        if (seanssRepository.count() > 0) {
            return;
        }
        if (filmRepository.count() <= 0) {
            logger.error("Kinokava üritati luua, aga andmebaasis pole ühtegi filmi mida näidata");
            return;
        }

        List<Film> filmid = filmRepository.findAll();

        // kino päevane avamis ja sulgemis kellaaeg
        LocalTime avamisAeg = LocalTime.of(9, 0); // 09:00
        LocalTime sulgemisAeg = LocalTime.of(21, 30); // 21:30

        // Koosta ühe nädala kinokava
        ArrayList<Seanss> kinokava = new ArrayList<>();
        LocalDateTime kinokavaLõpp = LocalDate.now().atTime(sulgemisAeg).plusWeeks(1);
        LocalDateTime viimaseSeanssiLõpp = LocalDateTime.now().with(avamisAeg).minusMinutes(20);
        Random random = new Random();
        LocalDateTime päevaSulgemine = viimaseSeanssiLõpp.with(sulgemisAeg);

        while (viimaseSeanssiLõpp.isBefore(kinokavaLõpp)) {

            LocalDateTime seanssiAlgus = viimaseSeanssiLõpp.plusMinutes(15); // jäta >15 minutit seansside vahele
            seanssiAlgus = seanssiAlgus.plusMinutes(5 - (seanssiAlgus.getMinute() % 5)); // ümarda seanssi algus 5 minuti vahemikku

            // liiguta seanssi algus järgmisesse päeva kui eelmine lõppes peale sulgemist
            if (seanssiAlgus.isAfter(päevaSulgemine)) {
                päevaSulgemine = päevaSulgemine.plusDays(1);
                seanssiAlgus = päevaSulgemine.with(avamisAeg);
            }

            Film suvalineFilm = filmid.get(random.nextInt(filmid.size()));

            viimaseSeanssiLõpp = seanssiAlgus.plus(suvalineFilm.getPikkus());

            Seanss uusSeanss = new Seanss(suvalineFilm, seanssiAlgus);
            kinokava.add(uusSeanss);
        }
        seanssRepository.insert(kinokava);

        logger.info("Kinokava koostatud");
    }
}
