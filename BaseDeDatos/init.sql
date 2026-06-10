PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS transacciones;
DROP TABLE IF EXISTS carrito_producto;
DROP TABLE IF EXISTS carrito;
DROP TABLE IF EXISTS comprador;
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario(
    usuario_id INTEGER PRIMARY KEY AUTOINCREMENT,
    autorizacion INTEGER NOT NULL CHECK (autorizacion IN (1, 2, 3)),
    nombre TEXT NOT NULL,
    contrasenia TEXT NOT NULL,
    activo INTEGER NOT NULL DEFAULT 1 CHECK (activo IN (0, 1)),
    tel INTEGER
);

CREATE TABLE comprador(
    comprador_id INTEGER,
    direccion TEXT NOT NULL,
    n_tarjeta TEXT NOT NULL,
    CONSTRAINT fk_Co FOREIGN KEY (comprador_id) REFERENCES usuario(usuario_id),
    CONSTRAINT pk_idComp PRIMARY KEY (comprador_id)
);

CREATE TABLE carrito(
    carrito_id INTEGER PRIMARY KEY AUTOINCREMENT,
    comprador_id INTEGER,
    CONSTRAINT fk_idCar FOREIGN KEY(comprador_id) REFERENCES comprador(comprador_id)
);

CREATE TABLE producto(
    producto_id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    categoria TEXT NOT NULL,
    precio REAL NOT NULL,
    descripcion TEXT,
    stock INTEGER NOT NULL DEFAULT 0,
    activo INTEGER NOT NULL DEFAULT 1 CHECK (activo IN (0, 1))
);

CREATE TABLE carrito_producto(
    carrito_id INTEGER,
    producto_id INTEGER,
    cantidad_p INTEGER,
    CONSTRAINT pk_idCaPr PRIMARY KEY (carrito_id, producto_id),
    CONSTRAINT fk_idCa FOREIGN KEY (carrito_id) REFERENCES carrito(carrito_id),
    CONSTRAINT fk_idPr FOREIGN KEY (producto_id) REFERENCES producto(producto_id)
);

CREATE TABLE empleado(
    empleado_id INTEGER,
    n_seg_social TEXT,
    iban TEXT,
    CONSTRAINT fk_Em FOREIGN KEY (empleado_id) REFERENCES usuario(usuario_id),
    CONSTRAINT pk_Em PRIMARY KEY (empleado_id)
);

CREATE TABLE transacciones(
    comprador_id INTEGER,
    carrito_id INTEGER,
    empleado_id INTEGER,
    fecha TEXT NOT NULL DEFAULT (date('now','localtime')),
    CONSTRAINT fk_Co FOREIGN KEY (comprador_id) REFERENCES comprador(comprador_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Ca FOREIGN KEY (carrito_id) REFERENCES carrito(carrito_id),
    CONSTRAINT fk_Em FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id),
    CONSTRAINT pk_CoCaEm PRIMARY KEY (comprador_id, carrito_id)
);


INSERT INTO usuario (autorizacion, nombre, contrasenia, activo, tel) VALUES
    (1, 'admin',     'admin',     1, 600111111),
    (1, 'Zoe',       '1234',      1, 123456789),
    (2, 'empleado',  'empleado',  1, 600222222),
    (3, 'comprador', 'comprador', 1, 600333333);


INSERT INTO empleado (empleado_id, n_seg_social, iban) VALUES
    (1, '28/111111111/01', 'ES8023100001180000011111'),
    (2, '123456789',       'ES1234567890123456789012'),
    (3, '28/987654321/02', 'ES8023100001180000012345');


INSERT INTO comprador (comprador_id, direccion, n_tarjeta) VALUES
    (4, 'Calle Mayor 10, Madrid', '4111111111111111');


INSERT INTO producto (nombre, categoria, precio, descripcion, stock, activo) VALUES
    ('The Last of Us Part II', 'Videojuego',     49.99,  'PS4/PS5',            30, 1),
    ('Sony WH-1000XM5',        'Audio',          349.99, 'Auriculares ANC',    12, 1),
    ('Kindle Paperwhite',      'E-reader',       139.99, '32 GB, luz cálida',  25, 1),
    ('Logitech MX Master 3S',  'Periférico',     99.99,  'Ratón inalámbrico',  20, 1),
    ('Samsung 970 EVO 1TB',    'Almacenamiento', 89.99,  'NVMe M.2',            0, 1);


INSERT INTO carrito (comprador_id) VALUES (4);


INSERT INTO carrito_producto (carrito_id, producto_id, cantidad_p) VALUES
    (1, 1, 1),
    (1, 4, 1);


INSERT INTO transacciones (comprador_id, carrito_id, empleado_id) VALUES
    (4, 1, 3);