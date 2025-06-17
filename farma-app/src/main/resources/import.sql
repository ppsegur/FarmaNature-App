-- Inserta usuarios con contraseñas encriptadas
INSERT INTO usuario_entity (id, username, password, email, nombre, apellidos, verificado) VALUES
                                                                                              -- Administrador
                                                                                              ('550e8400-e29b-41d4-a716-446655440000', 'admin', '{noop}admin', 'admin@correo.com', 'Admin', 'User', true),
                                                                                              -- Clientes
                                                                                              ('650e8400-e29b-41d4-a716-446655440001', 'cliente1', '{noop}cliente1', 'cliente1@correo.com', 'Cliente', 'Uno', true),
                                                                                              ('650e8400-e29b-41d4-a716-446655440002', 'cliente2', '{noop}cliente2', 'cliente2@correo.com', 'Cliente', 'Dos', true),
                                                                                              ('650e8400-e29b-41d4-a716-446655440003', 'cliente3', '{noop}cliente3', 'cliente3@correo.com', 'Cliente', 'Tres', true),
                                                                                              -- Farmacéuticos
                                                                                              ('750e8400-e29b-41d4-a716-446655440004', 'farmaceutico1', '{noop}farmaceutico1', 'farmaceutico1@correo.com', 'Farmacéutico', 'Uno', true),
                                                                                              ('750e8400-e29b-41d4-a716-446655440005', 'farmaceutico2', '{noop}farmaceutico2', 'farmaceutico2@correo.com', 'Farmacéutico', 'Dos', true),
                                                                                              ('750e8400-e29b-41d4-a716-446655440006', 'farmaceutico3', '{noop}farmaceutico3', 'farmaceutico3@correo.com', 'Farmacéutico', 'Tres', true);

-- Inserta roles
INSERT INTO usuario_roles (usuario_id, roles) VALUES
                                                  -- Administrador
                                                  ('550e8400-e29b-41d4-a716-446655440000', 0),  -- ADMIN
                                                  -- Clientes
                                                  ('650e8400-e29b-41d4-a716-446655440001', 1),  -- CLIENTE
                                                  ('650e8400-e29b-41d4-a716-446655440002', 1),  -- CLIENTE
                                                  ('650e8400-e29b-41d4-a716-446655440003', 1),  -- CLIENTE
                                                  -- Farmacéuticos
                                                  ('750e8400-e29b-41d4-a716-446655440004', 2),  -- FARMACEUTICO
                                                  ('750e8400-e29b-41d4-a716-446655440005', 2),  -- FARMACEUTICO
                                                  ('750e8400-e29b-41d4-a716-446655440006', 2);  -- FARMACEUTICO

-- Inserta administradores
INSERT INTO admin (id) VALUES
    ('550e8400-e29b-41d4-a716-446655440000');

-- Inserta clientes
INSERT INTO cliente (id, edad, direccion, telefono) VALUES
                                                        ('650e8400-e29b-41d4-a716-446655440001', 25, 'Calle Falsa 123', '600111222'),
                                                        ('650e8400-e29b-41d4-a716-446655440002', 30, 'Avenida Siempre Viva 456', '600333444'),
                                                        ('650e8400-e29b-41d4-a716-446655440003', 22, 'Calle del Olvido 789', '600555666');

-- Inserta farmacéuticos
INSERT INTO farmaceutico (id, direccion) VALUES
                                             ('750e8400-e29b-41d4-a716-446655440004', 'Calle Farmacia 1'),
                                             ('750e8400-e29b-41d4-a716-446655440005', 'Calle Farmacia 2'),
                                             ('750e8400-e29b-41d4-a716-446655440006', 'Calle Farmacia 3');

