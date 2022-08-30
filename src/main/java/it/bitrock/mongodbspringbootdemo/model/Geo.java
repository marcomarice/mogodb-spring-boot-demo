package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Geo {
    private String type;
    private List<Double> coordinates;
}
