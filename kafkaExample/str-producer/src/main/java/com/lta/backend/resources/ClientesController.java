package com.lta.backend.resources;

import com.lta.backend.services.ClientesProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    private static final long TIMEOUT_MS = 5000;

    @Autowired
    private ClientesProducerService producer;

    // GET /clientes?query=ana  -> JSON con los clientes encontrados
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> consultar(@RequestParam(defaultValue = "") String query) throws Exception {
        try {
            return ResponseEntity.ok(producer.consultar(query, TIMEOUT_MS));
        } catch (TimeoutException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body("{\"error\":\"Sin respuesta del servicio de clientes\"}");
        }
    }
}
