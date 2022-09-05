package it.bitrock.mongodbspringbootdemo.model.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import it.bitrock.mongodbspringbootdemo.model.Movie;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Component;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
public class QueryUtils {

    @Autowired
    MongoClient mongoClient;

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    public MongoClient createWithDefaultPojoCodecRegistry() {
        return MongoClients.create(defaultPojoCodecRegistry());
    }

    public void closeConnection(MongoClient mongoClient) {
        mongoClient.close();
    }

    public MongoCollection<Document> getDocumentsCollection(MongoClient mongoClient, String collection) {
        return mongoClient.getDatabase(database)
                .getCollection(collection);
    }

    public MongoCollection<Movie> getMoviesCollection(MongoClient mongoClient, String collection) {
        return mongoClient.getDatabase(database)
                .getCollection(collection, Movie.class);
    }

    private MongoClientSettings defaultPojoCodecRegistry() {
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongodbUri))
                .codecRegistry(pojoCodecRegistry)
                .build();
    }
}
