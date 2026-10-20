package com.lta.backend.services;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResponseListener {

    public static String response = null;

    @KafkaListener(topics = "str-topic-response", groupId = "group-1")
    public void listen(String message){
        response = message;
    }
}