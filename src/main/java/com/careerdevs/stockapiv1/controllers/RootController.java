package com.careerdevs.stockapiv1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("")
public class RootController {
    @Autowired
    Environment env;
    @GetMapping("/")
    public ResponseEntity<String> rootRoute(){
        //return new ResponseEntity<>("Root route", HttpStatus.OK);
        return  ResponseEntity.ok("Root Route");

    }

    @GetMapping("/apikey")

    public ResponseEntity<?> apiKey(){
        return  ResponseEntity.ok(env.getProperty("AV_API_KEY"));
    }
}
