package com.dam.model.pojos;

/**
 * Clase del modelo que representa el registro histórico de una operación comercial (`Transacciones`).
 * <p>
 * Almacena los metadatos y referencias clave vinculados a una compra finalizada con éxito, 
 * uniendo de forma relacional el identificador del cliente que adquiere los productos, el del 
 * empleado que procesa o asiste en la operación, el carrito de origen, la marca de tiempo de la 
 * compra y el importe económico total facturado.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class Transacciones {

	/** Identificador único del comprador asociado a la transacción. */
	private int comptradorId;
	/** Identificador único del empleado que ha procesado la transacción. */
	private int empleadoId;
	/** Identificador único del carrito de la compra asociado a los artículos adquiridos. */
	private int carritoId;
	/** Cadena de texto que representa la fecha y hora en la que se efectuó la operación. */
	private String fecha;
	/** Valor económico global e importe total tasado y facturado de la venta. */
	private double importe;
	
	/**
	 * Constructor completo parametrizado para instanciar el registro histórico de una transacción.
	 * * @param comptradorId Identificador numérico único del usuario comprador.
	 * @param empleadoId   Identificador numérico único del usuario empleado.
	 * @param carritoId    Identificador numérico único del carrito de origen.
	 * @param fecha        Cadena de caracteres representativa de la fecha de ejecución.
	 * @param importe      Monto total final cobrado en la operación.
	 */
	public Transacciones(int comptradorId, int empleadoId, int carritoId, String fecha, double importe) {
		this.comptradorId = comptradorId;
		this.empleadoId = empleadoId;
		this.carritoId = carritoId;
		this.fecha = fecha;
		this.importe = importe;
	}
	
	
	/**
	 * Devuelve una representación textual inteligible con los atributos e identificadores del objeto Transacciones.
	 * * @return Cadena de texto formateada que describe detalladamente los datos de la transacción.
	 */
	@Override
	public String toString() {
		return "Transacciones [comptradorId=" + comptradorId + ", empleadoId=" + empleadoId + ", carritoId=" + carritoId
				+ ", fecha=" + fecha + ", importe=" + importe + "]";
	}
}