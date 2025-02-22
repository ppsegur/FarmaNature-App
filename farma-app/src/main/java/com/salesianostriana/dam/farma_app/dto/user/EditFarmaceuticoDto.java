package com.salesianostriana.dam.farma_app.dto.user;

import com.salesianostriana.dam.farma_app.modelo.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditFarmaceuticoDto(@NotBlank(message = "La dirección del local no puede estar vacía")
                                   @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
                                   String direccion,

                                  @NotNull(message = "El turno es obligatorio")
                                  Turno turno  // Usamos el enum Turno para evitar valores inválidos)
)
                                  {
}
