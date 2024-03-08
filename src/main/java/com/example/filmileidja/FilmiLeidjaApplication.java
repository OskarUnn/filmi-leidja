package com.example.filmileidja;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.ArrayList;

@SpringBootApplication
public class FilmiLeidjaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmiLeidjaApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(FilmRepository filmRepository) {
		return args -> {
			filmRepository.deleteAll();

			ArrayList<Film> filmid = new ArrayList<>();
			filmid.add(new Film("Elu ja armastus", Duration.ofMinutes(118), "Draama"));
			filmid.add(new Film("Kung Fu Panda 4", Duration.ofMinutes(94), "Märul, Seiklus, Animatsioon"));
			filmid.add(new Film("Düün: teine osa", Duration.ofMinutes(166), "Ulme, Seiklus"));
			filmid.add(new Film("Pardid!", Duration.ofMinutes(92), "Komöödia, Seiklus, Animatsioon"));
			filmid.add(new Film("Mahajäänud", Duration.ofMinutes(133), "Komöödia, Draama"));

			filmRepository.insert(filmid);
		};
	}
}