-- Asocia farmacéuticos con turnos (0: Mañana, 1: Tarde, 2: Noche)
INSERT INTO farmaceutico_turno (usuario_id, turno) VALUES
                                                       ('750e8400-e29b-41d4-a716-446655440004', 0),  -- Farmacéutico 1: Mañana
                                                       ('750e8400-e29b-41d4-a716-446655440005', 1),  -- Farmacéutico 2: Tarde
                                                       ('750e8400-e29b-41d4-a716-446655440006', 2);  -- Farmacéutico 3: Noche

-- Inserta categorías
INSERT INTO categoria (id, nombre) VALUES
                                       ('550e8400-e29b-41d4-a716-446655440000', 'Medicamentos'),
                                       ('550e8400-e29b-41d4-a716-446655440001', 'Cuidado Personal'),
                                       ('550e8400-e29b-41d4-a716-446655440002', 'Suplementos'),
                                       ('550e8400-e29b-41d4-a716-446655440003', 'Higiene'),
                                       ('550e8400-e29b-41d4-a716-446655440004', 'Belleza');

-- Inserta productos
INSERT INTO producto (id, nombre, descripcion, precio, stock, fecha_publicacion, imagen, oferta, categoria_id) VALUES
                                                                                                                   -- Medicamentos
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440005', 'Paracetamol', 'Analgésico y antipirético', 5.99, 100, '2023-10-01', 'paracetamol.jpg', false, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440006', 'Ibuprofeno', 'Antiinflamatorio no esteroideo', 6.99, 80, '2023-10-02', 'ibuprofeno.jpg', false, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440007', 'Amoxicilina', 'Antibiótico de amplio espectro', 12.99, 50, '2023-10-03', 'amoxicilina.jpg', true, '550e8400-e29b-41d4-a716-446655440000'),
                                                                                                                   -- Cuidado Personal
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440008', 'Jabón de Glicerina', 'Jabón suave para pieles sensibles', 3.49, 50, '2023-10-01', 'jabon_glicerina.jpg', true, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440009', 'Crema Hidratante', 'Crema nutritiva para piel seca', 12.99, 30, '2023-10-03', 'crema_hidratante.jpg', true, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440010', 'Shampoo Anticaspa', 'Shampoo con fórmula anticaspa avanzada', 9.49, 40, '2023-10-05', 'shampoo_anticaspa.jpg', true, '550e8400-e29b-41d4-a716-446655440001'),
                                                                                                                   -- Suplementos
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440011', 'Vitamina C', 'Suplemento de vitamina C 1000 mg', 8.99, 60, '2023-10-04', 'vitamina_c.jpg', false, '550e8400-e29b-41d4-a716-446655440002'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440012', 'Colágeno en Polvo', 'Suplemento de colágeno hidrolizado', 19.99, 20, '2023-10-09', 'colageno_polvo.jpg', true, '550e8400-e29b-41d4-a716-446655440002'),
                                                                                                                   -- Higiene
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440013', 'Gel Desinfectante', 'Gel hidroalcohólico para manos', 4.99, 90, '2023-10-08', 'gel_desinfectante.jpg', false, '550e8400-e29b-41d4-a716-446655440003'),
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440014', 'Cepillo Dental', 'Cepillo dental de cerdas suaves', 2.99, 100, '2023-10-10', 'cepillo_dental.jpg', false, '550e8400-e29b-41d4-a716-446655440003'),
                                                                                                                   -- Belleza
                                                                                                                   ('550e8400-e29b-41d4-a716-446655440015', 'Mascarilla Facial', 'Mascarilla de arcilla purificante', 7.99, 35, '2023-10-07', 'mascarilla_facial.jpg', true, '550e8400-e29b-41d4-a716-446655440004'),




                                                                                                                ('550e8400-e29b-41d4-a716-446655440016', 'Aceite Esencial de Lavanda', 'Aceite relajante para aromaterapia', 14.99, 25, '2023-10-06', 'aceite_lavanda.jpg', false, '550e8400-e29b-41d4-a716-446655440004');


