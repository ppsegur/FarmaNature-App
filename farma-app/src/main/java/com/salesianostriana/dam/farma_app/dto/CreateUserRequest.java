package com.salesianostriana.dam.farma_app.dto;

public record CreateUserRequest(
        String username, String password, String verifyPassword
) {
}
