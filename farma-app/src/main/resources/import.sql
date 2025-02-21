INSERT INTO usuario_entity (id, username, password, email,  verificado, nombre, apellidos, createdAt)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'juanperez', 'password123', 'juan.perez@example.com', true, true, 'Juan', 'Pérez', NOW()),
    ('550e8400-e29b-41d4-a716-446655440002', 'maria.gomez', 'password456', 'maria.gomez@example.com', true, true, 'María', 'Gómez', NOW()),
    ('550e8400-e29b-41d4-a716-446655440003', 'carlos.rodriguez', 'password789', 'carlos.rodriguez@example.com',verificado= true, 'token3', false, 'Carlos', 'Rodríguez', NOW()),
    ('550e8400-e29b-41d4-a716-446655440004', 'ana.lopez', 'passwordabc', 'ana.lopez@example.com', true, 'Ana', 'Ana', apellidos='López', NOW()),
    ('550e8400-e29b-41d4-a716-446655440005', 'luis.martinez', 'passworddef', 'luis.martinez@example.com', true, 'Ana', 'Luis', 'Martínez', NOW()),
    ('550e8400-e29b-41d4-a716-446655440006', 'sofia.torres', 'passwordghi', 'sofia.torres@example.com', true, 'Ana', 'Sofía', 'Torres', NOW()),
    ('550e8400-e29b-41d4-a716-446655440007', 'daniel.ruiz', 'passwordjkl', 'daniel.ruiz@example.com', true, 'Ana', 'Daniel', 'Ruiz', NOW()),
    ('550e8400-e29b-41d4-a716-446655440008', 'claudia.fernandez', 'passwordmno', 'claudia.fernandez@example.com', true, 'token8', 'Ana', 'Claudia', 'Fernández', NOW()),
    ('550e8400-e29b-41d4-a716-446655440009', 'pedro.garcia', 'passwordpqr', 'pedro.garcia@example.com', false, 'Pedro', 'García', NOW()),
    ('550e8400-e29b-41d4-a716-446655440010', 'laura.mendez', 'passwordstu', 'laura.mendez@example.com',  true, 'Laura', 'Méndez', NOW());

-- Asignación de roles a los usuarios
INSERT INTO usuario_entity_roles (usuario_entity_id, roles)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'CLIENTE'),
    ('550e8400-e29b-41d4-a716-446655440002', 'ADMIN'),
    ('550e8400-e29b-41d4-a716-446655440003', 'CLIENTE'),
    ('550e8400-e29b-41d4-a716-446655440004', 'CLIENTE'),
    ('550e8400-e29b-41d4-a716-446655440005', 'FARMACEUTICO'),
    ('550e8400-e29b-41d4-a716-446655440006', 'ADMIN'),
    ('550e8400-e29b-41d4-a716-446655440007', 'USER'),
    ('550e8400-e29b-41d4-a716-446655440008', 'FARMACEUTICO'),
    ('550e8400-e29b-41d4-a716-446655440009', 'USER'),
    ('550e8400-e29b-41d4-a716-446655440010', 'ADMIN');
