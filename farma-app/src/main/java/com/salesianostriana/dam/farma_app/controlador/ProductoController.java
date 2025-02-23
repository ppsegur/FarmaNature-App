package com.salesianostriana.dam.farma_app.controlador;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "producto", description = "El controlador para los distintas productos  ")
public class ProductoController {
}
