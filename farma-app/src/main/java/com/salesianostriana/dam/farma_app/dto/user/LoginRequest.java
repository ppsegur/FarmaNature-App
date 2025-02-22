package com.salesianostriana.dam.farma_app.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        String username,
        String password) {
}
