package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase Data Access Object (DAO) para la gestión de la tabla de transacciones (`TableTransaccionesDAO`).
 * <p>
 * Se encarga de la persistencia e interacción directa con la base de datos para registrar las 
 * compras de los carritos de los usuarios. Ofrece funciones analíticas avanzadas para recuperar el histórico 
 * consolidado de transacciones mediante agrupaciones lógicas y cálculos de importes totales acumulados, 
 * permitiendo además la aplicación de filtros parametrizados por los nombres de los compradores o empleados.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class TableTransaccionesDAO {

    /** Instancia de la clase de configuración de propiedades y conexiones de la base de datos. */
    private AccessDBProp acc;

    /** Nombre de la tabla de transacciones comerciales en la base de datos. */
    private static final String NOM_TAB = "transacciones";
    /** Nombre de la columna que hace referencia al identificador único del comprador. */
    private static final String COL_COMPRADOR_ID = "comprador_id";
    /** Nombre de la columna que hace referencia al identificador único del carrito de la compra. */
    private static final String COL_CARRITO_ID = "carrito_id";
    /** Nombre de la columna que hace referencia al identificador único del empleado que asiste la transacción. */
    private static final String COL_EMPLEADO_ID = "empleado_id";

    /**
     * Constructor por defecto de la clase.
     * Inicializa el objeto que otorga el acceso a las propiedades de conexión.
     */
    public TableTransaccionesDAO() {
        acc = new AccessDBProp();
    }

    /**
     * Da de alta un nuevo registro de transacción (formalización de compra) en la base de datos.
     * <p>
     * Se encarga de tratar de manera diferencial el identificador del empleado mediante el envoltorio 
     * {@link Integer}, de tal forma que si el argumento se recibe nulo se almacena explícitamente 
     * un valor {@code NULL} en el motor relacional utilizando {@link java.sql.Types#INTEGER}.
     * </p>
     * * @param compradorId Identificador numérico único del comprador.
     * @param carritoId   Identificador numérico único del carrito a procesar.
     * @param empleadoId  Identificador numérico único del empleado (puede ser {@code null} si la compra es directa).
     * @return Una cadena alfabética informativa con el resultado textual del éxito o fallo de la inserción.
     */
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
    
    /**
     * Extrae el listado completo e histórico de transacciones comerciales registradas en la plataforma.
     * <p>
     * Realiza un acoplamiento múltiple de tablas mediante sentencias `JOIN` y un `LEFT JOIN` (para prever 
     * compras sin asistencia de empleados), agrupando los registros por comprador y carrito con el fin 
     * de calcular la sumatoria monetaria total multiplicando los precios unitarios por las unidades agregadas.
     * </p>
     * * @return Una matriz bidimensional de cadenas de texto ({@code String[][]}) donde cada fila indexada 
     * contiene: `[Nombre Comprador, Nombre Empleado / "Sin empleado", Monto Total Formateado]`.
     */
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
    
    /**
     * Extrae un listado filtrado de transacciones históricas aplicando criterios de coincidencia parcial (LIKE).
     * <p>
     * Reutiliza la lógica de agregación y cálculo de importes económicos agregando dinámicamente cláusulas 
     * adicionales de restricción en base al contenido del array unidimensional provisto por parámetro.
     * </p>
     * * @param consulta Un array indexado de cadenas de texto donde `consulta[0]` representa el nombre o patrón 
     * del comprador, y `consulta[1]` el nombre o patrón del empleado. Los elementos pueden ser nulos.
     * @return Una matriz bidimensional de cadenas de texto ({@code String[][]}) con la información estructurada 
     * de las transacciones que cumplen con los filtros indicados.
     */
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