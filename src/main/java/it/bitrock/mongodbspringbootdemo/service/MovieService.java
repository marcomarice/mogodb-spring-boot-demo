package it.bitrock.mongodbspringbootdemo.service;

import io.vavr.control.Option;
import it.bitrock.mongodbspringbootdemo.dto.MoviePostDto;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import it.bitrock.mongodbspringbootdemo.model.utils.MovieQueryUtils;
import it.bitrock.mongodbspringbootdemo.repository.MovieRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static it.bitrock.mongodbspringbootdemo.dto.transformer.MovieTransformer.fromMoviePostDtoToMovie;

@Service
public class MovieService {

    @Autowired
    MovieQueryUtils movieQueryUtils;

    @Autowired
    MovieRepository movieRepository;

    private static final String FAILURE_MESSAGE = "Missing some important info about the movie";

    public ResponseEntity<Movie> getMovieByIdRepository(String movieId) {
        return Option.of(movieRepository.findById(movieId))
                .map(movie -> ResponseEntity.ok().body(movie.get()))
                .getOrElse(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    public ResponseEntity<Movie> getMovieByIdMongoClient(String movieId) {
        return Option.of(movieQueryUtils.getMovieByIdMongoClient(movieId))
                .map(movie -> ResponseEntity.ok().body(movie))
                .getOrElse(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    public ResponseEntity<List<Movie>> getMovieByTitleRepository(String movieTitle) {
        return Option.of(movieRepository.findByTitle(movieTitle))
                .map(movie -> ResponseEntity.ok().body(movie))
                .getOrElse(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    public ResponseEntity<List<Movie>> getMoviesByYearRepository(Integer year) {
        if (year != null && year >= 1900) {
            return ResponseEntity.ok().body(movieRepository.findByYear(year));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<List<Movie>> getMoviesByYearMongoClient(Integer year) {
        if (year != null && year >= 1900) {
            return ResponseEntity.ok().body(movieQueryUtils.getMoviesByYearMongoClient(year));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<List<Document>> getAllMovieGenres() {
        return ResponseEntity.ok().body(movieQueryUtils.getAllMovieGenres());
    }

    public ResponseEntity<String> addMovie(MoviePostDto moviePostDto) {
        if (moviePostDto != null &&
                !moviePostDto.getTitle().isEmpty() &&
                moviePostDto.getYear() != null &&
                moviePostDto.getYear() > 1900 &&
                !moviePostDto.getDirectors().isEmpty() &&
                !moviePostDto.getCast().isEmpty() &&
                !moviePostDto.getGenres().isEmpty()) {
            movieRepository.save(fromMoviePostDtoToMovie(moviePostDto));
            return new ResponseEntity<>("Congratulation Movie added successfully!",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(FAILURE_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateMovie(String title) {
        return Option.of(movieRepository.findByTitle(title).get(0))
                .map(movie -> {
                    movie.setYear(2020);
                    movieRepository.save(movie);
                    return new ResponseEntity<>(
                            "Congratulation Movie updated successfully!",
                            HttpStatus.OK);
                }).getOrElse(() -> new ResponseEntity<>(
                        FAILURE_MESSAGE,
                        HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity<String> updateMovies(Integer year) {
        if (year != null && year > 1900) {
            List<Movie> movies = movieRepository.findByYear(year);
            movies.forEach(movie -> movie.setLastUpdate(LocalDateTime.now()));
            movieRepository.saveAll(movies);
            return new ResponseEntity<>("Congratulation Movies updated successfully!",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Missing some important info about the movies", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteMovie(String title) {
        return Option.of(movieRepository.findByTitle(title).get(0))
                .map(movie -> {
                    movieRepository.delete(movie);
                    return new ResponseEntity<>(
                            "Congratulation Movie delete successfully!",
                            HttpStatus.OK);
                })
                .getOrElse(() -> new ResponseEntity<>(
                        FAILURE_MESSAGE,
                        HttpStatus.BAD_REQUEST));
    }
}
