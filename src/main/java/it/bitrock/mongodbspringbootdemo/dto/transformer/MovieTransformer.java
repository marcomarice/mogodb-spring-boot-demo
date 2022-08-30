package it.bitrock.mongodbspringbootdemo.dto.transformer;

import it.bitrock.mongodbspringbootdemo.dto.MoviePostDto;
import it.bitrock.mongodbspringbootdemo.model.Movie;

public class MovieTransformer {
    private MovieTransformer() {

    }

    public static Movie fromMoviePostDtoToMovie(MoviePostDto moviePostDto) {
        Movie movie = new Movie();
        movie.setTitle(moviePostDto.getTitle());
        movie.setYear(moviePostDto.getYear());
        movie.setDirectors(moviePostDto.getDirectors());
        movie.setCast(moviePostDto.getCast());
        movie.setGenres(moviePostDto.getGenres());
        return movie;
    }
}
