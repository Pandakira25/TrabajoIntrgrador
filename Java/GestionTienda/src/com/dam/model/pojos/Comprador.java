package com.dam.model.pojos;

/**
 * Clase del modelo que representa a un usuario con el rol de comprador (`Comprador`).
 * <p>
 * Hereda de la clase base {@link Usuario} y extiende sus atributos para incluir información 
 * específica de facturación y logística, como la dirección de envío, el número de tarjeta 
 * de pago y un objeto {@link Carrito} asociado de forma persistente para gestionar sus compras actuales.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class Comprador extends Usuario{

	/** Dirección física o postal de entrega asignada al comprador. */
	private String direccion;
	/** Número de la tarjeta bancaria de crédito o débito vinculada a la cuenta para realizar pagos. */
	private String nTarjeta;
	/** Instancia del carrito de compras asignada de forma exclusiva a este comprador. */
	private Carrito carrito;
	
	/**
	 * Constructor completo parametrizado para recuperar o inicializar un comprador existente en la base de datos.
	 * <p>
	 * Inicializa los atributos generales del usuario invocando al constructor de la clase base `Usuario` 
	 * e instancia de forma interna un contenedor de tipo {@link Carrito} vacío.
	 * </p>
	 * * @param id           Identificador numérico único del usuario en el sistema.
	 * @param autorizacion Nivel o código numérico de privilegios asignado.
	 * @param nombre       Nombre único de acceso del usuario.
	 * @param contrasenia  Clave o palabra secreta de autenticación.
	 * @param tel          Número telefónico de contacto.
	 * @param activo       Estado de habilitación o vigencia lógica de la cuenta.
	 * @param direccion    Ubicación física de entrega del comprador.
	 * @param nTarjeta     Identificador de la tarjeta financiera asociada.
	 */
	public Comprador(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo,
			String direccion, String nTarjeta) {
		super(id, autorizacion, nombre, contrasenia, tel, activo);
		this.direccion = direccion;
		this.nTarjeta = nTarjeta;
		carrito = new Carrito();
	}
	
	/**
	 * Constructor simplificado parametrizado destinado al registro o alta de un nuevo comprador en el sistema.
	 * <p>
	 * Por defecto, delega en la clase base el nivel de autorización fijo con valor {@code 3} 
	 * (comprador) y establece el estado de vigencia inicial de la cuenta como activo ({@code true}).
	 * </p>
	 * * @param nombre      Nombre único de acceso seleccionado por el usuario.
	 * @param contrasenia Clave o palabra secreta de autenticación.
	 * @param tel         Número telefónico de contacto.
	 * @param direccion   Ubicación física de entrega del comprador.
	 * @param nTarjeta    Identificador de la tarjeta financiera asociada.
	 */
	public Comprador(String nombre, String contrasenia, int tel, String direccion, String nTarjeta) {
		super(3, nombre, contrasenia, tel, true);
		this.direccion = direccion;
		this.nTarjeta = nTarjeta;
		carrito = new Carrito();
	}
	

	public static String validarDireccion(String direccion) {
		if (direccion == null || direccion.isBlank()) {
			throw new IllegalArgumentException("La dirección es obligatoria.");
		}
		return direccion.trim();
	}

	public static String validarTarjeta(String nTarjeta) {
		if (nTarjeta == null || nTarjeta.isBlank()) {
			throw new IllegalArgumentException("Debe ingresar una tarjeta.");
		}
		String tarjeta = nTarjeta.trim();
		if (!tarjeta.matches("\\d{13,19}")) {
			throw new IllegalArgumentException("La tarjeta debe tener entre 13 y 19 dígitos numéricos.");
		}
		return tarjeta;
	}

	/**
	 * Devuelve una representación textual legible con el estado actual del objeto Comprador.
	 * Concatena sus propios atributos específicos junto con la salida literal devuelta por la superclase.
	 * * @return Cadena de texto formateada que describe el estado del comprador.
	 */
	@Override
	public String toString() {
		return "Comprador [direccion=" + direccion + ", nTarjeta=" + nTarjeta + ", toString()=" + super.toString()
				+ "]";
	}
	
	/**
	 * Proporciona acceso al carrito de compras asociado de forma exclusiva a este perfil.
	 * * @return La instancia de {@link Carrito} vinculada al comprador.
	 */
	public Carrito getCarrito() {
		return carrito;
	}

	/**
	 * Obtiene la dirección postal de entrega registrada para el comprador.
	 * * @return Cadena de texto correspondiente a la localización de envío.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Obtiene el identificador numérico de la tarjeta bancaria asociada a la cuenta del comprador.
	 * * @return Cadena de texto representativa del número de tarjeta de pago.
	 */
	public String getnTarjeta() {
		return nTarjeta;
	}
}
