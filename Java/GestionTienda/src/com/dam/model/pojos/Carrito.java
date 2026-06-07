package com.dam.model.pojos;

import java.util.HashMap;

/**
 * Clase del modelo que representa el contenedor de compras de un usuario (`Carrito`).
 * <p>
 * Se encarga de agrupar y gestionar de forma temporal los artículos seleccionados por un cliente 
 * antes de proceder al registro de una transacción comercial. Vincula cada entidad {@link Producto} 
 * con un valor entero que determina la cantidad de unidades solicitadas mediante una estructura de diccionario.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class Carrito {

	/** Identificador único asignado al registro del carrito de compra. */
	private int carritoId;
	/** Diccionario estructurado que asocia objetos de tipo {@link Producto} con sus respectivas cantidades ordenadas. */
	private HashMap<Producto, Integer> productos;
	
	/**
	 * Constructor por defecto de la clase.
	 * Permite crear una instancia vacía del carrito para su posterior inicialización o mapeo.
	 */
	public Carrito() {
		
	}

	/**
	 * Constructor parametrizado completo de la clase.
	 * * @param carritoId Identificador numérico único para el carrito.
	 * @param productos Mapa inicial estructurado de productos y cantidades con el que se poblará el contenedor.
	 */
	public Carrito(int carritoId, HashMap<Producto, Integer> productos) {
		super();
		this.carritoId = carritoId;
		this.productos = productos;
	}
	
	/**
	 * Agrega un producto al contenedor o sobrescribe la cantidad existente de un artículo determinado.
	 * * @param p Estructura u objeto de tipo {@link Producto} que se desea registrar.
	 * @param cantidad Volumen de unidades numéricas asignadas para la adquisición del producto.
	 */
	public void addP(Producto p, int cantidad) {
		productos.put(p, cantidad);
	}
	
	/**
	 * Remueve por completo la clave de un producto de la colección, desvinculándolo del carrito.
	 * * @param p Instancia de tipo {@link Producto} que se va a retirar.
	 */
	public void dellP(Producto p) {
		productos.remove(p);
	}
	
	/**
	 * Decrementa la cantidad de existencias de un artículo restándole el valor especificado.
	 * * @param p Instancia de tipo {@link Producto} cuyo volumen de unidades se va a modificar.
	 * @param cantR Unidades que se desean deducir del total actual indexado.
	 */
	public void restCant(Producto p, int cantR) {
		int cantUpd = productos.get(p) - cantR;
		productos.put(p, cantUpd);
	}
	
	/**
	 * Actualiza la cantidad de un artículo basándose en una variable incremental suministrada.
	 * * @param p Instancia de tipo {@link Producto} cuyo volumen de unidades se va a modificar.
	 * @param cantAdd Valor numérico para el cálculo de actualización.
	 */
	public void addCant(Producto p, int cantAdd) {
		int cantUpd = productos.get(p) - cantAdd;
		productos.put(p, cantUpd);
	}
	
	/**
	 * Limpia y vacía el diccionario interno de productos, dejando el contenedor con cero elementos.
	 */
	public void clearCarrtio() {
		productos.clear();
	}
}