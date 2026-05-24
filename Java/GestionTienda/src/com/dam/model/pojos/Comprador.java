package com.dam.model.pojos;

public class Comprador extends Usuario{

	private String direccion;
	private String nTarjeta;
	
	public Comprador(int id, int autorizacion, String nombre, String contrasenia, int tel, String direccion, String nTarjeta) {
		super(id, autorizacion, nombre, contrasenia, tel);
		this.direccion = direccion;
		this.nTarjeta = nTarjeta;
	}
	@Override
	public String toString() {
		return "Comprador [direccion=" + direccion + ", nTarjeta=" + nTarjeta + ", toString()=" + super.toString() + "]";
	}
}
