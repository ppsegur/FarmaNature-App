package com.salesianostriana.dam.farma_app.seguridad.TwoFA;

public record Verify2FARequest(String email, int code) {
}
