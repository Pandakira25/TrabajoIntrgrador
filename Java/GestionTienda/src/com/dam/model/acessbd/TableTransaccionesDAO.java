package com.dam.model.acessbd;

public class TableTransaccionesDAO {
	
	private AccessDBProp acc;
	
	private static final String COL_COMPRADOR_ID = "comprador_id";
	private static final String COL_CARRITO_ID = "carrito_id";
	private static final String COL_EMPLEADO_ID = "empleado_id";
	private static final String COL_FECHA = "fecha";
	
	public TableTransaccionesDAO() {
		acc = new AccessDBProp();
	}
	
	
}
