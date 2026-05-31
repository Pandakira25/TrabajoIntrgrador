package com.dam.model.acessbd;

public class TableCompradorDAO {
	
	private AccessDBProp acc;
	
	private static final String COL_COMPRADOR_ID = "comprador_id";
	private static final String COL_DIRECCION = "direccion";
	private static final String COL_N_TARJETA = "n_tarjeta";
	
	public TableCompradorDAO() {
		acc = new AccessDBProp();
	}
}
