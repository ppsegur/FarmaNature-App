package com.salesianostriana.dam.farma_app.upload.dtos;


public record NewImageResponse(
        String success,
        int status,
        NewImageInfo data
) {
}
