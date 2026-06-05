package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dam.model.pojos.Usuario;

public class TableUsuarioDAO {

	private AccessDBProp acc;

	private static final String COL_USUARIO_ID = "usuario_id";
	private static final String COL_AUTORIZACION = "autorizacion";
	private static final String COL_USUARIO = "nombre";
	private static final String COL_CONTRASENIA = "contrasenia";
	private static final String COL_ACTIVO = "activo";
	private static final String COL_TEL = "tel";

	public TableUsuarioDAO() {
		acc = new AccessDBProp();
	}

	public Usuario login(String usuario, String contrasenia) {
		String sql = "SELECT " + COL_USUARIO_ID + ", " + COL_AUTORIZACION + ", " + COL_USUARIO + ", "
				+ COL_CONTRASENIA + ", " + COL_TEL + ", " + COL_ACTIVO + " FROM usuario WHERE " + COL_USUARIO
				+ " = ? AND " + COL_CONTRASENIA + " = ?";

		try (Connection con = acc.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, usuario);
			ps.setString(2, contrasenia);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Usuario(
							rs.getInt(COL_USUARIO_ID),
							rs.getInt(COL_AUTORIZACION),
							rs.getString(COL_USUARIO),
							rs.getString(COL_CONTRASENIA),
							rs.getInt(COL_TEL),
							rs.getInt(COL_ACTIVO) == 1);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
