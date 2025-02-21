package com.salesianostriana.dam.farma_app.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "{createUserRequest.username.not blank}")
        String username,
        @NotBlank(message = "{createUserRequest.password.not blank}")
        String password) {
}
