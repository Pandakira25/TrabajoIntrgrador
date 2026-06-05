package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCarritoDAO {

    private AccessDBProp acc;

    private static final String NOM_TAB = "carrito";
    private static final String NOM_TAB_TRANS = "transacciones";
    private static final String COL_CARRITO_ID = "carrito_id";
    private static final String COL_COMPRADOR_ID = "comprador_id";

    public TableCarritoDAO() {
        acc = new AccessDBProp();
    }

    public int crearCarrito(int compradorId) {
        String sen = "INSERT INTO " + NOM_TAB + " (" + COL_COMPRADOR_ID + ") VALUES(?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = acc.getConnection();
            pstmt = con.prepareStatement(sen, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, compradorId);

            int f = pstmt.executeUpdate();
            if (f > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
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
        return -1;
    }

    public int getCarritoActivo(int compradorId) {
        String sen = "SELECT " + COL_CARRITO_ID + " FROM " + NOM_TAB +
                " WHERE " + COL_COMPRADOR_ID + " = ?" +
                " AND " + COL_CARRITO_ID + " NOT IN (SELECT " + COL_CARRITO_ID + " FROM " + NOM_TAB_TRANS + ")" +
                " LIMIT 1";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rslt = null;

        try {
            con = acc.getConnection();
            pstmt = con.prepareStatement(sen);
            pstmt.setInt(1, compradorId);
            rslt = pstmt.executeQuery();

            if (rslt.next()) {
                return rslt.getInt(COL_CARRITO_ID);
            }
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
        return -1;
    }
}