package com.example.filmileidja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class FilmiLeidjaApplication {

	Logger logger = LoggerFactory.getLogger(FilmiLeidjaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FilmiLeidjaApplication.class, args);
	}

	@Bean
	CommandLineRunner lisaFilmid(FilmRepository filmRepository) {
		return args -> {
			if (filmRepository.count() > 0) {
				return;
			}

			ArrayList<Film> filmid = new ArrayList<>();
			filmid.add(new Film("Elu ja armastus", Duration.ofMinutes(118), "Draama", "Elu_ja_armastus.jpg"));
			filmid.add(new Film("Kung Fu Panda 4", Duration.ofMinutes(94), "Märul, Seiklus, Animatsioon", "KungFuPanda4.jpg"));
			filmid.add(new Film("Düün: teine osa", Duration.ofMinutes(166), "Ulme, Seiklus", "Düün2.jpg"));
			filmid.add(new Film("Pardid!", Duration.ofMinutes(92), "Komöödia, Seiklus, Animatsioon", "Pardid.jpg"));
			filmid.add(new Film("Mahajäänud", Duration.ofMinutes(133), "Komöödia, Draama", "Mahajäänud.jpg"));

			filmRepository.insert(filmid);

			logger.info("Filmid lisatud andmebaasi");
		};
	}

	@Bean
	@DependsOn("lisaFilmid")
	CommandLineRunner koostaKinokava(FilmRepository filmRepository, SeanssRepository seanssRepository) {
		return args -> {
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

			// Koosta ühe kuu kinokava
			ArrayList<Seanss> kinokava = new ArrayList<>();
			LocalDateTime kinokavaLõpp = LocalDate.now().atTime(sulgemisAeg).plusMonths(1);
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
		};
	}
}
