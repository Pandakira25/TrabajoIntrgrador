package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase Data Access Object (DAO) para la gestión de la tabla de carritos (`TableCarritoDAO`).
 * <p>
 * Se encarga de centralizar las operaciones de persistencia e interacción directa con la base 
 * de datos para la entidad Carrito. Permite dar de alta nuevos carritos y recuperar de forma 
 * analítica el identificador del carrito activo de un comprador específico.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class TableCarritoDAO {

    /** Instancia de la clase de configuración de propiedades y conexiones de la base de datos. */
    private AccessDBProp acc;

    /** Nombre de la tabla principal de almacenamiento de carritos en la base de datos. */
    private static final String NOM_TAB = "carrito";
    /** Nombre de la tabla de transacciones utilizada para verificar el estado de los carritos. */
    private static final String NOM_TAB_TRANS = "transacciones";
    /** Nombre de la columna que representa el identificador único del carrito. */
    private static final String COL_CARRITO_ID = "carrito_id";
    /** Nombre de la columna que representa el identificador único del comprador asociado. */
    private static final String COL_COMPRADOR_ID = "comprador_id";

    /**
     * Constructor por defecto de la clase.
     * Inicializa el objeto de propiedades de acceso a la base de datos.
     */
    public TableCarritoDAO() {
        acc = new AccessDBProp();
    }

    /**
     * Inserta un nuevo registro de carrito para un comprador específico en la base de datos.
     * <p>
     * Modifica el estado de la persistencia mediante un {@link PreparedStatement} configurado 
     * para retornar la clave primaria auto-generada tras una inserción exitosa.
     * </p>
     * * @param compradorId Identificador numérico único del comprador.
     * @return El identificador numérico (ID) del nuevo carrito generado si la inserción fue exitosa; 
     * u {@code -1} en caso de error o si no se pudieron recuperar las claves autogeneradas.
     */
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

    /**
     * Recupera el identificador del carrito activo perteneciente a un comprador.
     * <p>
     * Un carrito se considera "activo" si está asociado al ID del comprador y su propio ID 
     * no figura todavía en ninguna subconsulta de la tabla de transacciones (es decir, no ha sido comprado).
     * El resultado se encuentra acotado a un único registro mediante la cláusula LIMIT.
     * </p>
     * * @param compradorId Identificador numérico único del comprador de consulta.
     * @return El ID del carrito activo encontrado en el {@link ResultSet}; 
     * u {@code -1} si no existe ningún carrito activo para el usuario o si se produce una excepción.
     */
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