package com.example.filmileidja;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface SeanssRepository extends MongoRepository<Seanss, String> {

    @Query("{'film.žanrid': {$in: ?0}, 'algus': {$gte: ?1, $lt: ?2}}") // ChatGPT koostatud päring
    List<Seanss> findByŽanridAndVahemik(List<String> žanrid, LocalDateTime vahemikAlgus, LocalDateTime vahemikLõpp);

    @Query("{'algus': {$gte: ?0, $lt: ?1}}") // ChatGPT koostatud päring
    List<Seanss> findByVahemik(LocalDateTime vahemikAlgus, LocalDateTime vahemikLõpp);

}
