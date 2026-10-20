package com.lta.backend.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Escucha las respuestas de clientes y las entrega a quien las espera
 * usando la key del mensaje (correlationId).
 */
@Component
public class ClientesResponseListener {

    @Autowired
    private ClientesProducerService service;

    @KafkaListener(topics = "clientes-response", groupId = "group-clientes-response")
    public void listen(ConsumerRecord<String, String> record) {
        service.completar(record.key(), record.value());
    }
}
