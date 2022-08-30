package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("theaters")
public class Theater {
    @BsonProperty("_id")
    private ObjectId id;
    private String theaterId;
    private Location locaction;
}
