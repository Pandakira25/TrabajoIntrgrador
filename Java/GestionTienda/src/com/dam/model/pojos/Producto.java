package com.dam.model.pojos;

public class Producto {

	private int id;
	private String nombre;
	private String categoria;
	private double precio;
	private String descripcion;
	private int stock;

	public Producto(int id, String nombre, String categoria, double precio, String descripcion, int stock) {
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.descripcion = descripcion;
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio + ", descripcion=" + descripcion + ", stock=" + stock + "]";
	}
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public double getPrecio() {
		return precio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public int getStock() {
		return stock;
	}
}
