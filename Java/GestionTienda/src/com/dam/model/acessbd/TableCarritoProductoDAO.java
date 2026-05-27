package com.dam.model.acessbd;

public class TableCarritoProductoDAO {
	
	private AccessDBProp acc;
	
	private static final String COL_CARRITO_ID = "carrito_id";
	private static final String COL_PRODUCTO_ID = "producto_id";
	private static final String COL_CANTIDAD_P = "cantidad_p";
	
	public TableCarritoProductoDAO() {
		acc = new AccessDBProp();
	}
}
