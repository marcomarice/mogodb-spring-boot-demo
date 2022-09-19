package it.bitrock.mongodbspringbootdemo.controller;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocumentControllerTest {

    @Autowired
    DocumentController documentController;

    @Test
    void getAllMovieGenresPositiveTest() {
        ResponseEntity<List<Document>> responseEntityMoviesGenres = documentController.getAllMovieGenres();
        assertEquals(HttpStatus.OK, responseEntityMoviesGenres.getStatusCode());
        assertEquals(26,
                Objects.requireNonNull(responseEntityMoviesGenres.getBody()).size());
        assertNotNull(responseEntityMoviesGenres);
    }
}
