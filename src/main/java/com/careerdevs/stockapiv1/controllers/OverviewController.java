package com.careerdevs.stockapiv1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";


    @GetMapping("/test")

    public ResponseEntity<?> testOverView(RestTemplate restTemplate){
        try{
            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol=IBM&apikey="+ apiKey;

            String  responseKey = restTemplate.getForObject(url,String.class);

           return ResponseEntity.ok(responseKey);
        }catch(Exception e){
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }


    }

    @GetMapping("/{symbol}")

    public ResponseEntity<?> dynamicOverview (RestTemplate restTemplate, @PathVariable String symbol){
        try{

            String apiKey = env.getProperty("AV_API_KEY");
            String url = BASE_URL + "&symbol="+ symbol +"&apikey="+ apiKey;

            String  alphaVantageResponse = restTemplate.getForObject(url,String.class);

            return ResponseEntity.ok(alphaVantageResponse);
        }catch(Exception e){
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
