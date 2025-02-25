package com.salesianostriana.dam.farma_app.controlador.users;

import com.salesianostriana.dam.farma_app.dto.user.EditClienteDto;
import com.salesianostriana.dam.farma_app.dto.user.EditFarmaceuticoDto;
import com.salesianostriana.dam.farma_app.modelo.users.Usuario;
import com.salesianostriana.dam.farma_app.servicio.users.FarmaceuticoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FarmaceuticoController {

    private final FarmaceuticoService service;


    @Operation(summary = "Edita un farmaceutico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Perfil actualizado con éxito",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EditClienteDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                              {                               
                                                 "direccionLocal":"calle c. de bustillos",                                        
                                                 "turno":1                                             \s
                                              }
                                            }
                                          \s"""
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado clientes")

    })
    @PreAuthorize("hasRole('FARMACEUTICO')")
    @PutMapping("/farmaceutico/{username}")
    public Usuario edit(@RequestBody @Valid EditFarmaceuticoDto editDto,
                        @AuthenticationPrincipal String username) {
        return service.editFarmaceutico(editDto, username );
    }
}
