package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("comments")
public class Comment {
    @BsonProperty("_id")
    private ObjectId id;
    private String name;
    private String email;
    @BsonProperty("movie_id")
    private ObjectId movieId;
    private String text;
    private LocalDateTime date;
}
