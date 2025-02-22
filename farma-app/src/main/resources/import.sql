-- Inserta usuarios con contraseñas encriptadas
INSERT INTO usuario_entity (id, username, password, email, nombre, apellidos, verificado) VALUES
                                                                                                         ('550e8400-e29b-41d4-a716-446655440000', 'admin', '{noop}admin', 'admin@correo.com', 'Admin', 'User', true),
                                                                                                         ('650e8400-e29b-41d4-a716-446655440001', 'usuario', '{noop}user', 'user@correo.com', 'Usuario', 'Normal', true),
                                                                                                         ('650e8400-e29b-41d4-a716-446655440002', 'farmaceutico', '{noop}farmaceutico', 'user@correo.com', 'Usuario', 'Normal', true);

INSERT INTO admin (id) VALUES
    ('550e8400-e29b-41d4-a716-446655440000');

INSERT INTO cliente (id, edad , direccion, telefono) VALUES
                                                         ('650e8400-e29b-41d4-a716-446655440001',23,'calle luis montoto','6774847484');

INSERT INTO farmaceutico (id , direccion ) VALUES
    ('650e8400-e29b-41d4-a716-446655440002','calle luis de morales');

INSERT INTO farmaceutico_turno (usuario_id, turno) VALUES
                                         ('650e8400-e29b-41d4-a716-446655440002',0);
-- Insert roles
INSERT INTO usuario_roles (usuario_id, roles) VALUES
                                                  ('550e8400-e29b-41d4-a716-446655440000', 0),  -- ADMIN
                                                  ('650e8400-e29b-41d4-a716-446655440001', 1),  -- Cliente
                                                  ('650e8400-e29b-41d4-a716-446655440002', 2);  -- FARMACEUTICO