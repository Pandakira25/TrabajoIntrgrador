package com.dam.model.pojos;

/**
 * Clase base del modelo que representa a un componente genérico del sistema (`Usuario`).
 * <p>
 * Define los atributos fundamentales de identidad, autenticación, contacto y estado operacional 
 * compartidos por todos los tipos de perfiles en la plataforma. Al declarar sus campos con el modificador 
 * de acceso {@code protected}, permite la herencia directa y reutilización limpia por parte de las 
 * subclases específicas como {@code Empleado} o {@code Comprador}.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class Usuario {
	
	/** Identificador numérico único del usuario en el sistema. */
	protected int userId;
	/** Código o nivel discreto de autorización que determina los privilegios de acceso del perfil. */
	protected int autorizacion;
	/** Nombre de usuario o alias exclusivo utilizado para los procesos de inicio de sesión. */
	protected String nombre;
	/** Palabra secreta o hash de la clave de autenticación para salvaguardar la cuenta. */
	protected String contrasenia;
	/** Número de teléfono o contacto directo del usuario. */
	protected int tel;
	/** Flag lógico indicador de vigencia; {@code true} si está operativo, {@code false} si está deshabilitado. */
	protected boolean activo;

	/**
	 * Constructor completo parametrizado para instanciar un usuario con registros ya persistidos.
	 * * @param id           Identificador numérico único del usuario.
	 * @param autorizacion Nivel de permisos otorgado dentro del ecosistema.
	 * @param nombre       Alias único de acceso a la cuenta.
	 * @param contrasenia  Clave de paso secreta de autenticación.
	 * @param tel          Número telefónico registrado.
	 * @param activo       Estado inicial de habilitación en el sistema.
	 */
	public Usuario(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo) {
		this.userId = id;
		this.autorizacion = autorizacion;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.tel = tel;
		this.activo = activo;
	}
	
	/**
	 * Constructor parametrizado simplificado, utilizado habitualmente en flujos de creación o alta 
	 * donde el identificador único es autogenerado de forma diferida por el motor de persistencia.
	 * * @param autorizacion Nivel de privilegios y rol en la aplicación.
	 * @param nombre       Alias único de acceso a la cuenta.
	 * @param contrasenia  Clave de paso secreta de autenticación.
	 * @param tel          Número telefónico registrado.
	 * @param activo       Estado inicial de habilitación.
	 */
	public Usuario(int autorizacion, String nombre, String contrasenia, int tel, boolean activo) {
		super();
		this.autorizacion = autorizacion;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.tel = tel;
		this.activo = activo;
	}



	/**
	 * Genera una representación textual legible que contiene los atributos de identidad y contacto del Usuario.
	 * * @return Cadena de texto formateada que describe el estado parcial del objeto.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + userId + ", autorizacion=" + autorizacion + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", tel=" + tel + "]";
	}
	
	/**
	 * Obtiene el identificador numérico único de la entidad usuario.
	 * * @return El valor entero de {@code userId}.
	 */
	 public int getUserId() {
		return userId;
	}
	 
	/**
	 * Obtiene el nivel o privilegio numérico asignado al perfil del usuario.
	 * * @return El código discreto de {@code autorizacion}.
	 */
	public int getAutorizacion() {
		return autorizacion;
	}
	
	/**
	 * Obtiene el nombre único o alias de login asociado a la cuenta.
	 * * @return Cadena de caracteres correspondiente al nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Obtiene la contraseña o palabra clave de seguridad.
	 * * @return Cadena de caracteres con el password.
	 */
	public String getContrasenia() {
		return contrasenia;
	}
	
	/**
	 * Obtiene la numeración telefónica de contacto registrada.
	 * * @return Código numérico entero asociado al teléfono.
	 */
	public int getTel() {
		return tel;
	}
	
	/**
	 * Evalúa si el usuario se encuentra operativo dentro de la plataforma.
	 * * @return {@code true} si la cuenta está activa; {@code false} en caso de baja o suspensión.
	 */
	public boolean isActivo() {
		return activo;
	}
	
	/**
	 * Realiza una baja lógica del usuario modificando de forma irreversible su estado interno 
	 * de activación a {@code false}.
	 */
	public void darDeBaja() {
		activo = false; 
	}
	
	/**
	 * Asigna o actualiza el identificador numérico único del usuario.
	 * * @param userId Nuevo código entero de identificación para el registro.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
}