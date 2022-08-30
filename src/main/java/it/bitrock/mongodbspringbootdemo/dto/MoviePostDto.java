package it.bitrock.mongodbspringbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoviePostDto {
    private String title;
    private Integer year;
    private List<String> directors;
    private List<String> cast;
    private List<String> genres;
}
