package it.bitrock.mongodbspringbootdemo.controller;

import it.bitrock.mongodbspringbootdemo.dto.MoviePostDto;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@Slf4j
class MovieControllerTest {

    private static final String RIGHT_MOVIE_ID = "573a1390f29313caabcd4135";
    private static final String WRONG_MOVIE_ID = "xxx";

    @Autowired
    MovieController movieController;

    @Autowired
    CommentController commentController;

    @Test
    void getMovieByIdRepositoryPositiveTest() {
        ResponseEntity<Movie> responseEntityMovie = movieController.getMovieByIdRepository(RIGHT_MOVIE_ID);
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        Assertions.assertEquals("Blacksmith Scene", Objects.requireNonNull(responseEntityMovie.getBody()).getTitle());
        Assertions.assertNotNull(responseEntityMovie);
    }

    @Test
    void getMovieByIdMongoClientPositiveTest() {
        ResponseEntity<Movie> responseEntityMovie = movieController.getMovieByIdMongoClient(RIGHT_MOVIE_ID);
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        Assertions.assertEquals("Blacksmith Scene", Objects.requireNonNull(responseEntityMovie.getBody()).getTitle());
        Assertions.assertNotNull(responseEntityMovie);
    }

    @Test
    void getMovieByIdNegativeTest() {
        ResponseEntity<Movie> responseEntityMovie = movieController.getMovieByIdRepository(WRONG_MOVIE_ID);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntityMovie.getStatusCode());
        Assertions.assertNull(responseEntityMovie.getBody());
    }

    @Test
    void getMovieByTitleRepositoryPositiveTest() {
        ResponseEntity<List<Movie>> responseEntityMovie = movieController.getMovieByTitleRepository("Blacksmith Scene");
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        Assertions.assertEquals("Blacksmith Scene", Objects.requireNonNull(responseEntityMovie.getBody()).get(0).getTitle());
        Assertions.assertNotNull(responseEntityMovie);
    }

    @Test
    void getMoviesByYearRepositoryPositiveTest() {
        ResponseEntity<List<Movie>> responseEntityMovies = movieController.getMoviesByYearRepository(2000);
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
        Assertions.assertEquals(618, Objects.requireNonNull(responseEntityMovies.getBody()).size());
        Assertions.assertNotNull(responseEntityMovies);
    }

    @Test
    void getMoviesByYearMongoClientPositiveTest() {
        ResponseEntity<List<Movie>> responseEntityMovies = movieController.getMoviesByYearMongoClient(2000);
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
        Assertions.assertEquals(618, Objects.requireNonNull(responseEntityMovies.getBody()).size());
        Assertions.assertNotNull(responseEntityMovies);
    }

    @Test
    void getAllMovieGenresPositiveTest() {
        ResponseEntity<List<Document>> responseEntityMoviesGenres = movieController.getAllMovieGenres();
        Assertions.assertEquals(HttpStatus.OK, responseEntityMoviesGenres.getStatusCode());
        Assertions.assertEquals(26, Objects.requireNonNull(responseEntityMoviesGenres.getBody()).size());
        Assertions.assertNotNull(responseEntityMoviesGenres);
    }

    @Test
    void addMoviePositiveTest() {
        MoviePostDto moviePostDto = new MoviePostDto(
                "Title 1", 1988, Arrays.asList("Director 1", "Director 2"),
                Arrays.asList("Actor 1", "Actor 2"), Arrays.asList("Drama", "Horror"));
        ResponseEntity<String> responseEntityMovie = movieController.addMovie(moviePostDto);
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
    }

    @Test
    void updateMoviePositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController.updateMovie("Title 1");
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void updateMoviesPositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController.updateMovies(1990);
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void deleteMoviePositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController.deleteMovie("Title 1");
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void getMovieByCommentPositiveTest() {
        ResponseEntity<Movie> responseEntityMovie = commentController
                .getMovieByComment("5a9427648b0beebeb69579e7");
        Assertions.assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        Assertions.assertEquals("The Land Beyond the Sunset", Objects.requireNonNull(responseEntityMovie.getBody()).getTitle());
    }
}
