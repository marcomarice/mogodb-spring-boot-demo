package it.bitrock.mongodbspringbootdemo.service;

import it.bitrock.mongodbspringbootdemo.model.Movie;
import it.bitrock.mongodbspringbootdemo.model.utils.MovieQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    MovieQueryUtils movieQueryUtils;

    public ResponseEntity<Movie> getMovieByComment(String id) {
        Optional<Movie> movieOptional = Optional.ofNullable(movieQueryUtils.getMovieByComment(id));
        return movieOptional
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
