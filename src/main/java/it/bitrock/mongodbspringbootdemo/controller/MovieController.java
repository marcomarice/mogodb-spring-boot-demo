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
    public ResponseEntity<Movie> getMovieByIdRepository(@PathVariable String id) {
        return movieService.getMovieByIdRepository(id);
    }

    @GetMapping("/v2/movie/{id}")
    public ResponseEntity<Movie> getMovieByIdMongoClient(@PathVariable String id) {
        return movieService.getMovieByIdMongoClient(id);
    }

    @GetMapping("/movie-by-title/{title}")
    public ResponseEntity<List<Movie>> getMovieByTitleRepository(@PathVariable String title) {
        return movieService.getMovieByTitleRepository(title);
    }

    @GetMapping("/movies-by-year/{year}")
    public ResponseEntity<List<Movie>> getMoviesByYearRepository(@PathVariable Integer year) {
        return movieService.getMoviesByYearRepository(year);
    }

    @GetMapping("/v2/movies-by-year/{year}")
    public ResponseEntity<List<Movie>> getMoviesByYearMongoClient(@PathVariable Integer year) {
        return movieService.getMoviesByYearMongoClient(year);
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
