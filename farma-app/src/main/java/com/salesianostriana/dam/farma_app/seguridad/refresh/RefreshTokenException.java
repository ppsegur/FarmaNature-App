package com.salesianostriana.dam.farma_app.seguridad.refresh;

import io.jsonwebtoken.JwtException;

public class RefreshTokenException extends JwtException {
    public RefreshTokenException(String s) {
        super(s);
    }
}