-- Formato: (id_cliente, id_farmaceutico, fechaInicio, fecha_fin, precioCita, especial, titulo)

INSERT INTO cita (id_cliente, id_farmaceutico, fecha_inicio, fecha_fin, precio_cita, especial, titulo) VALUES
-- Cliente 1 y Farmacéutico 1
('650e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440004', '2025-05-14 10:00:00', '2025-05-14 10:30:00', 25.00, false, 'Consulta general'),
-- Cliente 2 y Farmacéutico 2
('650e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440005', '2025-05-15 16:00:00', '2025-05-15 16:45:00', 35.00, true, 'Revisión de tratamiento'),
-- Cliente 3 y Farmacéutico 3
('650e8400-e29b-41d4-a716-446655440003', '750e8400-e29b-41d4-a716-446655440006', '2025-05-16 20:00:00', '2025-05-16 20:30:00', 30.00, false, 'Asesoramiento nutricional'),
-- Cliente 1 y Farmacéutico 2
('650e8400-e29b-41d4-a716-446655440001', '750e8400-e29b-41d4-a716-446655440005', '2025-05-17 11:00:00', '2025-05-17 11:45:00', 28.50, true, 'Consulta dermatológica'),
-- Cliente 2 y Farmacéutico 3
('650e8400-e29b-41d4-a716-446655440002', '750e8400-e29b-41d4-a716-446655440006', '2025-05-18 19:30:00', '2025-05-18 20:00:00', 22.00, false, 'Control de tensión arterial');



-- Inserta comentarios de ejemplo
INSERT INTO comentario (cliente_id, producto_id, comentarios) VALUES
('650e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440005', 'Muy buen producto, lo recomiendo.'),
('650e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440006', 'No me funcionó como esperaba.'),
('650e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440007', 'Entrega rápida y producto en buen estado.');







INSERT INTO categoria (id, nombre) VALUES
('550e8400-e29b-41d4-a716-446655440100', 'Ortopedia'),
('550e8400-e29b-41d4-a716-446655440101', 'Infantil'),
('550e8400-e29b-41d4-a716-446655440102', 'Dietética'),
('550e8400-e29b-41d4-a716-446655440103', 'Herbolario'),
('550e8400-e29b-41d4-a716-446655440104', 'Veterinaria');


INSERT INTO producto (id, nombre, descripcion, precio, stock, fecha_publicacion, imagen, oferta, categoria_id) VALUES
('550e8400-e29b-41d4-a716-446655440100', 'Muñequera', 'Muñequera ortopédica ajustable', 15.99, 20, '2023-10-11', 'munequera.jpg', false, '550e8400-e29b-41d4-a716-446655440100'),
('550e8400-e29b-41d4-a716-446655440101', 'Pañales Bebé', 'Pañales talla 3', 19.99, 50, '2023-10-12', 'panales.jpg', false, '550e8400-e29b-41d4-a716-446655440101'),
('550e8400-e29b-41d4-a716-446655440102', 'Barrita Energética', 'Barrita de cereales y miel', 1.99, 100, '2023-10-13', 'barrita.jpg', true, '550e8400-e29b-41d4-a716-446655440102'),
('550e8400-e29b-41d4-a716-446655440103', 'Té Verde', 'Infusión natural de té verde', 3.99, 60, '2023-10-14', 'te_verde.jpg', false, '550e8400-e29b-41d4-a716-446655440103'),
('550e8400-e29b-41d4-a716-446655440104', 'Antiparasitario Gato', 'Pipeta antiparasitaria', 8.99, 30, '2023-10-15', 'antiparasitario.jpg', true, '550e8400-e29b-41d4-a716-446655440104');


