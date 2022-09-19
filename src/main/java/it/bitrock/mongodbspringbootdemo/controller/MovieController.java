package it.bitrock.mongodbspringbootdemo.controller;

import it.bitrock.mongodbspringbootdemo.dto.MoviePostDto;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import it.bitrock.mongodbspringbootdemo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    public MovieService movieService;

    @GetMapping("/by-repository/movie/{id}")
    public ResponseEntity<Movie> getMovieByIdRepository(@PathVariable String id) {
        return movieService.getMovieByIdRepository(id);
    }

    @GetMapping("/by-mongo-client/movie/{id}")
    public ResponseEntity<Movie> getMovieByIdMongoClient(@PathVariable String id) {
        return movieService.getMovieByIdMongoClient(id);
    }

    @GetMapping("/by-repository/movie-by-title/{title}")
    public ResponseEntity<List<Movie>> getMovieByTitleRepository(@PathVariable String title) {
        return movieService.getMovieByTitleRepository(title);
    }

    @GetMapping("/by-repository/movies-by-year/{year}")
    public ResponseEntity<List<Movie>> getMoviesByYearRepository(@PathVariable Integer year) {
        return movieService.getMoviesByYearRepository(year);
    }

    @GetMapping("/by-mongo-client/movies-by-year/{year}")
    public ResponseEntity<List<Movie>> getMoviesByYearMongoClient(@PathVariable Integer year) {
        return movieService.getMoviesByYearMongoClient(year);
    }

    @PostMapping("/by-repository/movie-add")
    public ResponseEntity<String> addMovieRepository(@RequestBody MoviePostDto moviePostDto) {
        return movieService.addMovieRepository(moviePostDto);
    }

    @PostMapping("/by-mongo-template/movie-insert")
    public ResponseEntity<String> insertMovieMongoTemplate(@RequestBody MoviePostDto moviePostDto) {
        return movieService.insertMovieMongoTemplate(moviePostDto);
    }

    @PostMapping("/by-mongo-template/movie-save")
    public ResponseEntity<String> saveMovieMongoTemplate(@RequestBody MoviePostDto moviePostDto) {
        return movieService.saveMovieMongoTemplate(moviePostDto);
    }

    @PutMapping("/by-repository/movie-update/{title}")
    public ResponseEntity<String> updateMovieTitleRepository(@PathVariable String title) {
        return movieService.updateMovieTitleRepository(title);
    }

    @PutMapping("/by-mongo-template/movie-update/{title}")
    public ResponseEntity<String> updateMovieTitleMongoTemplate(@PathVariable String title) {
        return movieService.updateMovieTitleMongoTemplate(title);
    }

    @PutMapping("/by-repository/movies-update/{year}")
    public ResponseEntity<String> updateMoviesByYearRepository(@PathVariable Integer year) {
        return movieService.updateMoviesByYearRepository(year);
    }

    @PutMapping("/by-mongo-template/movies-update/{year}")
    public ResponseEntity<String> updateMoviesByYearMongoTemplate(@PathVariable Integer year) {
        return movieService.updateMoviesByYearMongoTemplate(year);
    }

    @DeleteMapping("/by-repository/movie-delete/{title}")
    public ResponseEntity<String> deleteMovieByTitleRepository(@PathVariable String title) {
        return movieService.deleteMovieByTitleRepository(title);
    }

    @DeleteMapping("/by-mongo-template/movie-delete/{title}")
    public ResponseEntity<String> deleteMovieByTitleMongoTemplate(@PathVariable String title) {
        return movieService.deleteMovieByTitleMongoTemplate(title);
    }
}
