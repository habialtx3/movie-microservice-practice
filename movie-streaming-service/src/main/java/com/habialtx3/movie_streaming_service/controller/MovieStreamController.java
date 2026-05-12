package com.habialtx3.movie_streaming_service.controller;

import org.apache.http.entity.InputStreamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MovieStreamController {

    private static final Logger log = Logger.getLogger(MovieStreamController.class.getName());

    @Autowired
    private MovieCatalogService movieCatalogService;

    public static String VIDEO_DIRECTORY = "C:/Users/Acer/Videos/";

    @GetMapping("/stream/{videoPath}")
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable String videoPath) throws FileNotFoundException {
        File file = new File(VIDEO_DIRECTORY + videoPath);

        if (file.exists()) {
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .body(inputStreamResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stream/with-id/{movieInfoId}")
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable Long movieInfoId) throws FileNotFoundException {
        String moviePath = movieCatalogService.getMoviePath(movieInfoId);
        log.log(Level.INFO, "Resolver movie path : {0}",moviePath);
        return streamVideo(moviePath);
    }
}
