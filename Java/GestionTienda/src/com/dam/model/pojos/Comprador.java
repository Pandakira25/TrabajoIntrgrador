package com.dam.model.pojos;

public class Comprador extends Usuario{

	private String direccion;
	private String nTarjeta;
	private Carrito carrito;
	
	public Comprador(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo,
			String direccion, String nTarjeta) {
		super(id, autorizacion, nombre, contrasenia, tel, activo);
		this.direccion = direccion;
		this.nTarjeta = nTarjeta;
		carrito = new Carrito();
	}
	
	@Override
	public String toString() {
		return "Comprador [direccion=" + direccion + ", nTarjeta=" + nTarjeta + ", toString()=" + super.toString() + "]";
	}
	
	public Carrito getCarrito() {
		return carrito;
	}
}
