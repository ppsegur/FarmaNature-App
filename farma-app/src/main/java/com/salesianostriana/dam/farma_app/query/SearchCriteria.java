package com.salesianostriana.dam.farma_app.query;
public record SearchCriteria(
        String key,
        String operation,
        Object value
) {}
