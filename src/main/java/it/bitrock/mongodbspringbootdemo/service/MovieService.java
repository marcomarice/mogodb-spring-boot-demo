package it.bitrock.mongodbspringbootdemo.service;

import io.vavr.control.Option;
import it.bitrock.mongodbspringbootdemo.config.MongoDBConfiguration;
import it.bitrock.mongodbspringbootdemo.dto.MoviePostDto;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import it.bitrock.mongodbspringbootdemo.model.utils.MovieQueryUtils;
import it.bitrock.mongodbspringbootdemo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static it.bitrock.mongodbspringbootdemo.dto.transformer.MovieTransformer.fromMoviePostDtoToMovie;

@Service
public class MovieService {

    @Autowired
    MongoDBConfiguration mongoDBConfiguration;

    @Autowired
    MovieQueryUtils movieQueryUtils;

    @Autowired
    MovieRepository movieRepository;

    private static final Predicate<MoviePostDto> IS_MOVIE_VALUES_VALID = moviePostDto ->
            moviePostDto != null &&
                    !moviePostDto.getTitle().isEmpty() &&
                    moviePostDto.getYear() != null &&
                    moviePostDto.getYear() > 1900 &&
                    !moviePostDto.getDirectors().isEmpty() &&
                    !moviePostDto.getCast().isEmpty() &&
                    !moviePostDto.getGenres().isEmpty();
    private static final String MOVIE_SUCCESS_MESSAGE = "Congratulation Movie added successfully!";
    private static final String MOVIE_FAILURE_MESSAGE = "Missing some important info about the movie";

    public ResponseEntity<Movie> getMovieByIdRepository(String movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        return movieOptional
                .map(movie -> ResponseEntity.ok().body(movie))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
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

    public ResponseEntity<Movie> getMovieByTitleMongoTemplate(String movieTitle) {
        Movie movieByTitle = mongoDBConfiguration.mongoTemplate()
                .findOne(Query.query(Criteria.where("title").is(movieTitle)), Movie.class);

        return Option.of(movieByTitle)
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

    public ResponseEntity<String> addMovieRepository(MoviePostDto moviePostDto) {
        if (IS_MOVIE_VALUES_VALID.test(moviePostDto)) {
            movieRepository.save(fromMoviePostDtoToMovie(moviePostDto));
            return new ResponseEntity<>(MOVIE_SUCCESS_MESSAGE, HttpStatus.OK);
        }
        return new ResponseEntity<>(MOVIE_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> insertMovieMongoTemplate(MoviePostDto moviePostDto) {
        if (IS_MOVIE_VALUES_VALID.test(moviePostDto)) {
            mongoDBConfiguration.mongoTemplate().insert(fromMoviePostDtoToMovie(moviePostDto));
            return new ResponseEntity<>(MOVIE_SUCCESS_MESSAGE, HttpStatus.OK);
        }
        return new ResponseEntity<>(MOVIE_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> saveMovieMongoTemplate(MoviePostDto moviePostDto) {
        if (IS_MOVIE_VALUES_VALID.test(moviePostDto)) {
            mongoDBConfiguration.mongoTemplate().save(fromMoviePostDtoToMovie(moviePostDto));
            return new ResponseEntity<>(MOVIE_SUCCESS_MESSAGE, HttpStatus.OK);
        }
        return new ResponseEntity<>(MOVIE_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateMovieTitleRepository(String title) {
        return Option.of(movieRepository.findByTitle(title).get(0))
                .map(movie -> {
                    movie.setTitle(title);
                    movieRepository.save(movie);
                    return new ResponseEntity<>("Congratulation Movie updated successfully!", HttpStatus.OK);
                }).getOrElse(() -> new ResponseEntity<>(MOVIE_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity<String> updateMovieTitleMongoTemplate(String movieTitle) {
        return Option.of(getMovieByTitleMongoTemplate(movieTitle).getBody())
                .map(movie -> {
                    movie.setTitle(movieTitle);
                    mongoDBConfiguration.mongoTemplate().save(movie);
                    return new ResponseEntity<>("Congratulation Movie updated successfully!", HttpStatus.OK);
                }).getOrElse(() -> new ResponseEntity<>(MOVIE_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity<String> updateMoviesByYearRepository(Integer year) {
        if (year != null && year > 1900) {
            List<Movie> movies = movieRepository.findByYear(year);
            movies.forEach(movie -> movie.setLastUpdate(LocalDateTime.now()));
            movieRepository.saveAll(movies);
            return new ResponseEntity<>("Congratulation Movies updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Missing some important info about the movies", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateMoviesByYearMongoTemplate(Integer year) {
        if (year != null && year > 1900) {
            Query query = new Query();
            query.addCriteria(Criteria.where("year").is(year));
            Update update = new Update();
            update.set("lastupdated", LocalDateTime.now());
            mongoDBConfiguration.mongoTemplate().updateMulti(query, update, Movie.class);
            return new ResponseEntity<>("Congratulation Movies updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Missing some important info about the movies", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteMovieByTitleRepository(String title) {
        return Option.of(movieRepository.findByTitle(title).get(0))
                .map(movie -> {
                    movieRepository.delete(movie);
                    return new ResponseEntity<>("Congratulation Movie delete successfully!", HttpStatus.OK);
                })
                .getOrElse(() -> new ResponseEntity<>(MOVIE_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity<String> deleteMovieByTitleMongoTemplate(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        Movie movie1 = mongoDBConfiguration.mongoTemplate().findOne(query, Movie.class);
        return Option.of(movie1)
                .map(movie -> {
                    mongoDBConfiguration.mongoTemplate().remove(movie);
                    return new ResponseEntity<>("Congratulation Movie delete successfully!", HttpStatus.OK);
                })
                .getOrElse(() -> new ResponseEntity<>(MOVIE_FAILURE_MESSAGE, HttpStatus.BAD_REQUEST));
    }
}
