package com.dam.model.pojos;

public class Producto {

	private int productoId;
	private String nombre;
	private String categoria;
	private double precio;
	private String descripcion;
	private int stock;
	private boolean activo;

	public Producto(int id, String nombre, String categoria, double precio, String descripcion, int stock,
			boolean activo) {
		validarNombre(nombre);
		validarCategoria(categoria);
		validarPrecio(precio);
		validarStock(stock);
		this.productoId = id;
		this.nombre = nombre.trim();
		this.categoria = categoria.trim();
		this.precio = precio;
		this.descripcion = descripcion != null ? descripcion.trim() : "";
		this.stock = stock;
		this.activo = activo;
	}

	public Producto(String nombre, String categoria, double precio, int stock, String descripcion) {
		this(0, nombre, categoria, precio, descripcion, stock, true);
	}

	public Producto(int id, String nombre, String categoria, String precioStr, String stockStr, String descripcion,
			boolean activo) {
		this(id, nombre, categoria, parsePrecio(precioStr), descripcion, parseStock(stockStr), activo);
	}

	public static int parseCantidadMovimiento(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("La cantidad es obligatoria.");
		}
		String valor = texto.trim();
		if (!valor.matches("\\d+")) { // regex que solo deja numeros
			throw new IllegalArgumentException("La cantidad debe contener solo dígitos.");
		}
		int cantidad;
		try {
			cantidad = Integer.parseInt(valor);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("La cantidad es demasiado grande.");
		}
		if (cantidad <= 0) {
			throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
		}
		return cantidad;
	}

	private static void validarNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
	}

	private static void validarCategoria(String categoria) {
		if (categoria == null || categoria.isBlank()) {
			throw new IllegalArgumentException("La categoría es obligatoria.");
		}
	}

	private static void validarPrecio(double precio) {
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo.");
		}
	}

	private static void validarStock(int stock) {
		if (stock < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo.");
		}
	}

	private static double parsePrecio(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("El precio es obligatorio.");
		}
		try {
			double precio = Double.parseDouble(texto.trim().replace(',', '.'));
			validarPrecio(precio);
			return precio;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("El precio debe ser un número decimal válido.");
		}
	}

	private static int parseStock(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("El stock es obligatorio.");
		}
		String valor = texto.trim();
		if (!valor.matches("\\d+")) {
			throw new IllegalArgumentException("El stock debe contener solo dígitos.");
		}
		try {
			int stock = Integer.parseInt(valor);
			validarStock(stock);
			return stock;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("El stock es demasiado grande.");
		}
	}

	@Override
	public String toString() {
		return "Producto [id=" + productoId + ", nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio
				+ ", descripcion=" + descripcion + ", stock=" + stock + "]";
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