INSERT INTO usuario_entity (id, username, password, email, nombre, apellidos, verificado) VALUES
('650e8400-e29b-41d4-a716-446655440004', 'cliente4', '{noop}cliente4', 'cliente4@correo.com', 'Cliente', 'Cuatro', true),
('650e8400-e29b-41d4-a716-446655440005', 'cliente5', '{noop}cliente5', 'cliente5@correo.com', 'Cliente', 'Cinco', true),
('650e8400-e29b-41d4-a716-446655440006', 'cliente6', '{noop}cliente6', 'cliente6@correo.com', 'Cliente', 'Seis', true),
('650e8400-e29b-41d4-a716-446655440007', 'cliente7', '{noop}cliente7', 'cliente7@correo.com', 'Cliente', 'Siete', true),
('650e8400-e29b-41d4-a716-446655440008', 'cliente8', '{noop}cliente8', 'cliente8@correo.com', 'Cliente', 'Ocho', true);

INSERT INTO usuario_roles (usuario_id, roles) VALUES
('650e8400-e29b-41d4-a716-446655440004', 1),
('650e8400-e29b-41d4-a716-446655440005', 1),
('650e8400-e29b-41d4-a716-446655440006', 1),
('650e8400-e29b-41d4-a716-446655440007', 1),
('650e8400-e29b-41d4-a716-446655440008', 1);

INSERT INTO cliente (id, edad, direccion, telefono) VALUES
('650e8400-e29b-41d4-a716-446655440004', 28, 'Calle Nueva 1', '600777888'),
('650e8400-e29b-41d4-a716-446655440005', 35, 'Avenida Central 2', '600999000'),
('650e8400-e29b-41d4-a716-446655440006', 40, 'Plaza Mayor 3', '600123456'),
('650e8400-e29b-41d4-a716-446655440007', 19, 'Calle Sol 4', '600654321'),
('650e8400-e29b-41d4-a716-446655440008', 31, 'Calle Luna 5', '600321654');


INSERT INTO usuario_entity (id, username, password, email, nombre, apellidos, verificado) VALUES
('750e8400-e29b-41d4-a716-446655440007', 'farmaceutico4', '{noop}farmaceutico4', 'farmaceutico4@correo.com', 'Farmacéutico', 'Cuatro', true),
('750e8400-e29b-41d4-a716-446655440008', 'farmaceutico5', '{noop}farmaceutico5', 'farmaceutico5@correo.com', 'Farmacéutico', 'Cinco', true),
('750e8400-e29b-41d4-a716-446655440009', 'farmaceutico6', '{noop}farmaceutico6', 'farmaceutico6@correo.com', 'Farmacéutico', 'Seis', true),
('750e8400-e29b-41d4-a716-446655440010', 'farmaceutico7', '{noop}farmaceutico7', 'farmaceutico7@correo.com', 'Farmacéutico', 'Siete', true),
('750e8400-e29b-41d4-a716-446655440011', 'farmaceutico8', '{noop}farmaceutico8', 'farmaceutico8@correo.com', 'Farmacéutico', 'Ocho', true);

INSERT INTO usuario_roles (usuario_id, roles) VALUES
('750e8400-e29b-41d4-a716-446655440007', 2),
('750e8400-e29b-41d4-a716-446655440008', 2),
('750e8400-e29b-41d4-a716-446655440009', 2),
('750e8400-e29b-41d4-a716-446655440010', 2),
('750e8400-e29b-41d4-a716-446655440011', 2);

INSERT INTO farmaceutico (id, direccion) VALUES
('750e8400-e29b-41d4-a716-446655440007', 'Calle Farmacia 4'),
('750e8400-e29b-41d4-a716-446655440008', 'Calle Farmacia 5'),
('750e8400-e29b-41d4-a716-446655440009', 'Calle Farmacia 6'),
('750e8400-e29b-41d4-a716-446655440010', 'Calle Farmacia 7'),
('750e8400-e29b-41d4-a716-446655440011', 'Calle Farmacia 8');

