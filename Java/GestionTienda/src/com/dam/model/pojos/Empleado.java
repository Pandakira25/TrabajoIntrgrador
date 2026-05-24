package com.dam.model.pojos;

public class Empleado extends Usuario{
	
	private String nSeguridad;
	private String iban;

	public Empleado(int id, int autorizacion, String nombre, String contrasenia, int tel, String nSeguridad, String iban) {
		super(id, autorizacion, nombre, contrasenia, tel);
		this.nSeguridad = nSeguridad;
		this.iban = iban;
	}
	@Override
	public String toString() {
		return "Empleado [nSeguridad=" + nSeguridad + ", iban=" + iban + ", toString()=" + super.toString() + "]";
	}
}