package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("sessions")
public class Session {
    @BsonProperty("_id")
    private ObjectId id;
    @BsonProperty("user_id")
    private ObjectId userId;
    private String jwt;
}
