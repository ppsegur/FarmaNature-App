package com.salesianostriana.dam.farma_app.dto.user;

import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ClienteCitaDto(
        String username
) {

    public static ClienteCitaDto of(Usuario usuario) {
        return new ClienteCitaDto(usuario.getUsername());
    }

}
