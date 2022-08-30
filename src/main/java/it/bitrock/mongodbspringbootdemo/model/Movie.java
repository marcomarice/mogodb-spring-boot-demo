package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("movies")
public class Movie {

    @BsonProperty("_id")
    private ObjectId id;
    private String title;
    private Integer year;
    private Integer runtime;
    private List<String> directors;
    private List<String> cast;
    private List<String> genres;
    private LocalDateTime released;
    private String plot;
    @BsonProperty("fullplot")
    private String fullPlot;
    private List<String> countries;
    private String rated;
    private Awards awards;
    private Imdb imdb;
    private String type;
    private Tomatoes tomatoes;
    @BsonProperty("num_mflix_comments")
    private Integer numMflixComments;
    @BsonProperty("lastupdate")
    private LocalDateTime lastUpdate;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", runtime=" + runtime +
                ", directors=" + directors +
                ", cast=" + cast +
                ", genres=" + genres +
                ", released=" + released +
                ", plot='" + plot + '\'' +
                ", fullPlot='" + fullPlot + '\'' +
                ", countries=" + countries +
                ", rated='" + rated + '\'' +
                ", awards=" + awards +
                ", imdb=" + imdb +
                ", type='" + type + '\'' +
                ", tomatoes=" + tomatoes +
                ", numMflixComments=" + numMflixComments +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
