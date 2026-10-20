package com.lta.backend.listeners;

import com.lta.backend.services.clientes;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Nuevo listener (Parte 3): atiende las consultas de clientes.
 * Lee "clientes-request", busca el cliente y responde en "clientes-response"
 * conservando la key (correlationId) para que el producer asocie la respuesta.
 */
@Log4j2
@Component
public class ClientesListener {

    @Autowired
    private clientes clientesService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(groupId = "group-clientes",
            topics = "clientes-request",
            containerFactory = "validMessageContainerFactory")
    public void clientesListener(ConsumerRecord<String, String> record) throws Exception {
        log.info("CLIENTES-LISTENER ::: consulta '{}' (id {}, particion {})",
                record.value(), record.key(), record.partition());

        String json = clientesService.buscarJson(record.value());

        kafkaTemplate.send("clientes-response", record.key(), json);
    }
}
