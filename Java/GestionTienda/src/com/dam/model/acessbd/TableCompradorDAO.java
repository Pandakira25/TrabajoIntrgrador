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

	public String insertUsr(Comprador compr) {
		String sen = "INSERT INTO " + NOM_TAB + " VALUES(?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen);

			pstmt.setInt(1, compr.getUserId());
			pstmt.setString(2, compr.getDireccion());
			pstmt.setString(3, compr.getnTarjeta());

			int f = pstmt.executeUpdate();

			if (f > 0) {
				return "Se ha insertado el restaurante con éxito";
			}else {
				return "Algo malo ocurrió";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: se ha producido un error al establecer la conexion con la base de datos";
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
