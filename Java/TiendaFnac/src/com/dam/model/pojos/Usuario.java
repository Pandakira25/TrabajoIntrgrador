package com.dam.model.pojos;

public class Usuario {
	
	protected int id;
	protected int autorizacion;
	protected String nombre;
	protected String contrasenia;
	protected int tel;

	public Usuario(int id, int autorizacion, String nombre, String contrasenia, int tel) {
		this.id = id;
		this.autorizacion = autorizacion;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.tel = tel;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", autorizacion=" + autorizacion + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", tel=" + tel + "]";
	}
}	
