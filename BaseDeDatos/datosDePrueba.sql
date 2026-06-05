-- Empleado (referencia usuario_id = 2)
INSERT INTO empleado (empleado_id, n_seg_social, iban) VALUES
  (2, '28/987654321/02', 'ES8023100001180000012345');

-- Comprador (referencia usuario_id = 3)
INSERT INTO comprador (comprador_id, direccion, n_tarjeta) VALUES
  (3, 'Calle Mayor 10, Madrid', '4111111111111111');

-- Productos
INSERT INTO producto (nombre, categoria, precio, descripcion, stock) VALUES
  ('The Last of Us Part II',  'Videojuego',    49.99,  'PS4/PS5',           30),
  ('Sony WH-1000XM5',         'Audio',         349.99, 'Auriculares ANC',   12),
  ('Kindle Paperwhite',       'E-reader',      139.99, '32 GB, luz cálida', 25),
  ('Logitech MX Master 3S',   'Periférico',    99.99,  'Ratón inalámbrico', 20),
  ('Samsung 970 EVO 1TB',     'Almacenamiento', 89.99, 'NVMe M.2',           0);

-- Carrito (del comprador_id = 3)
INSERT INTO carrito (comprador_id) VALUES (3);  -- carrito_id = 1

-- Líneas del carrito
INSERT INTO carrito_producto (carrito_id, producto_id, cantidad_p) VALUES
  (1, 1, 1),  -- The Last of Us
  (1, 4, 1);  -- MX Master 3S

-- Transacción (comprador 3, carrito 1, procesada por empleado 2)
INSERT INTO transacciones (comprador_id, carrito_id, empleado_id) VALUES
  (3, 1, 2);