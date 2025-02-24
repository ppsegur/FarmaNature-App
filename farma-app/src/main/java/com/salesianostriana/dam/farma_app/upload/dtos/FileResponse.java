package com.salesianostriana.dam.farma_app.upload.dtos;


import lombok.Builder;

@Builder
public record FileResponse(
        String id,
        String name,
        String uri,
        String type,
        long size
)
{}