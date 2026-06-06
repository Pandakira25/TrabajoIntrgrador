package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TableTransaccionesDAO {

    private AccessDBProp acc;

    private static final String NOM_TAB = "transacciones";
    private static final String COL_COMPRADOR_ID = "comprador_id";
    private static final String COL_CARRITO_ID = "carrito_id";
    private static final String COL_EMPLEADO_ID = "empleado_id";

    public TableTransaccionesDAO() {
        acc = new AccessDBProp();
    }

    public String insertTransaccion(int compradorId, int carritoId, Integer empleadoId) {
        String sen = "INSERT INTO " + NOM_TAB + " (" + COL_COMPRADOR_ID + ", " + COL_CARRITO_ID + ", " + COL_EMPLEADO_ID + ") VALUES(?,?,?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = acc.getConnection();
            pstmt = con.prepareStatement(sen);
            pstmt.setInt(1, compradorId);
            pstmt.setInt(2, carritoId);
            if (empleadoId != null) {
                pstmt.setInt(3, empleadoId);
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }

            int f = pstmt.executeUpdate();
            if (f > 0) {
                return "Compra realizada con éxito";
            } else {
                return "Algo malo ocurrió";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: se ha producido un error al establecer la conexion con la base de datos";
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String[][] selectTransacciones() {
        String sen = "SELECT u_comp.nombre, u_emp.nombre, " +
                     "SUM(p.precio * cp.cantidad_p) " +
                     "FROM transacciones t " +
                     "JOIN usuario u_comp ON t.comprador_id = u_comp.usuario_id " +
                     "LEFT JOIN usuario u_emp ON t.empleado_id = u_emp.usuario_id " +
                     "JOIN carrito_producto cp ON t.carrito_id = cp.carrito_id " +
                     "JOIN producto p ON cp.producto_id = p.producto_id " +
                     "GROUP BY t.comprador_id, t.carrito_id";

        Connection con = null;
        Statement stmt = null;
        ResultSet rslt = null;

        try {
            con = acc.getConnection();
            stmt = con.createStatement();
            rslt = stmt.executeQuery(sen);

            ArrayList<String[]> lista = new ArrayList<>();
            while (rslt.next()) {
                String[] fila = new String[3];
                fila[0] = rslt.getString(1);
                fila[1] = rslt.getString(2) != null ? rslt.getString(2) : "Sin empleado";
                fila[2] = String.format("%.2f", rslt.getDouble(3));
                lista.add(fila);
            }

            return lista.toArray(new String[0][]);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
        } finally {
            try {
                if (rslt != null) rslt.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new String[0][0];
    }
    
    public String[][] selectTransaccionesCons(String[] consulta) {
        String sen = "SELECT u_comp.nombre, u_emp.nombre, " +
                     "SUM(p.precio * cp.cantidad_p) " +
                     "FROM transacciones t " +
                     "JOIN usuario u_comp ON t.comprador_id = u_comp.usuario_id " +
                     "LEFT JOIN usuario u_emp ON t.empleado_id = u_emp.usuario_id " +
                     "JOIN carrito_producto cp ON t.carrito_id = cp.carrito_id " +
                     "JOIN producto p ON cp.producto_id = p.producto_id " +
                     "WHERE 1=1 ";

        if (consulta != null && consulta[0] != null) {
            sen += "AND u_comp.nombre LIKE ? ";
        }
        if (consulta != null && consulta[1] != null) {
            sen += "AND u_emp.nombre LIKE ? ";
        }

        sen += "GROUP BY t.comprador_id, t.carrito_id";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rslt = null;

        try {
            con = acc.getConnection();
            pstmt = con.prepareStatement(sen);

            int param = 1;
            if (consulta != null && consulta[0] != null) {
                pstmt.setString(param++, consulta[0] + "%");
            }
            if (consulta != null && consulta[1] != null) {
                pstmt.setString(param++, consulta[1] + "%");
            }

            rslt = pstmt.executeQuery();

            ArrayList<String[]> lista = new ArrayList<>();
            while (rslt.next()) {
                String[] fila = new String[3];
                fila[0] = rslt.getString(1);
                fila[1] = rslt.getString(2) != null ? rslt.getString(2) : "Sin empleado";
                fila[2] = String.format("%.2f", rslt.getDouble(3));
                lista.add(fila);
            }

            return lista.toArray(new String[0][]);

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
        return new String[0][0];
    }
}