package com.lta.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Publica consultas de clientes en "clientes-request" y espera la respuesta
 * que llega por "clientes-response". Cada consulta lleva un correlationId
 * (key del mensaje), así varias consultas simultáneas no se mezclan.
 */
@Service
@Slf4j
public class ClientesProducerService {

    public static final String REQUEST_TOPIC = "clientes-request";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // correlationId -> respuesta pendiente
    private final Map<String, CompletableFuture<String>> pending = new ConcurrentHashMap<>();

    public String consultar(String query, long timeoutMs) throws Exception {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        pending.put(correlationId, future);

        try {
            kafkaTemplate.send(REQUEST_TOPIC, correlationId, query).whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Error al enviar consulta {}: {}", correlationId, ex.getMessage());
                    future.completeExceptionally(ex);
                } else {
                    log.info("Consulta {} enviada a {} (particion {}, offset {})", correlationId, REQUEST_TOPIC,
                            result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                }
            });
            return future.get(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log.warn("Sin respuesta para la consulta {}", correlationId);
            throw e;
        } finally {
            pending.remove(correlationId);
        }
    }

    // Lo invoca ClientesResponseListener cuando llega una respuesta
    public void completar(String correlationId, String respuesta) {
        CompletableFuture<String> future = pending.get(correlationId);
        if (future != null) {
            future.complete(respuesta);
        }
    }
}
