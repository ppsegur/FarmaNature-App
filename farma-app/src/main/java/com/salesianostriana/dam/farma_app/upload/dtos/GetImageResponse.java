package com.salesianostriana.dam.farma_app.upload.dtos;

public record GetImageResponse(        String success,
                                       int status,
                                       GetImageInfo data
) {
}
