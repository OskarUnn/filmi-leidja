package com.example.filmileidja;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface SeanssRepository extends MongoRepository<Seanss, String> {
    @Query("{'film.žanrid': {$in: ?0}, 'algus': {$gte: ?1}}") // ChatGPT koostatud päring
    List<Seanss> findByŽanridAndAlgus(List<String> žanrid, LocalTime algus);

    @Query("{'algus': {$gte: ?0}}") // ChatGPT koostatud päring
    List<Seanss> findByAlgus(LocalTime algus);
}
