package com.dam.model.pojos;

public class Usuario {

	private static final int DIGITOS_TELEFONO = 9;

	protected int userId;
	protected int autorizacion;
	protected String nombre;
	protected String contrasenia;
	protected int tel;
	protected boolean activo;

	public Usuario(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo) {
		validarAutorizacion(autorizacion);
		validarNombre(nombre);
		validarContrasenia(contrasenia);
		validarTel(tel);
		this.userId = id;
		this.autorizacion = autorizacion;
		this.nombre = nombre.trim();
		this.contrasenia = contrasenia;
		this.tel = tel;
		this.activo = activo;
	}

	public Usuario(int autorizacion, String nombre, String contrasenia, int tel, boolean activo) {
		this(0, autorizacion, nombre, contrasenia, tel, activo);
	}

	protected static void validarNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
	}

	protected static void validarContrasenia(String contrasenia) {
		if (contrasenia == null || contrasenia.isBlank()) {
			throw new IllegalArgumentException("La contraseña es obligatoria.");
		}
	}

	protected static void validarAutorizacion(int autorizacion) {
		if (autorizacion < 1 || autorizacion > 3) {
			throw new IllegalArgumentException("La autorización debe ser 1, 2 o 3.");
		}
	}

	protected static void validarTel(int tel) {
		if (tel < 600_000_000 || tel > 999_999_999) {
			throw new IllegalArgumentException(
					"El teléfono debe tener " + DIGITOS_TELEFONO + " dígitos y comenzar por 6, 7, 8 o 9.");
		}
	}

	/** Convierte texto del formulario a teléfono validado. */
	protected static int parseTelefono(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("El teléfono es obligatorio.");
		}
		String tel = texto.trim();
		if (!tel.matches("\\d{" + DIGITOS_TELEFONO + "}")) {
			throw new IllegalArgumentException(
					"El teléfono debe tener exactamente " + DIGITOS_TELEFONO + " dígitos numéricos.");
		}
		char primero = tel.charAt(0);
		if (primero != '6' && primero != '7' && primero != '8' && primero != '9') {
			throw new IllegalArgumentException("El teléfono español debe comenzar por 6, 7, 8 o 9.");
		}
		return Integer.parseInt(tel);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + userId + ", autorizacion=" + autorizacion + ", nombre=" + nombre + ", contrasenia="
				+ contrasenia + ", tel=" + tel + "]";
	}

	public int getUserId() {
		return userId;
	}

	public int getAutorizacion() {
		return autorizacion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public int getTel() {
		return tel;
	}

	public boolean isActivo() {
		return activo;
	}

	public void darDeBaja() {
		activo = false;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
