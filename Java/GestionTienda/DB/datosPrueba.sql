-- zoe hice esto para que se puedan hacer pruebas de login
INSERT INTO usuario (autorizacion, nombre, contrasenia, activo, tel) VALUES
(1, 'admin', 'admin', 1, 600111111),
(2, 'empleado', 'empleado', 1, 600222222),
(3, 'comprador', 'comprador', 1, 600333333);


INSERT INTO usuario (autorizacion, nombre, contrasenia, activo, tel) VALUES (1, 'Zoe', '1234', 1, 123456789);
INSERT INTO empleado (empleado_id, n_seg_social, iban) VALUES (last_insert_rowid(), '123456789', 'ES1234567890123456789012');