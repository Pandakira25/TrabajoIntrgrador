package com.dam.model.pojos;

public class Empleado extends Usuario {

	private static final int DIGITOS_SEG_SOCIAL = 12;

	private String nSeguridad;
	private String iban;

	public Empleado(int id, int autorizacion, String nombre, String contrasenia, int tel, boolean activo,
			String nSeguridad, String iban) {
		super(id, autorizacion, nombre, contrasenia, tel, activo);
		this.nSeguridad = validarNumSeguridadSocial(nSeguridad);
		this.iban = validarIban(iban);
	}

	public Empleado(int autorizacion, String nombre, String contrasenia, int tel, boolean activo, String nSeguridad,
			String iban) {
		this(0, autorizacion, nombre, contrasenia, tel, activo, nSeguridad, iban);
	}

	/** Constructor desde formulario: valida y parsea todos los campos. */
	public Empleado(int autorizacion, String nombre, String contrasenia, String telStr, boolean activo,
			String nSeguridad, String iban) {
		this(autorizacion, nombre, contrasenia, parseTelefono(telStr), activo, nSeguridad, iban);
	}

	private static String validarNumSeguridadSocial(String texto) {
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

	private static String validarIban(String texto) {
		if (texto == null || texto.isBlank()) {
			throw new IllegalArgumentException("El IBAN es obligatorio.");
		}
		String iban = texto.trim().toUpperCase().replace(" ", "");
		if (!iban.matches("ES\\d{22}")) {
			throw new IllegalArgumentException(
					"El IBAN debe ser español (ES seguido de 22 dígitos, 24 caracteres en total).");
		}
		return iban;
	}

	public String getnSeguridad() {
		return nSeguridad;
	}

	public String getIban() {
		return iban;
	}

	@Override
	public String toString() {
		return "Empleado [nSeguridad=" + nSeguridad + ", iban=" + iban + ", toString()=" + super.toString() + "]";
	}
}
