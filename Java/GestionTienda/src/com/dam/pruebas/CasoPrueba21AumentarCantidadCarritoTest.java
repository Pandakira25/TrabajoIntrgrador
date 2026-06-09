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

public class CasoPrueba21AumentarCantidadCarritoTest {

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
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 1);
    }

    @After
    public void tearDown() {
        limpiarCarrito(carritoId);
    }

    @Test
    public void testCantidadInicialEsUno() throws Exception {
        assertEquals("La cantidad inicial debe ser 1", 1, getCantidadEnCarrito(carritoId, PRODUCTO_ID));
    }

    @Test
    public void testAumentarCantidadActualizaElValor() throws Exception {
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 3);
        assertEquals("La cantidad debe actualizarse a 3 tras el aumento", 3, getCantidadEnCarrito(carritoId, PRODUCTO_ID));
    }

    @Test
    public void testCantidadAumentadaEsMayorQueInicial() throws Exception {
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 3);
        assertTrue("La cantidad aumentada debe ser mayor que la inicial", getCantidadEnCarrito(carritoId, PRODUCTO_ID) > 1);
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
            System.err.println("Advertencia limpieza CP21: " + e.getMessage());
        }
    }
}
