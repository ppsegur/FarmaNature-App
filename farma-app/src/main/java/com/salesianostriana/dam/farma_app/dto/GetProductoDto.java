package com.salesianostriana.dam.farma_app.dto;

import com.salesianostriana.dam.farma_app.modelo.Producto;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.UUID;

public record GetProductoDto(
        UUID id,
        @NotNull(message = "El precio no puede ser nulo")
        String nombre,
        @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
        String descripcion,

        @NotNull(message = "El precio no puede ser nulo")
        @Positive(message = "El precio debe ser un número positivo")
        Double precio,

        @NotNull(message = "El stock no puede ser nulo")
        @PositiveOrZero(message = "El stock debe ser un número positivo o cero")
        Integer stock,

        @PastOrPresent(message = "La fecha de publicación no puede ser futura")
        Date fechaPublicacion,

        Boolean oferta,
        GetCategoriaDto categoria,
        String url
        ) {

    public static GetProductoDto of(Producto p, String url){
        //Cambios para devolver el producto con la foto y la categoría aparentemente  correcto
        GetCategoriaDto categoriaDto = GetCategoriaDto.of(p.getCategoria());
        //Tocamos los ids para usarlo en el carrito
        return new GetProductoDto(p.getId(),p.getNombre(),p.getDescripcion(),p.getPrecio(),p.getStock(),p.getFechaPublicacion() ,p.getOferta(), categoriaDto,url);
    }
}
