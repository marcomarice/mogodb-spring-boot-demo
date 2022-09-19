package it.bitrock.mongodbspringbootdemo.model.utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UnwindOptions;
import io.vavr.control.Try;
import it.bitrock.mongodbspringbootdemo.config.MongoDBConfiguration;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.unwind;

@Component
public class DocumentQueryUtils {

    @Autowired
    MongoDBConfiguration mongoDBConfiguration;

    @Value("${spring.data.mongodb.database}")
    private String database;

    private static final String MOVIE_COLLECTION = "movies";

    public List<Document> getAllMovieGenres() {
        List<Bson> pipeline = new ArrayList<>();
        Bson unwindGenres = unwind("$genres",
                new UnwindOptions().preserveNullAndEmptyArrays(true));
        Bson groupGenres = group("$genres");
        pipeline.add(unwindGenres);
        pipeline.add(groupGenres);
        return getDocumentsCollection()
                .aggregate(pipeline)
                .into(new ArrayList<>());
    }

    private MongoCollection<Document> getDocumentsCollection() {
        return Try.of(() -> mongoDBConfiguration.mongoClient())
                .map(mc -> mc.getDatabase(database).getCollection(DocumentQueryUtils.MOVIE_COLLECTION))
                .getOrElse(() -> null);
    }
}
