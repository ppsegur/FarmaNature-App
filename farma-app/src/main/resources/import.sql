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

INSERT INTO producto (id, nombre, descripcion, precio, stock, fecha_publicacion, imagen, oferta, categoria_id) VALUES
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440002', 'Paracetamol', 'Analgésico y antipirético', 5.99, 100, '2023-10-01', 'paracetamol.jpg', false, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440003', 'Jabón de Glicerina', 'Jabón suave para pieles sensibles', 3.49, 50, '2023-10-01', 'jabon_glicerina.jpg', true, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440004', 'Ibuprofeno', 'Antiinflamatorio no esteroideo', 6.99, 80, '2023-10-02', 'ibuprofeno.jpg', false, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440005', 'Crema Hidratante', 'Crema nutritiva para piel seca', 12.99, 30, '2023-10-03', 'crema_hidratante.jpg', true, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440006', 'Vitamina C', 'Suplemento de vitamina C 1000 mg', 8.99, 60, '2023-10-04', 'vitamina_c.jpg', false, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440007', 'Shampoo Anticaspa', 'Shampoo con fórmula anticaspa avanzada', 9.49, 40, '2023-10-05', 'shampoo_anticaspa.jpg', true, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440008', 'Aceite Esencial de Lavanda', 'Aceite relajante para aromaterapia', 14.99, 25, '2023-10-06', 'aceite_lavanda.jpg', false, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440009', 'Mascarilla Facial', 'Mascarilla de arcilla purificante', 7.99, 35, '2023-10-07', 'mascarilla_facial.jpg', true, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440010', 'Gel Desinfectante', 'Gel hidroalcohólico para manos', 4.99, 90, '2023-10-08', 'gel_desinfectante.jpg', false, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440011', 'Colágeno en Polvo', 'Suplemento de colágeno hidrolizado', 19.99, 20, '2023-10-09', 'colageno_polvo.jpg', true, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440012', 'Cepillo Dental', 'Cepillo dental de cerdas suaves', 2.99, 100, '2023-10-10', 'cepillo_dental.jpg', false, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440013', 'Té Verde', 'Infusión natural con propiedades antioxidantes', 5.49, 45, '2023-10-11', 'te_verde.jpg', true, '550e8400-e29b-41d4-a716-446655440000');