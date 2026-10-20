package com.lta.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class clientes {

    public record Cliente(int id, String nombre, String ciudad, String email) {}

    // "Base de datos" en memoria
    private static final List<Cliente> CLIENTES = List.of(
            new Cliente(1, "Ana Torres", "Monterrey", "ana.torres@mail.com"),
            new Cliente(2, "Luis Ramirez", "Guadalajara", "luis.ramirez@mail.com"),
            new Cliente(3, "Maria Lopez", "Ciudad de Mexico", "maria.lopez@mail.com"),
            new Cliente(4, "Carlos Mendez", "Monterrey", "carlos.mendez@mail.com"),
            new Cliente(5, "Sofia Herrera", "Puebla", "sofia.herrera@mail.com"),
            new Cliente(6, "Jorge Castillo", "Guadalajara", "jorge.castillo@mail.com"),
            new Cliente(7, "Laura Diaz", "Queretaro", "laura.diaz@mail.com"),
            new Cliente(8, "Pedro Navarro", "Ciudad de Mexico", "pedro.navarro@mail.com")
    );

    @Autowired
    private ObjectMapper objectMapper;

    // Busca por id exacto o por texto en nombre, ciudad o email. Vacío = todos.
    public List<Cliente> buscar(String query) {
        String q = query == null ? "" : query.trim().toLowerCase();
        if (q.isEmpty()) {
            return CLIENTES;
        }
        return CLIENTES.stream()
                .filter(c -> String.valueOf(c.id()).equals(q)
                        || c.nombre().toLowerCase().contains(q)
                        || c.ciudad().toLowerCase().contains(q)
                        || c.email().toLowerCase().contains(q))
                .toList();
    }

    public String buscarJson(String query) throws JsonProcessingException {
        List<Cliente> resultado = buscar(query);
        log.info("Consulta '{}' -> {} cliente(s)", query, resultado.size());
        return objectMapper.writeValueAsString(resultado);
    }

    public String ordenar(String message){

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
