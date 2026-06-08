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

public class CasoPrueba22ReducirCantidadCarritoTest {

    private static final int COMPRADOR_ID = 4;
    private static final int PRODUCTO_ID  = 1;
    private TableCarritoDAO carritoDAO;
    private TableCarritoProductoDAO carritoProductoDAO;
    private int carritoId;

    @Before
    public void setUp() {
        carritoDAO = new TableCarritoDAO();
        carritoProductoDAO = new TableCarritoProductoDAO();
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        assertTrue("El carrito debe crearse correctamente", carritoId > 0);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 2);
    }

    @After
    public void tearDown() {
        limpiarCarrito(carritoId);
    }

    @Test
    public void testCantidadInicialEsDos() throws Exception {
        assertEquals("La cantidad inicial debe ser 2", 2, getCantidadEnCarrito(carritoId, PRODUCTO_ID));
    }

    @Test
    public void testReducirCantidadActualizaElValor() throws Exception {
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 1);
        assertEquals("La cantidad debe reducirse a 1", 1, getCantidadEnCarrito(carritoId, PRODUCTO_ID));
    }

    @Test
    public void testCuandoCantidadLlegaACeroSeEliminaElProducto() throws Exception {
        carritoProductoDAO.eliminarProducto(carritoId, PRODUCTO_ID);
        assertFalse("Cuando la cantidad llega a 0 el producto debe eliminarse del carrito",
                    existeProductoEnCarrito(carritoId, PRODUCTO_ID));
    }

    @Test
    public void testProductoExistiaEnCarritoAntesDeEliminar() throws Exception {
        assertTrue("El producto debe estar en el carrito antes de ser eliminado",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID));
    }

    private int getCantidadEnCarrito(int cId, int pId) throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "SELECT cantidad_p FROM carrito_producto WHERE carrito_id = ? AND producto_id = ?");
        ps.setInt(1, cId);
        ps.setInt(2, pId);
        ResultSet rs = ps.executeQuery();
        int cant = rs.next() ? rs.getInt(1) : 0;
        rs.close(); ps.close(); con.close();
        return cant;
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
            System.err.println("Advertencia limpieza CP22: " + e.getMessage());
        }
    }
}
