package com.dam.model.pojos;

public class Usuario {
	
	protected int userId;
	protected int autorizacion;
	protected String nombre;
	protected String contrasenia;
	protected int tel;
	protected boolean activo;

	public Usuario(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo) {
		this.userId = id;
		this.autorizacion = autorizacion;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.tel = tel;
		this.activo = activo;
	}
	
	public Usuario(int autorizacion, String nombre, String contrasenia, int tel, boolean activo) {
		super();
		this.autorizacion = autorizacion;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.tel = tel;
		this.activo = activo;
	}



	@Override
	public String toString() {
		return "Usuario [id=" + userId + ", autorizacion=" + autorizacion + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", tel=" + tel + "]";
	}
	
	 public int getUserId() {
		return userId;
	}
	 
	public int getAutorizacion() {
		return autorizacion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getContrasenia() {
		return contrasenia;
	}
	
	public int getTel() {
		return tel;
	}
	public boolean isActivo() {
		return activo;
	}
	
	public void darDeBaja() {
		activo = false; 
	}
}	
