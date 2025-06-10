package com.salesianostriana.dam.farma_app.dto.user;

import com.salesianostriana.dam.farma_app.modelo.users.Usuario;

public record UsuarioSimpleDto(String username, 
String nombre,
 String role, 
 String email 
 
 ) { public static UsuarioSimpleDto from(Usuario usuario) {
        String rol = usuario.getRoles().stream().findFirst().map(Enum::name).orElse("");
        return new UsuarioSimpleDto(
            usuario.getUsername(),
            usuario.getNombre(),
            usuario.getEmail(),
            rol
        );
    }}
