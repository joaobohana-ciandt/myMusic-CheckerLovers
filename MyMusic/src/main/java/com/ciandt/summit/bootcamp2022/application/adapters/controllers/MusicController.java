package com.ciandt.summit.bootcamp2022.application.adapters.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {
    private static Logger logger = LoggerFactory.getLogger(MusicController.class.getSimpleName());
    public static void main(String[] args) {
        logger.error("teste");
    }

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("67f5976c-eb1e-404e-8220-2c2a8a23be47");
    }
}
