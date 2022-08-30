package it.bitrock.mongodbspringbootdemo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String street1;
    private String city;
    private String state;
    private String zipcode;
}
