package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase Data Access Object (DAO) para la gestión de la tabla intermedia de productos del carrito (`TableCarritoProductoDAO`).
 * <p>
 * Centraliza la persistencia e interacción con la base de datos para la relación de muchos a muchos 
 * entre Carritos y Productos. Ofrece la capacidad de guardar, actualizar la cantidad (operación upsert) 
 * y remover artículos vinculados a un carrito de compra activo.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class TableCarritoProductoDAO {

    /** Instancia encargada de gestionar los parámetros de configuración y conexiones de la base de datos. */
    private AccessDBProp acc;

    /** Nombre de la tabla relacional intermedia en la base de datos. */
    private static final String NOM_TAB = "carrito_producto";
    /** Nombre de la columna de clave foránea que referencia al identificador único del carrito. */
    private static final String COL_CARRITO_ID = "carrito_id";
    /** Nombre de la columna de clave foránea que referencia al identificador único del producto. */
    private static final String COL_PRODUCTO_ID = "producto_id";
    /** Nombre de la columna que almacena la cantidad seleccionada de un mismo artículo. */
    private static final String COL_CANTIDAD_P = "cantidad_p";

    /**
     * Constructor por defecto de la clase.
     * Inicializa el objeto que da acceso a las propiedades de conexión.
     */
    public TableCarritoProductoDAO() {
        acc = new AccessDBProp();
    }

    /**
     * Inserta o actualiza la cantidad de un artículo específico dentro de un carrito (Operación Upsert).
     * <p>
     * Evalúa inicialmente mediante una consulta de selección si el vínculo entre el carrito 
     * y el producto ya existe. En caso afirmativo, procede a ejecutar un {@code UPDATE} modificando 
     * el campo de cantidad; en caso negativo, ejecuta un {@code INSERT} para añadir el nuevo registro.
     * </p>
     * * @param carritoId  Identificador numérico único del carrito.
     * @param productoId Identificador numérico único del producto.
     * @param cantidad   Unidades deseadas finales que se registrarán para este producto.
     */
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

    /**
     * Elimina por completo la asociación física de un artículo específico dentro de un carrito de compras.
     * <p>
     * Ejecuta una instrucción estructural de tipo {@code DELETE} filtrando bajo el criterio concurrente 
     * de ambos identificadores únicos de fila.
     * </p>
     * * @param carritoId  Identificador numérico único del carrito destino.
     * @param productoId Identificador numérico único del producto a retirar.
     */
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