package com.salesianostriana.dam.farma_app.controlador;


import com.salesianostriana.dam.farma_app.dto.user.EditClienteDto;
import com.salesianostriana.dam.farma_app.dto.user.EditUserDto;
import com.salesianostriana.dam.farma_app.modelo.Usuario;
import com.salesianostriana.dam.farma_app.servicio.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "CLiente", description = "El controlador para los dsitintos roles  ")
public class RoleController {

    private final ClienteService service;

    //EndPoint al estilo de editar perfil para qe le cliente añadde datos más
    // personales de contacto o para después agregar algun tipo de atributos
    //Ppra las compras commo es una tarjeta o etc
    @Operation(summary = "Edita un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cliente actualizado con éxito",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EditClienteDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                              {
                                                 "email" : "ppsegur@gmail.com",
                                                 "direccion":"calle c. de bustillos",
                                                 "telefono": "+34 606 79 83",
                                                 "edad":43                                              \s
                                              }
                                            }
                                          \s"""
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado clientes")

    })
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/cliente/{username}")
    public Usuario edit(@RequestBody EditClienteDto editDto,
                        @PathVariable String username) {
        return service.editCliente(editDto, username );
    }
}
