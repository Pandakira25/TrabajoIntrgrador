package com.dam.model.pojos;

import java.util.HashMap;

public class Carrito {

	private int carritoId;
	private HashMap<Producto, Integer> productos;
	
	public Carrito() {
		
	}

	public Carrito(int carritoId, HashMap<Producto, Integer> productos) {
		super();
		this.carritoId = carritoId;
		this.productos = productos;
	}
	
	public void addP(Producto p, int cantidad) {
		productos.put(p, cantidad);
	}
	
	public void dellP(Producto p) {
		productos.remove(p);
	}
	
	private void restCant(Producto p, int cantR) {
		int cantUpd = productos.get(p) - cantR;
		productos.put(p, cantUpd);
	}
	
	private void addCant(Producto p, int cantAdd) {
		int cantUpd = productos.get(p) - cantAdd;
		productos.put(p, cantUpd);
	}
	
	private void clearCarrtio() {
		productos.clear();
	}
}
