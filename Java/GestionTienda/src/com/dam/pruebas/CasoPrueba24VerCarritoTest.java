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

public class CasoPrueba24VerCarritoTest {

    private static final int COMPRADOR_ID  = 4;
    private static final int PRODUCTO_ID_A = 1;
    private static final int PRODUCTO_ID_B = 3;
    private TableCarritoDAO carritoDAO;
    private TableCarritoProductoDAO carritoProductoDAO;
    private int carritoId;

    @Before
    public void setUp() {
        carritoDAO = new TableCarritoDAO();
        carritoProductoDAO = new TableCarritoProductoDAO();
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        assertTrue("El carrito debe crearse correctamente", carritoId > 0);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, 2);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_B, 1);
    }

    @After
    public void tearDown() {
        limpiarCarrito(carritoId);
    }

    @Test
    public void testCarritoActivoEsRecuperable() {
        int activo = carritoDAO.getCarritoActivo(COMPRADOR_ID);
        assertTrue("Debe existir un carrito activo para el comprador", activo > 0);
    }

    @Test
    public void testProductoAAparecenEnElCarrito() throws Exception {
        assertTrue("El producto A debe estar en el carrito", existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
    }

    @Test
    public void testProductoBAparecenEnElCarrito() throws Exception {
        assertTrue("El producto B debe estar en el carrito", existeProductoEnCarrito(carritoId, PRODUCTO_ID_B));
    }

    @Test
    public void testCarritoTieneExactamenteDosProductos() throws Exception {
        assertEquals("El carrito debe tener exactamente 2 productos", 2, contarProductosEnCarrito(carritoId));
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
        rs.close(); ps.close(); con.close();
        return existe;
    }

    private int contarProductosEnCarrito(int cId) throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM carrito_producto WHERE carrito_id = ?");
        ps.setInt(1, cId);
        ResultSet rs = ps.executeQuery();
        int count = rs.next() ? rs.getInt(1) : 0;
        rs.close(); ps.close(); con.close();
        return count;
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
            System.err.println("Advertencia limpieza CP24: " + e.getMessage());
        }
    }
}
