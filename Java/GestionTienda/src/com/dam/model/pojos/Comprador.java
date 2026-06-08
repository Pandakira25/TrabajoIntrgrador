package com.dam.model.pojos;

public class Comprador extends Usuario {

	private String direccion;
	private String nTarjeta;
	private Carrito carrito;

	public Comprador(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo,
			String direccion, String nTarjeta) {
		super(id, autorizacion, nombre, contrasenia, tel, activo);
		this.direccion = validarDireccion(direccion);
		this.nTarjeta = validarTarjeta(nTarjeta);
		carrito = new Carrito();
	}

	public Comprador(String nombre, String contrasenia, int tel, String direccion, String nTarjeta) {
		this(0, 3, nombre, contrasenia, tel, true, direccion, nTarjeta);
	}

	public Comprador(String nombre, String contrasenia, String telStr, String direccion, String nTarjeta) {
		this(nombre, contrasenia, parseTelefono(telStr), direccion, nTarjeta);
	}

	public Comprador(int id, String nombre, String contrasenia, String telStr, String direccion, String nTarjeta) {
		this(id, 3, nombre, contrasenia, parseTelefono(telStr), true, direccion, nTarjeta);
	}

	private static String validarDireccion(String direccion) {
		if (direccion == null || direccion.isBlank()) {
			throw new IllegalArgumentException("La dirección es obligatoria.");
		}
		return direccion.trim();
	}

	private static String validarTarjeta(String nTarjeta) {
		if (nTarjeta == null || nTarjeta.isBlank()) {
			throw new IllegalArgumentException("Debe ingresar una tarjeta.");
		}
		String tarjeta = nTarjeta.trim();
		if (!tarjeta.matches("\\d{13,19}")) {
			throw new IllegalArgumentException("La tarjeta debe tener entre 13 y 19 dígitos numéricos.");
		}
		return tarjeta;
	}

	@Override
	public String toString() {
		return "Comprador [direccion=" + direccion + ", nTarjeta=" + nTarjeta + ", toString()=" + super.toString()
				+ "]";
	}

	public Carrito getCarrito() {
		return carrito;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getnTarjeta() {
		return nTarjeta;
	}
}
