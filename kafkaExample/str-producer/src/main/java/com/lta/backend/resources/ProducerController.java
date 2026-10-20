package com.lta.backend.resources;

import com.lta.backend.services.ResponseListener;
import com.lta.backend.services.StringProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private StringProducerService producer;

    @GetMapping("/menu")
    public String sendMenu(@RequestParam String message) throws InterruptedException {
        return sendAndWait(message);
    }

    @GetMapping("/test")
    public String sendTest(@RequestParam String message) throws InterruptedException {
        return sendAndWait(message);
    }

    // MÉTODO REUTILIZABLE
    private String sendAndWait(String message) throws InterruptedException {

        ResponseListener.response = null;

        producer.sendMessage(message);

        int attempts = 0;

        while(ResponseListener.response == null && attempts < 10){
            Thread.sleep(500);
            attempts++;
        }

        return ResponseListener.response != null
                ? ResponseListener.response
                : "No response (posiblemente fue a partición 0)";
    }
}