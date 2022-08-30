package it.bitrock.mongodbspringbootdemo.repository;

import it.bitrock.mongodbspringbootdemo.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitle(String title);
    List<Movie> findByYear(Integer year);
}
