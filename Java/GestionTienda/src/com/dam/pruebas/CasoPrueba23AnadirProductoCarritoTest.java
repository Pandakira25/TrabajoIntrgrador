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

public class CasoPrueba23AnadirProductoCarritoTest {

    private static final int COMPRADOR_ID = 4;
    private static final int PRODUCTO_ID  = 5;
    private TableCarritoDAO carritoDAO;
    private TableCarritoProductoDAO carritoProductoDAO;
    private int carritoId = -1;

    @Before
    public void setUp() {
        carritoDAO = new TableCarritoDAO();
        carritoProductoDAO = new TableCarritoProductoDAO();
    }

    @After
    public void tearDown() {
        if (carritoId > 0) {
            limpiarCarrito(carritoId);
        }
    }

    @Test
    public void testCrearCarritoSiNoExisteUnoActivo() {
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        assertTrue("El carrito debe crearse con un ID mayor que 0", carritoId > 0);
    }

    @Test
    public void testAnadirProductoLoRegistraEnElCarrito() throws Exception {
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 1);
        assertTrue("El producto debe aparecer en el carrito tras ser añadido",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID));
    }

    @Test
    public void testCantidadDelProductoAnadidoEsCorrecta() throws Exception {
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 2);
        assertEquals("La cantidad del producto en el carrito debe ser 2", 2, getCantidadEnCarrito(carritoId, PRODUCTO_ID));
    }

    @Test
    public void testGetCarritoActivoDevuelveCarritoRecienCreado() {
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        int activo = carritoDAO.getCarritoActivo(COMPRADOR_ID);
        assertEquals("El carrito activo debe ser el recién creado", carritoId, activo);
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
            System.err.println("Advertencia limpieza CP23: " + e.getMessage());
        }
    }
}
