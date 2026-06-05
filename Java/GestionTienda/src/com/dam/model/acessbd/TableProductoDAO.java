package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dam.model.pojos.Producto;

public class TableProductoDAO {

	private AccessDBProp acc;

	private static final String NOM_TAB = "producto";

	private static final String COL_PRODUCTO_ID = "producto_id";
	private static final String COL_NOMBRE = "nombre";
	private static final String COL_CATEGORIA = "categoria";
	private static final String COL_PRECIO = "precio";
	private static final String COL_DESCRIPCION = "descripcion";
	private static final String COL_STOCK = "stock";
	private static final String COL_ACTIVO = "activo";

	public TableProductoDAO() {
		acc = new AccessDBProp();
	}

	public ArrayList<Producto> selectProductos(String nombre, String precio, String categoria, boolean soloActivos) {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sen = "SELECT * FROM " + NOM_TAB + " WHERE 1=1";

		if (soloActivos) {
			sen += " AND " + COL_ACTIVO + " = 1";
		}
		if (nombre != null && !nombre.isEmpty()) {
			sen += " AND " + COL_NOMBRE + " LIKE ?";
		}
		if (precio != null) {
			switch (precio) {
			case "< 10 €":
				sen += " AND " + COL_PRECIO + " < 10";
				break;
			case "10 - 50 €":
				sen += " AND " + COL_PRECIO + " BETWEEN 10 AND 50";
				break;
			case "> 50 €":
				sen += " AND " + COL_PRECIO + " > 50";
				break;
			}
		}
		if (categoria != null && !categoria.isEmpty()) {
			sen += " AND " + COL_CATEGORIA + " = ?";
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);

			int param = 1;
			if (nombre != null && !nombre.isEmpty()) {
				pstmt.setString(param++, nombre + "%");
			}
			if (categoria != null && !categoria.isEmpty()) {
				pstmt.setString(param++, categoria);
			}

			rslt = pstmt.executeQuery();

			while (rslt.next()) {
				productos.add(new Producto(rslt.getInt(COL_PRODUCTO_ID), rslt.getString(COL_NOMBRE),
						rslt.getString(COL_CATEGORIA), rslt.getDouble(COL_PRECIO), rslt.getString(COL_DESCRIPCION),
						rslt.getInt(COL_STOCK), rslt.getBoolean(COL_ACTIVO)));
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

		return productos;
	}

	public ArrayList<String> selectCategorias() {
		ArrayList<String> categorias = new ArrayList<String>();

		String sen = "SELECT DISTINCT " + COL_CATEGORIA + " FROM " + NOM_TAB;

		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConnection();

			stmt = con.createStatement();

			rslt = stmt.executeQuery(sen);

			while (rslt.next()) {
				categorias.add(rslt.getString(COL_CATEGORIA));
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

		return categorias;
	}

	public String disableProd(String nombre) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_ACTIVO + " = 0 WHERE " + COL_NOMBRE + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, nombre);

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Se ha deshabilitado el producto con éxito";
			} else {
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

	public String enableProd(String nombre) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_ACTIVO + " = 1 WHERE " + COL_NOMBRE + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, nombre);

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Se ha habilitado el producto con éxito";
			} else {
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

	public String insertProducto(Producto p) {
		String sen = "INSERT INTO " + NOM_TAB + " (" + COL_NOMBRE + ", " + COL_CATEGORIA + ", " + COL_PRECIO + ", "
				+ COL_DESCRIPCION + ", " + COL_STOCK + ") VALUES(?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getCategoria());
			pstmt.setDouble(3, p.getPrecio());
			pstmt.setString(4, p.getDescripcion());
			pstmt.setInt(5, p.getStock());

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Producto agregado con éxito";
			} else {
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

	public String updateProducto(Producto p) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_NOMBRE + " = ?, " + COL_CATEGORIA + " = ?, " + COL_PRECIO
				+ " = ?, " + COL_DESCRIPCION + " = ?, " + COL_STOCK + " = ? WHERE " + COL_PRODUCTO_ID + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getCategoria());
			pstmt.setDouble(3, p.getPrecio());
			pstmt.setString(4, p.getDescripcion());
			pstmt.setInt(5, p.getStock());
			pstmt.setInt(6, p.getId());

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Producto modificado con éxito";
			} else {
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

	public void decrementarStock(int productoId, int cantidad) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_STOCK + " = " + COL_STOCK + " - ? WHERE " + COL_PRODUCTO_ID
				+ " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);
			pstmt.setInt(1, cantidad);
			pstmt.setInt(2, productoId);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
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
	
	public void incrementarStock(int productoId, int cantidad) {
	    String sen = "UPDATE " + NOM_TAB + " SET " + COL_STOCK + " = " + COL_STOCK + " + ? WHERE " + COL_PRODUCTO_ID + " = ?";

	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = acc.getConnection();
	        pstmt = con.prepareStatement(sen);
	        pstmt.setInt(1, cantidad);
	        pstmt.setInt(2, productoId);
	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
