package com.example.filmileidja;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SeanssRepository extends MongoRepository<Seanss, String> {
    @Query("{'film.žanrid': {$in: ?0}}") // ChatGPT koostatud päring
    List<Seanss> findByŽanrid(List<String> žanrid);
}
