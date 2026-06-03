package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dam.model.pojos.Comprador;
import com.dam.model.pojos.Usuario;
import com.dam.model.pojos.Empleado;

public class TableUsuarioDAO {

	private AccessDBProp acc;

	private static final String COL_USUARIO_ID = "usuario_id";
	private static final String COL_EMPLEADO_ID = "empleado_id";
	private static final String COL_COMPRADOR_ID = "comprador_id";
	
	private static final String COL_AUTORIZACION = "autorizacion";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_CONTRASENIA = "contrasenia";
	private static final String COL_ACTIVO = "activo";
	private static final String COL_TEL = "tel";
	
	private static final String COL_N_SEG_S = "n_seg_social";
	private static final String COL_IBAN = "iban";

	private static final String NOM_TAB = "usuario";
	private static final String NOM_TAB_COMPRADOR = "comprador";
	private static final String NOM_TAB_EMPLEADO = "empleado";

	public TableUsuarioDAO() {
		acc = new AccessDBProp();
	}

	public Usuario login(String usuario, String contrasenia) {
		String sql = "SELECT " + COL_USUARIO_ID + ", " + COL_AUTORIZACION + ", " + COL_NOMBRE + ", " + COL_CONTRASENIA
				+ ", " + COL_TEL + ", " + COL_ACTIVO + " FROM usuario WHERE " + COL_NOMBRE + " = ? AND "
				+ COL_CONTRASENIA + " = ?";

		try (Connection con = acc.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, usuario);
			ps.setString(2, contrasenia);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Usuario(rs.getInt(COL_USUARIO_ID), rs.getInt(COL_AUTORIZACION), rs.getString(COL_NOMBRE),
							rs.getString(COL_CONTRASENIA), rs.getInt(COL_TEL), rs.getInt(COL_ACTIVO) == 1);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertUsr(Comprador compr) {
		String sen = "INSERT INTO " + NOM_TAB + " (autorizacion, nombre, contrasenia, activo, tel) VALUES(?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, compr.getAutorizacion());
			pstmt.setString(2, compr.getNombre());
			pstmt.setString(3, compr.getContrasenia());
			int activo = compr.isActivo() ? 1 : 0;
			pstmt.setInt(4, activo);
			pstmt.setInt(5, compr.getTel());

			int f = pstmt.executeUpdate();

			if (f > 0) {
				// Hacer el select del id
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return -1;
	}

	public ArrayList<Empleado> selectAllEmpleados() {
		ArrayList<Empleado> emp = new ArrayList<Empleado>();

		String sen = "SELECT * FROM " + NOM_TAB + " u JOIN " + NOM_TAB_EMPLEADO + " e ON u." + COL_USUARIO_ID + " = e." + COL_EMPLEADO_ID;

		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConnection();

			stmt = con.createStatement();

			rslt = stmt.executeQuery(sen);

			while (rslt.next()) {
				emp.add(new Empleado(rslt.getInt(COL_USUARIO_ID), rslt.getInt(COL_AUTORIZACION), rslt.getString(COL_NOMBRE),
						rslt.getString(COL_CONTRASENIA), rslt.getInt(COL_TEL), rslt.getBoolean(COL_ACTIVO), rslt.getString(COL_N_SEG_S),
						rslt.getString(COL_IBAN)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return emp;
	}
}
