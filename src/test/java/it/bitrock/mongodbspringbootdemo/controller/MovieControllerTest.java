package it.bitrock.mongodbspringbootdemo.controller;

import it.bitrock.mongodbspringbootdemo.dto.MoviePostDto;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieControllerTest {

    @Autowired
    MovieController movieController;

    @Autowired
    CommentController commentController;

    private static final String RIGHT_MOVIE_ID = "573a1390f29313caabcd4135";
    private static final String WRONG_MOVIE_ID = "xxx";

    @Test
    void getMovieByIdRepositoryPositiveTest() {
        ResponseEntity<Movie> responseEntityMovie = movieController.getMovieByIdRepository(RIGHT_MOVIE_ID);
        assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        assertEquals("Blacksmith Scene",
                Objects.requireNonNull(responseEntityMovie.getBody()).getTitle());
        assertNotNull(responseEntityMovie);
    }

    @Test
    void getMovieByIdMongoClientPositiveTest() {
        ResponseEntity<Movie> responseEntityMovie = movieController.getMovieByIdMongoClient(RIGHT_MOVIE_ID);
        assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        assertEquals("Blacksmith Scene",
                Objects.requireNonNull(responseEntityMovie.getBody()).getTitle());
        assertNotNull(responseEntityMovie);
    }

    @Test
    void getMovieByIdRepositoryNegativeTest() {
        ResponseEntity<Movie> responseEntityMovie = movieController.getMovieByIdRepository(WRONG_MOVIE_ID);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityMovie.getStatusCode());
        assertNull(responseEntityMovie.getBody());
    }

    @Test
    void getMovieByTitleRepositoryPositiveTest() {
        ResponseEntity<List<Movie>> responseEntityMovie = movieController.getMovieByTitleRepository("Blacksmith Scene");
        assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        assertEquals("Blacksmith Scene",
                Objects.requireNonNull(responseEntityMovie.getBody()).get(0).getTitle());
        assertNotNull(responseEntityMovie);
    }

    @Test
    void getMoviesByYearRepositoryPositiveTest() {
        ResponseEntity<List<Movie>> responseEntityMovies = movieController.getMoviesByYearRepository(2000);
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
        assertEquals(618, Objects.requireNonNull(responseEntityMovies.getBody()).size());
        assertNotNull(responseEntityMovies);
    }

    @Test
    void getMoviesByYearMongoClientPositiveTest() {
        ResponseEntity<List<Movie>> responseEntityMovies = movieController.getMoviesByYearMongoClient(2000);
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
        assertEquals(618, Objects.requireNonNull(responseEntityMovies.getBody()).size());
        assertNotNull(responseEntityMovies);
    }

    @Test
    void addMovieRepositoryPositiveTest() {
        MoviePostDto moviePostDto = new MoviePostDto(
                "Title 1 - addMovie Repository", 1988, Arrays.asList("Director 1", "Director 2"),
                Arrays.asList("Actor 1", "Actor 2"), Arrays.asList("Drama", "Horror"));
        ResponseEntity<String> responseEntityMovie = movieController.addMovieRepository(moviePostDto);
        assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
    }

    @Test
    void insertMovieMongoTemplatePositiveTest() {
        MoviePostDto moviePostDto = new MoviePostDto(
                "Title 1 - insertMovie MongoTemplate", 1988, Arrays.asList("Director 1", "Director 2"),
                Arrays.asList("Actor 1", "Actor 2"), Arrays.asList("Drama", "Horror"));
        ResponseEntity<String> responseEntityMovie = movieController.insertMovieMongoTemplate(moviePostDto);
        assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
    }

    @Test
    void saveMovieMongoTemplatePositiveTest() {
        MoviePostDto moviePostDto = new MoviePostDto(
                "Title 1 - saveMovie MongoTemplate", 1988, Arrays.asList("Director 1", "Director 2"),
                Arrays.asList("Actor 1", "Actor 2"), Arrays.asList("Drama", "Horror"));
        ResponseEntity<String> responseEntityMovie = movieController.saveMovieMongoTemplate(moviePostDto);
        assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
    }

    @Test
    void updateMovieTitleRepositoryPositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController.updateMovieTitleRepository("Title 1");
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void updateMovieTitleMongoTemplatePositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController.updateMovieTitleMongoTemplate("Title 1");
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void updateMoviesByYearRepositoryPositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController.updateMoviesByYearRepository(1990);
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void updateMoviesByYearMongoTemplatePositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController.updateMoviesByYearMongoTemplate(1990);
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void deleteMovieByTitleRepositoryPositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController
                .deleteMovieByTitleRepository("Title 1 - addMovie Repository");
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void deleteMovieByTitleMongoTemplatePositiveTest() {
        ResponseEntity<String> responseEntityMovies = movieController
                .deleteMovieByTitleMongoTemplate("Title 1 - insertMovie MongoTemplate");
        assertEquals(HttpStatus.OK, responseEntityMovies.getStatusCode());
    }

    @Test
    void getMovieByCommentPositiveTest() {
        ResponseEntity<Movie> responseEntityMovie = commentController
                .getMovieByComment("5a9427648b0beebeb69579e7");
        assertEquals(HttpStatus.OK, responseEntityMovie.getStatusCode());
        assertEquals("The Land Beyond the Sunset",
                Objects.requireNonNull(responseEntityMovie.getBody()).getTitle());
    }
}
