package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.dto.user.UserResponse;
import com.salesianostriana.dam.farma_app.modelo.Comentario;
import jakarta.validation.constraints.NotBlank;

public record GetComentarioDto(
        @NotBlank
        String comentario,
        GetProductoDto producto,
        UserResponse cliente

) {
    public  static  GetComentarioDto of(Comentario comentario) {
        return new GetComentarioDto(comentario.getComentarios()
                ,GetProductoDto.of(comentario.getProducto(), comentario.getProducto().getImagen())
                ,UserResponse.of(comentario.getCliente()));
    }
}