INSERT INTO farmaceutico_turno (usuario_id, turno) VALUES
('750e8400-e29b-41d4-a716-446655440007', 0),
('750e8400-e29b-41d4-a716-446655440008', 1),
('750e8400-e29b-41d4-a716-446655440009', 2),
('750e8400-e29b-41d4-a716-446655440010', 0),
('750e8400-e29b-41d4-a716-446655440011', 1);

-- Inserta 5 ventas de ejemplo
INSERT INTO venta (id, fecha_creacion, estado, cliente_id, importe_total) VALUES
('850e8400-e29b-41d4-a716-446655440001', '2025-06-01 10:00:00', true, '650e8400-e29b-41d4-a716-446655440001', 25.98),
('850e8400-e29b-41d4-a716-446655440002', '2025-06-02 11:30:00', true, '650e8400-e29b-41d4-a716-446655440002', 12.99),
('850e8400-e29b-41d4-a716-446655440003', '2025-06-03 12:45:00', true, '650e8400-e29b-41d4-a716-446655440003', 19.99),
('850e8400-e29b-41d4-a716-446655440004', '2025-06-04 13:15:00', true, '650e8400-e29b-41d4-a716-446655440004', 8.99),
('850e8400-e29b-41d4-a716-446655440005', '2025-06-05 14:20:00', true, '650e8400-e29b-41d4-a716-446655440005', 15.99);

-- Inserta 5 comentarios adicionales
INSERT INTO comentario (cliente_id, producto_id, comentarios) VALUES
('650e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440100', 'Muy útil para el dolor de muñeca.'),
('650e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440101', 'Los pañales son de buena calidad.'),
('650e8400-e29b-41d4-a716-446655440006', '550e8400-e29b-41d4-a716-446655440102', 'Barritas muy ricas y energéticas.'),
('650e8400-e29b-41d4-a716-446655440007', '550e8400-e29b-41d4-a716-446655440103', 'El té verde tiene buen sabor.'),
('650e8400-e29b-41d4-a716-446655440008', '550e8400-e29b-41d4-a716-446655440104', 'Mi gato ya no tiene pulgas.');

-- Inserta 5 citas adicionales
INSERT INTO cita (id_cliente, id_farmaceutico, fecha_inicio, fecha_fin, precio_cita, especial, titulo) VALUES
('650e8400-e29b-41d4-a716-446655440004', '750e8400-e29b-41d4-a716-446655440007', '2025-06-20 09:00:00', '2025-06-20 09:30:00', 20.00, false, 'Consulta ortopédica'),
('650e8400-e29b-41d4-a716-446655440005', '750e8400-e29b-41d4-a716-446655440008', '2025-06-21 10:00:00', '2025-06-21 10:30:00', 22.00, true, 'Revisión infantil'),
('650e8400-e29b-41d4-a716-446655440006', '750e8400-e29b-41d4-a716-446655440009', '2025-06-22 11:00:00', '2025-06-22 11:30:00', 18.00, false, 'Consejo dietético'),
('650e8400-e29b-41d4-a716-446655440007', '750e8400-e29b-41d4-a716-446655440010', '2025-06-23 12:00:00', '2025-06-23 12:30:00', 25.00, true, 'Consulta herbolario'),
('650e8400-e29b-41d4-a716-446655440008', '750e8400-e29b-41d4-a716-446655440011', '2025-06-24 13:00:00', '2025-06-24 13:30:00', 30.00, false, 'Consulta veterinaria');
-- Inserta lineas de venta para las ventas de ejemplo
INSERT INTO linea_de_venta (id, venta_id, producto_id, cantidad, precio_venta) VALUES
(1, '850e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440005', 2, 12.99), -- Paracetamol x2
(2, '850e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440007', 1, 12.99), -- Amoxicilina x1
(3, '850e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440012', 1, 19.99), -- Colágeno en Polvo x1
(4, '850e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440013', 1, 8.99), -- Gel Desinfectante x1
(5, '850e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440100', 1, 15.99); -- Muñequera x1
