package com.dam.model.pojos;

public class Empleado extends Usuario{
	
	private String nSeguridad;
	private String iban;

	public Empleado(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo,
			String nSeguridad, String iban) {
		super(id, autorizacion, nombre, contrasenia, tel, activo);
		this.nSeguridad = nSeguridad;
		this.iban = iban;
	}
	
	public String getnSeguridad() {
		return nSeguridad;
	}

	public String getIban() {
		return iban;
	}

	@Override
	public String toString() {
		return "Empleado [nSeguridad=" + nSeguridad + ", iban=" + iban + ", toString()=" + super.toString() + "]";
	}
}