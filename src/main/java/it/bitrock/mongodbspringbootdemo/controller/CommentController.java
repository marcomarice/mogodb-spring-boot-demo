package it.bitrock.mongodbspringbootdemo.controller;

import it.bitrock.mongodbspringbootdemo.model.Movie;
import it.bitrock.mongodbspringbootdemo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/movie-by-comment/{id}")
    public ResponseEntity<Movie> getMovieByComment(@PathVariable String id) {
        return commentService.getMovieByComment(id);
    }
}
