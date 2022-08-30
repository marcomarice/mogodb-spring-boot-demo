package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private Address address;
    private Geo geo;
}
