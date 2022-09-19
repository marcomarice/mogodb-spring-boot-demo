package it.bitrock.mongodbspringbootdemo.controller;

import it.bitrock.mongodbspringbootdemo.service.DocumentService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping("/document/movie-genres")
    public ResponseEntity<List<Document>> getAllMovieGenres() {
        return documentService.getAllMovieGenres();
    }
}
