drop database if exists tiendaFnac;
create database tiendaFnac;
use tiendaFnac;

create table Users(
user_id int not null unique
);