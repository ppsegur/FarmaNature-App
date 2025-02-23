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


-- Insertar categorías
INSERT INTO categoria (id, nombre) VALUES
                                       ('550e8400-e29b-41d4-a716-446655440000', 'Medicamentos'),
                                       ('550e8400-e29b-41d4-a716-446655440001', 'Cuidado Personal');

-- Insertar productos
INSERT INTO producto (id, nombre, descripcion, precio, stock, fecha_publicacion, imagen, oferta, categoria_id) VALUES
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440002', 'Paracetamol', 'Analgésico y antipirético', 5.99, 100, '2023-10-01', 'paracetamol.jpg', false, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440003', 'Jabón de Glicerina', 'Jabón suave para pieles sensibles', 3.49, 50, '2023-10-01', 'jabon_glicerina.jpg', true, '550e8400-e29b-41d4-a716-446655440001');