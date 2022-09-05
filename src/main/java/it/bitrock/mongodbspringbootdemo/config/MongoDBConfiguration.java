package it.bitrock.mongodbspringbootdemo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class MongoDBConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory,
                                       MongoConverter mongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
    }

    @Bean
    public MongoClient mongoClient(@Value("${spring.data.mongodb.uri}") String connectionString) {
        return MongoClients.create(connectionString);
    }
}
