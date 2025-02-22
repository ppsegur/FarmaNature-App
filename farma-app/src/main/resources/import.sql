-- Inserta usuarios con contraseñas encriptadas
INSERT INTO usuario_entity (id, username, password, email, nombre, apellidos, verificado) VALUES
                                                                                                         ('550e8400-e29b-41d4-a716-446655440000', 'admin', '{noop}admin', 'admin@correo.com', 'Admin', 'User', true),
                                                                                                         ('650e8400-e29b-41d4-a716-446655440001', 'usuario', 'user', 'user@correo.com', 'Usuario', 'Normal', true),
                                                                                                         ('650e8400-e29b-41d4-a716-446655440002', 'farmaceutico', '$2a$10$XcJrUOyjKsK.BSNYl5oGkO6ftI7FhI3yoBkHkkR6W5dbDqDOxJ1su', 'user@correo.com', 'Usuario', 'Normal', true);


-- Insertar roles (si tienes una tabla para roles y una tabla intermedia usuario_roles)
INSERT INTO usuario_roles (usuario_id, roles) VALUES
                                                  ('550e8400-e29b-41d4-a716-446655440000', 'ADMIN'),
                                                  ('650e8400-e29b-41d4-a716-446655440001', 'CLIENTE'),
                                                  ('650e8400-e29b-41d4-a716-446655440002', 'FARMACEUTICO');

