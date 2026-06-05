package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dam.model.pojos.Comprador;

public class TableCompradorDAO {
	
	private AccessDBProp acc;
	
	private static final String COL_COMPRADOR_ID = "comprador_id";
	private static final String COL_DIRECCION = "direccion";
	private static final String COL_N_TARJETA = "n_tarjeta";

	private static final String NOM_TAB = "comprador";
	
	public TableCompradorDAO() {
		acc = new AccessDBProp();
	}
}
