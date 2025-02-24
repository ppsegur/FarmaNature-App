package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.dto.user.UserResponse;

public record GetComentarioDto(
        String comentario,
        GetProductoDto producto,
        UserResponse cliente

) {
    public  static GetComentarioDto of(GetComentarioDto dto) {
        return new GetComentarioDto(dto.comentario(), dto.producto(), dto.cliente());
    }
}
