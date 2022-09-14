package it.bitrock.mongodbspringbootdemo.model.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.UnwindOptions;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

@Component
public class MovieQueryUtils {

    @Autowired
    QueryUtils queryUtils;

    private static final String MOVIE_COLLECTION = "movies";
    private static final String COMMENT_COLLECTION = "comments";

    @Value("${spring.data.mongodb.database}")
    private String database;

    public Movie getMovieByIdMongoClient(String id) {
        MongoClient mongoClient = queryUtils.createMongoClientWithDefaultCodec();
        Movie movie = queryUtils.getMoviesCollection(mongoClient, MOVIE_COLLECTION)
                .find(eq("_id", new ObjectId(id))).first();
        queryUtils.closeMongoClient(mongoClient);
        return movie;
    }

    public List<Movie> getMoviesByYearMongoClient(Integer year) {
        MongoClient mongoClient = queryUtils.createMongoClientWithDefaultCodec();
        List<Movie> movies = queryUtils.getMoviesCollection(mongoClient, MOVIE_COLLECTION)
                .find(eq("year", year)).into(new ArrayList<>());
        queryUtils.closeMongoClient(mongoClient);
        return movies;
    }

    public List<Document> getAllMovieGenres() {
        List<Bson> pipeline = new ArrayList<>();
        Bson unwindGenres = unwind("$genres",
                new UnwindOptions().preserveNullAndEmptyArrays(true));
        Bson groupGenres = group("$genres");
        pipeline.add(unwindGenres);
        pipeline.add(groupGenres);
        return queryUtils.getDocumentsCollection(MOVIE_COLLECTION)
                .aggregate(pipeline)
                .into(new ArrayList<>());
    }

    public Movie getMovieByComment(String id) {
        MongoClient mongoClient = queryUtils.createMongoClientWithDefaultCodec();
        List<Bson> pipeline = new ArrayList<>();
        Bson matchComment = match(eq("_id", new ObjectId(id)));
        Bson lookupComment = lookup(MOVIE_COLLECTION, "movie_id", "_id", "movie");
        Bson projectComment = project(fields(include("movie"), excludeId()));
        Bson unwindComment = unwind("$movie",
                new UnwindOptions().preserveNullAndEmptyArrays(false));
        Bson replaceRootComment = replaceRoot("$movie");
        pipeline.add(matchComment);
        pipeline.add(lookupComment);
        pipeline.add(projectComment);
        pipeline.add(unwindComment);
        pipeline.add(replaceRootComment);

        Movie movie = queryUtils.getMoviesCollection(mongoClient, COMMENT_COLLECTION)
                .aggregate(pipeline).first();
        queryUtils.closeMongoClient(mongoClient);
        return movie;
    }
}
