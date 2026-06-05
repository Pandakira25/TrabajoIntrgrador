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

	private static final String COL_DIRECCION = "direccion";
	private static final String COL_N_TARJETA = "n_tarjeta";

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

	public int insertUsr(Usuario u) {
	    String senUsr = "INSERT INTO " + NOM_TAB + " (" + COL_AUTORIZACION + ", " + COL_NOMBRE + ", " + COL_CONTRASENIA + ", " + COL_ACTIVO + ", " + COL_TEL + ") VALUES(?,?,?,?,?)";

	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = acc.getConnection();
	        pstmt = con.prepareStatement(senUsr, Statement.RETURN_GENERATED_KEYS);

	        pstmt.setInt(1, u.getAutorizacion());
	        pstmt.setString(2, u.getNombre());
	        pstmt.setString(3, u.getContrasenia());
	        pstmt.setInt(4, u.isActivo() ? 1 : 0);
	        pstmt.setInt(5, u.getTel());

	        int f = pstmt.executeUpdate();

	        if (f > 0) {
	            ResultSet rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                int id = rs.getInt(1);
	                pstmt.close();

	                if (u instanceof Empleado) {
	                    Empleado emp = (Empleado) u;
	                    String senEmp = "INSERT INTO " + NOM_TAB_EMPLEADO + " (" + COL_EMPLEADO_ID + ", " + COL_N_SEG_S + ", " + COL_IBAN + ") VALUES(?,?,?)";
	                    pstmt = con.prepareStatement(senEmp);
	                    pstmt.setInt(1, id);
	                    pstmt.setString(2, emp.getnSeguridad());
	                    pstmt.setString(3, emp.getIban());
	                    pstmt.executeUpdate();

	                } else if (u instanceof Comprador) {
	                    Comprador comp = (Comprador) u;
	                    String senComp = "INSERT INTO " + NOM_TAB_COMPRADOR + " (" + COL_COMPRADOR_ID + ") VALUES(?)";
	                    pstmt = con.prepareStatement(senComp);
	                    pstmt.setInt(1, id);
	                    pstmt.executeUpdate();
	                }

	                return 1;
	            }
	        }
	        return -1;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public ArrayList<Empleado> selectEmpleados(String nombre) {
	    ArrayList<Empleado> emp = new ArrayList<Empleado>();

	    String sen = "SELECT * FROM " + NOM_TAB + " u JOIN " + NOM_TAB_EMPLEADO + " e ON u." + COL_USUARIO_ID + " = e."
	            + COL_EMPLEADO_ID + " WHERE u." + COL_ACTIVO + " = 1";
	    
	    if (nombre != null && !nombre.isEmpty()) {
	        sen += " AND u." + COL_NOMBRE + " LIKE ?";
	    }

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rslt = null;

	    try {
	        con = acc.getConnection();

	        pstmt = con.prepareStatement(sen);
	        
	        if (nombre != null && !nombre.isEmpty()) {
	            pstmt.setString(1, nombre + "%");
	        }

	        rslt = pstmt.executeQuery();

	        while (rslt.next()) {
	            emp.add(new Empleado(rslt.getInt(COL_USUARIO_ID), rslt.getInt(COL_AUTORIZACION),
	                    rslt.getString(COL_NOMBRE), rslt.getString(COL_CONTRASENIA), rslt.getInt(COL_TEL),
	                    rslt.getBoolean(COL_ACTIVO), rslt.getString(COL_N_SEG_S), rslt.getString(COL_IBAN)));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
	    } finally {
	        try {
	            if (rslt != null)
	                rslt.close();
	            if (pstmt != null)
	                pstmt.close();
	            if (con != null)
	                con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return emp;
	}

	public String deleteEmp(String nombre) {
		String sen = "UPDATE usuario SET " + COL_ACTIVO + " = 0 WHERE " +  COL_NOMBRE + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, nombre);

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Se ha eliminado el empleado con exito";
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
