package it.bitrock.mongodbspringbootdemo.model.utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UnwindOptions;
import io.vavr.control.Try;
import it.bitrock.mongodbspringbootdemo.config.MongoDBConfiguration;
import it.bitrock.mongodbspringbootdemo.model.Movie;
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
    MongoDBConfiguration mongoDBConfiguration;

    private static final String MOVIE_COLLECTION = "movies";
    private static final String COMMENT_COLLECTION = "comments";

    @Value("${spring.data.mongodb.database}")
    private String database;

    public Movie getMovieByIdMongoClient(String id) {
        return getMoviesCollection(MOVIE_COLLECTION)
                .find(eq("_id", new ObjectId(id))).first();
    }

    public List<Movie> getMoviesByYearMongoClient(Integer year) {
        return getMoviesCollection(MOVIE_COLLECTION)
                .find(eq("year", year)).into(new ArrayList<>());
    }

    public Movie getMovieByComment(String id) {
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

        return getMoviesCollection(COMMENT_COLLECTION)
                .aggregate(pipeline).first();
    }

    private MongoCollection<Movie> getMoviesCollection(String collection) {
        return Try.of(() -> mongoDBConfiguration.createMongoClientWithDefaultCodec())
                .map(mc -> mc.getDatabase(database).getCollection(collection, Movie.class))
                .getOrElse(() -> null);
    }
}
