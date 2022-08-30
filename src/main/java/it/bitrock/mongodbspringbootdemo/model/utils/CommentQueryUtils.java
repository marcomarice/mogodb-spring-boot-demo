package it.bitrock.mongodbspringbootdemo.model.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommentQueryUtils {

    @Autowired
    QueryUtils queryUtils;

    @Autowired
    MovieQueryUtils movieQueryUtils;

    @Value("${spring.data.mongodb.database}")
    private String database;

}
