package com.salesianostriana.dam.farma_app.seguridad.TwoFA;

import jakarta.validation.constraints.NotBlank;

public record Verify2FARequest(
        @NotBlank(message = "{createUserRequest.email.not blank}")
        String email,
        @NotBlank(message = "{verify2FARequest.code.not blank}")
        String code) {

}
