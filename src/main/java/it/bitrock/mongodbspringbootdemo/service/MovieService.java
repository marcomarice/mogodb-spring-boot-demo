package it.bitrock.mongodbspringbootdemo.service;

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
import java.util.Optional;

import static it.bitrock.mongodbspringbootdemo.dto.transformer.MovieTransformer.fromMoviePostDtoToMovie;

@Service
public class MovieService {

    @Autowired
    MovieQueryUtils movieQueryUtils;

    @Autowired
    MovieRepository movieRepository;

    public ResponseEntity<Movie> getMovieById(String movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            return ResponseEntity.ok().body(movieOptional.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<List<Movie>> getMovieByTitle(String movieTitle) {
        Optional<List<Movie>> movieOptional = Optional.ofNullable(
                movieRepository.findByTitle(movieTitle));
        if (movieOptional.isPresent()) {
            return ResponseEntity.ok().body(movieOptional.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<List<Movie>> getMoviesByYear(Integer year) {
        if (year != null && year >= 1900) {
//            return ResponseEntity.ok().body(movieQueryUtils.getMoviesByYear(year));
            return ResponseEntity.ok().body(movieRepository.findByYear(year));
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
        return new ResponseEntity<>("Missing some important info about the movie", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateMovie(String title) {
        if (!title.isEmpty()) {
            Movie movie = movieRepository.findByTitle(title).get(0);
            movie.setYear(2020);
            movieRepository.save(movie);
            return new ResponseEntity<>("Congratulation Movie updated successfully!",
                            HttpStatus.OK);
        }
        return new ResponseEntity<>("Missing some important info about the movie", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateMovies(Integer year) {
        if (year != null && year > 1900) {
            List<Movie> movies = movieRepository.findByYear(year);
            movies.stream()
                    .forEach(movie -> movie.setLastUpdate(LocalDateTime.now()));
            movieRepository.saveAll(movies);
            return new ResponseEntity<>("Congratulation Movies updated successfully!",
                            HttpStatus.OK);
        }
        return new ResponseEntity<>("Missing some important info about the movies", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteMovie(String title) {
        if (!title.isEmpty()) {
            Movie movie = movieRepository.findByTitle(title).get(0);
            movieRepository.delete(movie);
            return new ResponseEntity<>("Congratulation Movie delete successfully!",
                            HttpStatus.OK);
        }
        return new ResponseEntity<>("Missing some important info about the movie", HttpStatus.BAD_REQUEST);
    }
}
