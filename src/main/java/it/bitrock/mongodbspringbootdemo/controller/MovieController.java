package it.bitrock.mongodbspringbootdemo.controller;

import it.bitrock.mongodbspringbootdemo.dto.MoviePostDto;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import it.bitrock.mongodbspringbootdemo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.bson.Document;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    public MovieService movieService;

    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/movie-by-title/{title}")
    public ResponseEntity<List<Movie>> getMovieByTitle(@PathVariable String title) {
        return movieService.getMovieByTitle(title);
    }

    @GetMapping("/movie-by-year/{year}")
    public ResponseEntity<List<Movie>> getMoviesByYear(@PathVariable Integer year) {
        return movieService.getMoviesByYear(year);
    }

    @GetMapping("/movie-genres")
    public ResponseEntity<List<Document>> getAllMovieGenres() {
        return movieService.getAllMovieGenres();
    }

    @PostMapping("/movie-add")
    public ResponseEntity<String> addMovie(@RequestBody MoviePostDto moviePostDto) {
        return movieService.addMovie(moviePostDto);
    }

    @PutMapping("/movie-update/{title}")
    public ResponseEntity<String> updateMovie(@PathVariable String title) {
        return movieService.updateMovie(title);
    }

    @PutMapping("/movies-update/{year}")
    public ResponseEntity<String> updateMovies(@PathVariable Integer year) {
        return movieService.updateMovies(year);
    }

    @DeleteMapping("/movie-delete/{title}")
    public ResponseEntity<String> deleteMovie(@PathVariable String title) {
        return movieService.deleteMovie(title);
    }
}
