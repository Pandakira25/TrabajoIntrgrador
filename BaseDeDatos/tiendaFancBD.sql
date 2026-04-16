drop database if exists tiendaFnac;
create database tiendaFnac;
use tiendaFnac;

create database if not exists appInventario;

drop table if exists usuario;
create table usuario (
ID int not null primary key auto_increment,
nombreU varchar(100),
contrasenia int,
nombre varchar(100),
apellido varchar(200)
);

drop table if exists comprador;
create table comprador(
ID int primary key,
foreign key(ID) references usuario(ID),

direccion varchar(300),
nTarjeta int,
tel int
);

drop table if exists empleado;
create table emleado(
ID int primary key,
foreign key(ID) references usuario(ID),

nSegSocial int(12) not null,
iban varchar(24)
);

drop table if exists carrito;
create table carrito(
id int primary key auto_increment,
compradorID int,
foreign key (compradorID) references comprador(ID)
);

drop table if exists producto;
create table producto(
id int primary key auto_increment,
nombre varchar(200),
categoria varchar(100), 
precio decimal(10,2),
descripcion varchar(500),
imagen varchar(1000)
);

drop table if exists carrito_producto;
create table carrito_producto(
id int primary key auto_increment,
carritoId int,
foreign key (carritoId ) references carrito(id),
compradorId int,
foreign key (compradorId) references comprador(ID),
cantidad int default 1
);

drop table if exists transacciones;
create table transacciones(
id int not null primary key auto_increment,
compradorId int,
foreign key (compradorId) references comprador(ID),
carritoId int,
foreign key (carritoId) references carrito(id)
);