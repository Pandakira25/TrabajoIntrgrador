package com.dam.model.acessbd;

public class TableCarritoDAO {
	
	private AccessDBProp acc;
	
	private static final String COL_CARRITO_ID = "carrito_id";
	private static final String COL_COMPRADOR_ID = "comprador_id";
	
	public TableCarritoDAO() {
		acc = new AccessDBProp();
	}
}
