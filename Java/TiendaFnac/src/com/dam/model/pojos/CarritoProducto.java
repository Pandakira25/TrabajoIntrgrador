package com.dam.model.pojos;

public class CarritoProducto {
	
	private int id;
	private int idProducto;
	private int cantidadP;
	private Carrito ca;
	private Comprador co;

	public CarritoProducto(int id, int idProducto, int cantidadP, Carrito ca, Comprador co) {
		this.id = id;
		this.idProducto = idProducto;
		this.cantidadP = cantidadP;
		this.ca = ca;
		this.co = co;
	}
	
	@Override
	public String toString() {
		return "CarritoProducto [id=" + id + ", idProducto=" + idProducto + ", cantidadP=" + cantidadP + ", ca=" + ca + ", co=" + co + "]";
	}
}
