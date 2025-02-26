package com.salesianostriana.dam.farma_app.dto.user;

import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import lombok.Builder;

@Builder
public record FarmaceuticoCitaDto(
        String username
) {
    public static  FarmaceuticoCitaDto of(Usuario u){
        return new FarmaceuticoCitaDto(u.getUsername());

    }
}
