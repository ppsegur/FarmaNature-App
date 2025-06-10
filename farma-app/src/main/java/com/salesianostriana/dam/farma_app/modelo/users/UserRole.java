package com.salesianostriana.dam.farma_app.modelo.users;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRole {
      ADMIN,CLIENTE, FARMACEUTICO
}
