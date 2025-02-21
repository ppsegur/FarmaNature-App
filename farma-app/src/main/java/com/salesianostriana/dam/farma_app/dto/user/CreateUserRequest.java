package com.salesianostriana.dam.farma_app.dto.user;

import com.salesianostriana.dam.farma_app.validation.FieldsValueMatch;
import com.salesianostriana.dam.farma_app.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Los valores de password y verifyPassword no coinciden"),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "verifyEmail",
                message = "Los valores de email y verifyEmail no coinciden")
})
public record CreateUserRequest(
        @NotBlank(message = "{createUserRequest.username.notblank}")
        @Size(min = 3, max = 50, message = "{createUserRequest.username.size}")
        @UniqueUsername
        String username,

        @NotBlank(message = "{createUserRequest.password.notblank}")
        String password,

        @NotBlank(message = "{createUserRequest.verifyPassword.notblank}")
        String verifyPassword,

        @NotBlank(message = "{createUserRequest.email.notblank}")
        @Email(message = "{createUserRequest.email.invalid}")
        @Size(max = 255, message = "{createUserRequest.email.size}")
        String email,

        @NotBlank(message = "{createUserRequest.email.notblank}")
        @Email(message = "{createUserRequest.email.invalid}")
        @Size(max = 255, message = "{createUserRequest.email.size}")
        String verifyEmail


) {
}
