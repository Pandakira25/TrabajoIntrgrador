package com.dam.model.pojos;

public class Producto {

	private int productoId;
	private String nombre;
	private String categoria;
	private double precio;
	private String descripcion;
	private int stock;
	private boolean activo;

	public Producto(int id, String nombre, String categoria, double precio, String descripcion, int stock,boolean activo) {
		this.productoId = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.descripcion = descripcion;
		this.stock = stock;
		this.activo = activo;
	}
	public Producto(String nombre, String categoria, double precio, int stock, String descripcion) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return "Producto [id=" + productoId + ", nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio + ", descripcion=" + descripcion + ", stock=" + stock + "]";
	}
	public int getId() {
		return productoId;
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
	
	public boolean isActivo() {
		return activo;
	}
}
