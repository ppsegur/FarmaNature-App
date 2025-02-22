package com.salesianostriana.dam.farma_app.dto.user;

import jakarta.validation.constraints.*;

public record EditClienteDto(
        @NotBlank(message = "{createUserRequest.email.notblank}")
        @Email(message = "{createUserRequest.email.invalid}")
        @Size(max = 255, message = "{createUserRequest.email.size}")
        String email,

        @NotBlank(message = "La dirección no puede estar vacía")
        @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
        String direccion,

        @NotBlank(message = "El teléfono no puede estar vacío")
        @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
        String telefono,

        @Min(value = 0, message = "La edad no puede ser negativa")
        int edad
) {}
