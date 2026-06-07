package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableCarritoDAO;
import com.dam.model.acessbd.TableCarritoProductoDAO;


public class CarritoEliminarProductoTest {

    private static final int COMPRADOR_ID  = 4; // "comprador" del init.sql
    private static final int PRODUCTO_ID_A = 3; // Kindle Paperwhite
    private static final int PRODUCTO_ID_B = 2; // Sony WH-1000XM5

    private TableCarritoDAO carritoDAO;
    private TableCarritoProductoDAO carritoProductoDAO;
    private int carritoId;

    @Before
    public void setUp() {
        carritoDAO = new TableCarritoDAO();
        carritoProductoDAO = new TableCarritoProductoDAO();
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        assertTrue("El carrito de prueba debe crearse correctamente en setUp", carritoId > 0);
       
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, 1);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_B, 2);
    }

    @After
    public void tearDown() {
        limpiarCarrito(carritoId);
    }

    @Test
    public void testEliminarProductoDelCarrito() throws Exception {
        assertTrue("El producto debe estar en el carrito antes de eliminarlo",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
        carritoProductoDAO.eliminarProducto(carritoId, PRODUCTO_ID_A);
        assertFalse("El producto debe haberse eliminado del carrito",
                    existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
    }

    @Test
    public void testEliminarUnProductoNoAfectaAlOtro() throws Exception {
        carritoProductoDAO.eliminarProducto(carritoId, PRODUCTO_ID_A);
        assertFalse("El producto A debe haberse eliminado",
                    existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
        assertTrue("El producto B debe seguir en el carrito",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID_B));
    }

    @Test
    public void testEliminarTodosLosProductosDejaCarritoVacio() throws Exception {
        carritoProductoDAO.eliminarProducto(carritoId, PRODUCTO_ID_A);
        carritoProductoDAO.eliminarProducto(carritoId, PRODUCTO_ID_B);
        assertFalse(existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
        assertFalse(existeProductoEnCarrito(carritoId, PRODUCTO_ID_B));
    }

    @Test
    public void testEliminarProductoYReagregarloEsValido() throws Exception {
        carritoProductoDAO.eliminarProducto(carritoId, PRODUCTO_ID_A);
        assertFalse(existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, 3);
        assertTrue("El producto debe poder volver a añadirse tras ser eliminado",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
    }

    
    private boolean existeProductoEnCarrito(int cId, int pId) throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM carrito_producto WHERE carrito_id = ? AND producto_id = ?");
        ps.setInt(1, cId);
        ps.setInt(2, pId);
        ResultSet rs = ps.executeQuery();
        boolean existe = rs.next() && rs.getInt(1) > 0;
        rs.close();
        ps.close();
        con.close();
        return existe;
    }

    private void limpiarCarrito(int cId) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM carrito_producto WHERE carrito_id = ?");
            ps.setInt(1, cId);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("DELETE FROM carrito WHERE carrito_id = ?");
            ps.setInt(1, cId);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia: error en limpieza RF8 - " + e.getMessage());
        }
    }
}
