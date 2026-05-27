package com.dam.model.acessbd;

public class TableUsuarioDAO {
	
	private AccessDBProp acc;
	
	private static final String COL_USUARIO_ID = "usuario_id";
	private static final String COL_AUTORIZACION = "autorizacion";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_CONTRASENIA = "contrasenia";
	private static final String COL_ACTIVO = "activo";
	private static final String COL_TEL = "tel";

	public TableUsuarioDAO() {
		acc = new AccessDBProp();
	}
	
	
}
