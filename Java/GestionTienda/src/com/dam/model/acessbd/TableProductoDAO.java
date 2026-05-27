package com.dam.model.acessbd;

public class TableProductoDAO {

	private AccessDBProp acc;

	private static final String COL_PRODUCTO_ID = "producto_id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_CATEGORIA = "categoria";
	private static final String COL_PRECIO = "precio";
	private static final String COL_DESCRIPCION = "descripcion";
	private static final String COL_STOCK = "stock";
	
	public TableProductoDAO() {
		acc = new AccessDBProp();
	}
}
