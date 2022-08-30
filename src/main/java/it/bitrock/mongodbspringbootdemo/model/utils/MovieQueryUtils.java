package it.bitrock.mongodbspringbootdemo.model.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.UnwindOptions;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

@Component
public class MovieQueryUtils {

    @Autowired
    QueryUtils queryUtils;

    private static final String MOVIE_COLLECTION = "movies";

    @Value("${spring.data.mongodb.database}")
    private String database;

    public List<Movie> getMoviesByYear(Integer year) {
        MongoClient openConnection = queryUtils.mongoClient;
//        MongoClient openConnection = queryUtils.openConnection();
        List<Movie> movies = getMoviesCollection(openConnection, MOVIE_COLLECTION)
                .find(eq("year", year)).into(new ArrayList<>());
        queryUtils.closeConnection(openConnection);
        return movies;
    }

    public List<Document> getAllMovieGenres() {
        List<Bson> pipeline = new ArrayList<>();
        Bson unwindGenres = Aggregates.unwind("$genres",
                new UnwindOptions().preserveNullAndEmptyArrays(true));
        Bson groupGenres = Aggregates.group("$genres");
        pipeline.add(unwindGenres);
        pipeline.add(groupGenres);
        return queryUtils.getDocumentsCollection(queryUtils.mongoClient, MOVIE_COLLECTION)
                .aggregate(pipeline)
                .into(new ArrayList<>());
    }

    public Movie getMovieByComment(String id) {
        List<Bson> pipeline = new ArrayList<>();
        Bson matchMovie = Aggregates.match(eq("_id", id));
        Bson lookupMovie = Aggregates.lookup(MOVIE_COLLECTION, "movie_id", "_id", "movie");
        Bson projectMovie = Aggregates.project(fields(include("movie"), excludeId()));
        Bson unwindMovie = Aggregates.unwind("$movie",
                new UnwindOptions().preserveNullAndEmptyArrays(false));
        pipeline.add(matchMovie);
        pipeline.add(lookupMovie);
        pipeline.add(projectMovie);
        pipeline.add(unwindMovie);
        return getMoviesCollection(queryUtils.mongoClient, MOVIE_COLLECTION)
                .aggregate(pipeline).first();
    }

    private MongoCollection<Movie> getMoviesCollection(MongoClient mongoClient, String collection) {
        return mongoClient.getDatabase(database)
                .getCollection(collection, Movie.class);
    }
}
