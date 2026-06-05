package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableCarritoProductoDAO {

    private AccessDBProp acc;

    private static final String NOM_TAB = "carrito_producto";
    private static final String COL_CARRITO_ID = "carrito_id";
    private static final String COL_PRODUCTO_ID = "producto_id";
    private static final String COL_CANTIDAD_P = "cantidad_p";

    public TableCarritoProductoDAO() {
        acc = new AccessDBProp();
    }

    public void upsertProducto(int carritoId, int productoId, int cantidad) {
        String senSelect = "SELECT " + COL_CANTIDAD_P + " FROM " + NOM_TAB +
                " WHERE " + COL_CARRITO_ID + " = ? AND " + COL_PRODUCTO_ID + " = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rslt = null;

        try {
            con = acc.getConnection();
            pstmt = con.prepareStatement(senSelect);
            pstmt.setInt(1, carritoId);
            pstmt.setInt(2, productoId);
            rslt = pstmt.executeQuery();

            if (rslt.next()) {
                pstmt.close();
                String senUpdate = "UPDATE " + NOM_TAB + " SET " + COL_CANTIDAD_P + " = ? WHERE " +
                        COL_CARRITO_ID + " = ? AND " + COL_PRODUCTO_ID + " = ?";
                pstmt = con.prepareStatement(senUpdate);
                pstmt.setInt(1, cantidad);
                pstmt.setInt(2, carritoId);
                pstmt.setInt(3, productoId);
            } else {
                pstmt.close();
                String senInsert = "INSERT INTO " + NOM_TAB + " (" + COL_CARRITO_ID + ", " + COL_PRODUCTO_ID + ", " + COL_CANTIDAD_P + ") VALUES(?,?,?)";
                pstmt = con.prepareStatement(senInsert);
                pstmt.setInt(1, carritoId);
                pstmt.setInt(2, productoId);
                pstmt.setInt(3, cantidad);
            }
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
        } finally {
            try {
                if (rslt != null) rslt.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void eliminarProducto(int carritoId, int productoId) {
        String sen = "DELETE FROM " + NOM_TAB + " WHERE " + COL_CARRITO_ID + " = ? AND " + COL_PRODUCTO_ID + " = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = acc.getConnection();
            pstmt = con.prepareStatement(sen);
            pstmt.setInt(1, carritoId);
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