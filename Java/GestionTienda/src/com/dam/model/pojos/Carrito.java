package com.dam.model.pojos;

public class Carrito {

	private int id;
	private Comprador c;
	private double importe;

	public Carrito(int id, Comprador c, double importe) {
		this.id = id;
		this.c = c;
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "Carrito [id=" + id + ", c=" + c + ", importe=" + importe + "]";
	}
}
