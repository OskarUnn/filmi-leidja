package com.example.filmileidja;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.ArrayList;

@SpringBootApplication
public class FilmiLeidjaApplication {

	Logger logger = LoggerFactory.getLogger(FilmiLeidjaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FilmiLeidjaApplication.class, args);
	}

	@Bean
	ApplicationRunner lisaFilmid(FilmRepository filmRepository, SeanssRepository seanssRepository, KinokavaService koostaKinokavaService) {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
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

				koostaKinokavaService.koostaKinokava();
			}
		};
	}
}
