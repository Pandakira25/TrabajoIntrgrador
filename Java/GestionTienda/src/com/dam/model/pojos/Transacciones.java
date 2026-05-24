package com.dam.model.pojos;

public class Transacciones {

	private int id;
	private Comprador co;
	private Empleado e;
	private Carrito ca;

	public Transacciones(int id, Comprador co, Empleado e, Carrito ca) {
		this.id = id;
		this.co = co;
		this.e = e;
		this.ca = ca;
	}
	@Override
	public String toString() {
		return "Transacciones [id=" + id + ", co=" + co + ", e=" + e + ", ca=" + ca + "]";
	}
}
