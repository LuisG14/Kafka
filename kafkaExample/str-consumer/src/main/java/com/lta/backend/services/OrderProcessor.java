package com.lta.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProcessor {

    public String process(String message){

        String msg = message.toLowerCase();
        String response;

        if(msg.contains("vegan")){
            response = "Vegan menu: tofu bowl, quinoa salad, green smoothie";
        } 
        else if(msg.contains("vegetarian")){
            response = "Vegetarian menu: pesto pasta, margherita pizza, caprese salad";
        } 
        else if(msg.contains("meat") || msg.contains("chicken") || msg.contains("beef")){
            response = "Meat menu: burger, beef tacos, roasted chicken";
        } 
        else {
            response = "General menu: pasta, salad, soup";
        }

        log.info("Generated response: {}", response);

        return response;
    }
}