package it.bitrock.mongodbspringbootdemo.service;

import it.bitrock.mongodbspringbootdemo.model.utils.DocumentQueryUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    DocumentQueryUtils documentQueryUtils;

    public ResponseEntity<List<Document>> getAllMovieGenres() {
        return ResponseEntity.ok().body(documentQueryUtils.getAllMovieGenres());
    }
}
