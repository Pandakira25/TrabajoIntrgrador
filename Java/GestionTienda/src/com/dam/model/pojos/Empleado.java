package com.dam.model.pojos;

/**
 * Clase del modelo que representa a un usuario con privilegios o rol de trabajador (`Empleado`).
 * <p>
 * Hereda de la clase base {@link Usuario} y extiende sus atributos para albergar la información 
 * administrativa y laboral necesaria para la gestión interna de la empresa, tales como el número 
 * de la Seguridad Social y el código de cuenta bancaria internacional (IBAN) para el abono de nóminas.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class Empleado extends Usuario{
	
	/** Número de afiliación a la Seguridad Social asignado al empleado. */
	private static final int DIGITOS_SEG_SOCIAL = 12;

	private String nSeguridad;
	/** Código internacional de cuenta bancaria (IBAN) del empleado para transacciones y pagos. */
	private String iban;

	/**
	 * Constructor completo parametrizado utilizado habitualmente para reconstruir o inicializar un empleado 
	 * cuyos datos ya existen y están persistidos con un identificador único en el sistema.
	 * <p>
	 * Inicializa los atributos generales delegando en el constructor homólogo de la superclase `Usuario`.
	 * </p>
	 * * @param id           Identificador numérico único del usuario en el sistema.
	 * @param autorizacion Nivel o código numérico de privilegios asignado.
	 * @param nombre       Nombre único de acceso del usuario.
	 * @param contrasenia  Clave o palabra secreta de autenticación.
	 * @param tel          Número telefónico de contacto.
	 * @param activo       Estado de habilitación o vigencia lógica de la cuenta.
	 * @param nSeguridad   Número identificativo de la Seguridad Social del trabajador.
	 * @param iban         Código de la cuenta bancaria para el procesamiento de pagos.
	 */
	public Empleado(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo,
			String nSeguridad, String iban) {
		super(id, autorizacion, nombre, contrasenia, tel, activo);
		this.nSeguridad = nSeguridad;
		this.iban = iban;
	}
	
	/**
	 * Constructor parametrizado sin el atributo ID, utilizado comúnmente para el alta o registro 
	 * inicial de un nuevo empleado en el sistema donde el identificador es autogenerado posteriormente.
	 * <p>
	 * Inicializa los parámetros de identidad llamando al constructor base de `Usuario`.
	 * </p>
	 * * @param autorizacion Nivel o código numérico de privilegios asignado (Administrador/Empleado).
	 * @param nombre       Nombre único de acceso seleccionado para el usuario.
	 * @param contrasenia  Clave o palabra secreta de autenticación.
	 * @param tel          Número telefónico de contacto.
	 * @param activo       Estado de habilitación inicial de la cuenta en el sistema.
	 * @param nSeguridad   Número identificativo de la Seguridad Social del trabajador.
	 * @param iban         Código de la cuenta bancaria para el procesamiento de pagos.
	 */
	public Empleado(int autorizacion, String nombre, String contrasenia, int tel, boolean activo, String nSeguridad,
			String iban) {
		super(autorizacion, nombre, contrasenia, tel, activo);
		this.nSeguridad = nSeguridad;
		this.iban = iban;
	}
	

	public static String validarNumSeguridadSocial(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("El número de seguridad social es obligatorio.");
		}
		String nss = texto.trim();
		if (!nss.matches("\\d{" + DIGITOS_SEG_SOCIAL + "}")) {
			throw new IllegalArgumentException(
					"El número de seguridad social debe tener exactamente " + DIGITOS_SEG_SOCIAL + " dígitos.");
		}
		return nss;
	}

	public static String validarIban(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("El IBAN es obligatorio.");
		}
		String iban = texto.trim().toUpperCase().replace(" ", "");
		//System.out.println(iban);
		if (!iban.matches("ES\\d{22}")) {
			throw new IllegalArgumentException(
					"El IBAN debe ser español (ES seguido de 22 dígitos, 24 caracteres en total).");
		}
		return iban;
	}

	/**
	 * Obtiene el número de afiliación a la Seguridad Social registrado para el empleado.
	 * * @return Cadena de texto que representa el identificador de la Seguridad Social.
	 */
	public String getnSeguridad() {
		return nSeguridad;
	}

	/**
	 * Obtiene el código internacional de cuenta bancaria (IBAN) asociado al empleado.
	 * * @return Cadena de texto correspondiente a la numeración bancaria para transferencias.
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * Devuelve una representación textual inteligible con el estado detallado del objeto Empleado.
	 * Concatena sus atributos específicos de nómina junto con la salida devuelta por el método homónimo de la clase base.
	 * * @return Cadena de texto formateada que detalla el estado actual del empleado.
	 */
	@Override
	public String toString() {
		return "Empleado [nSeguridad=" + nSeguridad + ", iban=" + iban + ", toString()=" + super.toString() + "]";
	}
}
