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
	
	public TableProductoDAO() {
		acc = new AccessDBProp();
	}
	
	public ArrayList<Producto> selectProductos(String nombre, String precio, String categoria) {
	    ArrayList<Producto> productos = new ArrayList<Producto>();

	    String sen = "SELECT * FROM " + NOM_TAB + " WHERE 1=1";

	    if (nombre != null && !nombre.isEmpty()) {
	        sen += " AND " + COL_NOMBRE + " LIKE ?";
	    }
	    if (precio != null) {
	        switch (precio) {
	            case "< 10 €":    sen += " AND " + COL_PRECIO + " < 10"; break;
	            case "10 - 50 €": sen += " AND " + COL_PRECIO + " BETWEEN 10 AND 50"; break;
	            case "> 50 €":    sen += " AND " + COL_PRECIO + " > 50"; break;
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
	                    rslt.getString(COL_CATEGORIA), rslt.getDouble(COL_PRECIO),
	                    rslt.getString(COL_DESCRIPCION), rslt.getInt(COL_STOCK)));
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
}
