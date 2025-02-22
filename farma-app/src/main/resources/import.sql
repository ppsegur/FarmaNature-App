-- Inserta usuarios con contraseñas encriptadas
INSERT INTO usuario_entity (id, username, password, email, nombre, apellidos, verificado) VALUES
                                                                                                         ('550e8400-e29b-41d4-a716-446655440000', 'admin', '{noop}admin', 'admin@correo.com', 'Admin', 'User', true),
                                                                                                         ('650e8400-e29b-41d4-a716-446655440001', 'usuario', '{noop}user', 'user@correo.com', 'Usuario', 'Normal', true),
                                                                                                         ('650e8400-e29b-41d4-a716-446655440002', 'farmaceutico', '{noop}farmaceutico', 'user@correo.com', 'Usuario', 'Normal', true);


-- Insertar roles (si tienes una tabla para roles y una tabla intermedia usuario_roles)
INSERT INTO usuario_roles (usuario_id, roles) VALUES
                                                  ('550e8400-e29b-41d4-a716-446655440000', 0),
                                                  ('650e8400-e29b-41d4-a716-446655440001', 1),
                                                  ('650e8400-e29b-41d4-a716-446655440002', 2);

