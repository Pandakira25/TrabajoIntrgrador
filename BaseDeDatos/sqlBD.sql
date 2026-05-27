PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS transacciones;
DROP TABLE IF EXISTS carrito_producto;
DROP TABLE IF EXISTS carrito;
DROP TABLE IF EXISTS comprador;
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario(
	idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,
	autorizacion INTEGER not NULL CHECK (autorizacion in (1, 2, 3)),
	nombre TEXT NOT NULL,
	contrasenia TEXT NOT NULL,
	tel int
);


CREATE TABLE comprador(
	idComprador INTEGER,
	direccion TEXT NOT NULL,
	nTarjeta TEXT NOT NULL,
	CONSTRAINT fk_Co FOREIGN key (idComprador) REFERENCES usuario(idUsuario)
	on DELETE CASCADE on UPDATE CASCADE,
	CONSTRAINT pk_idComp PRIMARY KEY (idComprador)
);


CREATE TABLE carrito(
	idCarrito INTEGER PRIMARY KEY AUTOINCREMENT,
	idComprador INTEGER,
	CONSTRAINT fk_idCar FOREIGN KEY(idComprador) REFERENCES comprador(idComprador)
	on DELETE CASCADE on UPDATE CASCADE
);


CREATE TABLE producto(
	idProducto INTEGER PRIMARY KEY AUTOINCREMENT,
	nombre TEXT NOT NULL,
	categoria TEXT NOT NULL,
	precio REAL NOT NULL,
	descripcion TEXT
);


CREATE TABLE carrito_producto(
	idCarrito INTEGER,
	idProducto INTEGER,
	cantidadP INTEGER,
	CONSTRAINT pk_idCaPr PRIMARY KEY (idCarrito, idProducto),
	CONSTRAINT fk_idCa FOREIGN KEY (idCarrito) REFERENCES carrito(idCarrito)
	on DELETE CASCADE on UPDATE CASCADE,
	CONSTRAINT fk_idPr FOREIGN KEY (idProducto) REFERENCES producto(idProducto)
	on DELETE CASCADE on UPDATE CASCADE
);


CREATE TABLE empleado(
	idEmpleado INTEGER,
	nSegSocial TEXT,
	iban TEXT,
	CONSTRAINT fk_Em FOREIGN KEY (idEmpleado) REFERENCES usuario(idUsuario)
	on DELETE CASCADE on UPDATE CASCADE,
	CONSTRAINT pk_Em PRIMARY KEY (idEmpleado)
);


CREATE TABLE transacciones(
	idComprador INTEGER,
	idCarrito INTEGER,
	idEmpleado INTEGER,
	CONSTRAINT fk_Co FOREIGN KEY (idComprador) REFERENCES comprador(idComprador)
	on DELETE CASCADE on UPDATE CASCADE,
	CONSTRAINT fk_Ca FOREIGN KEY (idCarrito) REFERENCES carrito(idCarrito)
	on DELETE CASCADE on UPDATE CASCADE,
	CONSTRAINT fk_Em FOREIGN KEY (idEmpleado) REFERENCES empleado(idEmpleado)
	on DELETE SET NULL on UPDATE CASCADE,
	CONSTRAINT pk_CoCaEm PRIMARY KEY (idComprador, idCarrito)
);


