package com.salesianostriana.dam.farma_app.dto.user;

import com.salesianostriana.dam.farma_app.modelo.Usuario;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllUsuariosDto(
        List<UserResponse> listadoDeUsuarios
) {
    public static GetAllUsuariosDto fromDto(List<Usuario> listadoDeUsuariosSinProcesar) {
        return GetAllUsuariosDto.builder()
                .listadoDeUsuarios(listadoDeUsuariosSinProcesar.stream().map(UserResponse::of).toList())
                .build();
    }
}