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
	autorizacion INTEGER not NULL CHECK (autorizacion in (1, 2, 3)),
	nombre TEXT NOT NULL,
	contrasenia TEXT NOT NULL,
	activo INTEGER NOT NULL DEFAULT 1 CHECK (activo IN (0, 1)),
	tel int
);


CREATE TABLE comprador(
	comprador_id INTEGER,
	direccion TEXT NOT NULL,
	n_tarjeta TEXT NOT NULL,
	CONSTRAINT fk_Co FOREIGN key (comprador_id) REFERENCES usuario(usuario_id),
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
	stock INTEGER NOT NULL DEFAULT 0
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
	on DELETE CASCADE on UPDATE CASCADE,
	CONSTRAINT fk_Ca FOREIGN KEY (carrito_id) REFERENCES carrito(carrito_id),
	CONSTRAINT fk_Em FOREIGN KEY (empleado_id) REFERENCES empleado(empleado_id),
	CONSTRAINT pk_CoCaEm PRIMARY KEY (comprador_id, carrito_id)
);


