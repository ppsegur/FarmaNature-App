package com.salesianostriana.dam.farma_app.upload.dtos;

import lombok.Builder;
import lombok.Data;


@Builder
public record FileResponse(
        String id,
        String name,
        String uri,
        String type,
        long size
) {
    @Builder
    public static FileResponse of(String id, String name, String uri, String type, long size) {
        return new FileResponse(id, name, uri, type, size);
    }
}