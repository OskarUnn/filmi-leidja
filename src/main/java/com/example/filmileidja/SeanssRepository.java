package com.example.filmileidja;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SeanssRepository extends MongoRepository<Seanss, String> {

//    @Query("{'algus': {$gte: ?0, $lt: ?1}}") // ChatGPT koostatud päring
//    List<Seanss> findByVahemik(LocalDateTime vahemikAlgus, LocalDateTime vahemikLõpp);
//
//    @Query("{'algus': {$gte: ?0, $lt: ?1}, 'film.žanrid': {$in: ?2}}") // ChatGPT koostatud päring
//    List<Seanss> findByVahemikAndŽanrid(LocalDateTime vahemikAlgus, LocalDateTime vahemikLõpp, List<String> žanrid);

//    @Query("{'algus': {$gt: ?0}, 'algus': {$gt: ISODate(new Date().toISOString().split('T')[0] + 'T' + ?1)}}") // ChatGPT koostatud päring
//    List<Seanss> findByAlgus(LocalDateTime now, LocalTime specifiedTime);
//
//    @Query("{'algus': {$gt: ?0}, 'algus': {$gt: ISODate(new Date().toISOString().split('T')[0] + 'T' + ?1)}, 'film.žanrid': {$in: ?2}}") // ChatGPT koostatud päring
//    List<Seanss> findByAlgusAndŽanrid(LocalDateTime now, LocalTime specifiedTime, List<String> žanrid);

    @Query("{'film.žanrid': {$in: ?0}}") // ChatGPT koostatud päring
    List<Seanss> findByŽanrid(List<String> žanrid);
}
