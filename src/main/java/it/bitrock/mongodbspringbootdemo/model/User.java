package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("users")
public class User {
    @BsonProperty("_id")
    private ObjectId id;
    private String name;
    private String email;
    private String password;
}
