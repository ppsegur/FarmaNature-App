package com.salesianostriana.dam.farma_app.dto.user;

public record CreateUserRequest(

        String username,
        String password, String verifyPassword, String email
) {
}
