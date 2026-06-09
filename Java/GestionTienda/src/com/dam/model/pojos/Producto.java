package com.dam.model.pojos;

/**
 * Clase del modelo que representa a un artículo comercializable en el sistema (`Producto`).
 * <p>
 * Define todas las propiedades intrínsecas de un producto de la base de datos, incluyendo 
 * su identificación, categorización comercial, tasación económica, descripción detallada, 
 * control de inventario (stock disponible) y su estado lógico de activación.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class Producto {

	/** Identificador único asignado al registro del producto en la base de datos. */
	private int productoId;
	/** Denominación comercial o nombre del producto. */
	private String nombre;
	/** Clasificación o categoría de mercado a la que pertenece el producto. */
	private String categoria;
	/** Valor económico unitario tasado para la venta del producto. */
	private double precio;
	/** Reseña explicativa o especificaciones técnicas detalladas del artículo. */
	private String descripcion;
	/** Volumen numérico de existencias disponibles en el inventario o almacén. */
	private int stock;
	/** Indicador lógico de disponibilidad; {@code true} si está habilitado para la venta, {@code false} si está retirado. */
	private boolean activo;

	/**
	 * Constructor completo parametrizado para instanciar un producto con datos ya persistidos.
	 * * @param id          Identificador numérico único del producto.
	 * @param nombre      Denominación comercial del artículo.
	 * @param categoria   Clasificación o sector comercial asociado.
	 * @param precio      Valor económico de venta.
	 * @param descripcion Reseña detallada de características.
	 * @param stock       Unidades disponibles en el almacén.
	 * @param activo      Estado de vigencia lógica del artículo.
	 */
	public Producto(int id, String nombre, String categoria, double precio, String descripcion, int stock,boolean activo) {
		this.productoId = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.descripcion = descripcion;
		this.stock = stock;
		this.activo = activo;
	}
	
	/**
	 * Constructor simplificado parametrizado destinado al registro o alta de un nuevo producto.
	 * <p>
	 * Utilizado comúnmente antes de que el sistema asigne un identificador único autogenerado y 
	 * defina su estado de activación por defecto.
	 * </p>
	 * * @param nombre      Denominación comercial del artículo.
	 * @param categoria   Clasificación o sector comercial asociado.
	 * @param precio      Valor económico de venta.
	 * @param stock       Unidades iniciales destinadas a inventario.
	 * @param descripcion Reseña detallada de características.
	 */
	public Producto(String nombre, String categoria, double precio, int stock, String descripcion) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
		this.descripcion = descripcion;
	}

	public static void validarNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
	}

	public static void validarCategoria(String categoria) {
		if (categoria == null || categoria.isBlank()) {
			throw new IllegalArgumentException("La categoría es obligatoria.");
		}
	}

	public static void validarPrecio(double precio) {
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo.");
		}
	}

	public static void validarStock(int stock) {
		if (stock < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo.");
		}
	}

	public static double parsePrecio(String texto) {
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

	public static int parseStock(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("El stock es obligatorio.");
		}
		String valor = texto.trim();
		if (!valor.matches("\\d+")) {
			throw new IllegalArgumentException("El stock debe contener solo números enteros.");
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
	
	/**
	 * Obtiene el identificador único del producto.
	 * * @return El código numérico {@code productoId}.
	 */
	public int getId() {
		return productoId;
	}
	
	/**
	 * Obtiene la denominación comercial del producto.
	 * * @return Cadena de texto con el nombre del artículo.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Obtiene la categoría comercial asignada al producto.
	 * * @return Cadena de texto representativa del sector del artículo.
	 */
	public String getCategoria() {
		return categoria;
	}
	
	/**
	 * Obtiene el precio de venta al público del artículo.
	 * * @return Valor numérico de tipo real con el costo unitario.
	 */
	public double getPrecio() {
		return precio;
	}
	
	/**
	 * Obtiene el texto descriptivo o especificaciones del artículo.
	 * * @return Cadena de texto con el detalle del producto.
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Obtiene el volumen actual de existencias del producto en almacén.
	 * * @return Valor entero con el stock remanente.
	 */
	public int getStock() {
		return stock;
	}
	
	/**
	 * Comprueba el estado de disponibilidad o visibilidad comercial del producto.
	 * * @return {@code true} si el artículo está operativo para transacciones; {@code false} en caso contrario.
	 */
	public boolean isActivo() {
		return activo;
	}
}