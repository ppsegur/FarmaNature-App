package com.salesianostriana.dam.farma_app.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NotBlank(message = "{createUserRequest.not blank}")
public record CreateUserRequest(
        @NotBlank(message = "{createUserRequest.username.not blank}")
        @Size(min = 3, max = 50, message = "{createUserRequest.username.size}")
        String username,

        @NotBlank(message = "{createUserRequest.password.not blank}")
        String password,

        @NotBlank(message = "{createUserRequest.verifyPassword.not blank}")
        String verifyPassword,

        @NotBlank(message = "{createUserRequest.email.not blank}")
        @Email(message = "{createUserRequest.email.invalid}")
        @Size(max = 255, message = "{createUserRequest.email.size}")
        String email
) {
}
