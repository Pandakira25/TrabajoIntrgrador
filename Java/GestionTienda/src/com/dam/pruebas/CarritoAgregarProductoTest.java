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

public class CarritoAgregarProductoTest {

    
    private static final int COMPRADOR_ID  = 4;
    private static final int PRODUCTO_ID_A = 2; // Sony WH-1000XM5
    private static final int PRODUCTO_ID_B = 3; // Kindle Paperwhite

    private TableCarritoDAO carritoDAO;
    private TableCarritoProductoDAO carritoProductoDAO;
    private int carritoId;

    @Before
    public void setUp() {
        carritoDAO = new TableCarritoDAO();
        carritoProductoDAO = new TableCarritoProductoDAO();
        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        assertTrue("El carrito de prueba debe crearse correctamente en setUp", carritoId > 0);
    }

    @After
    public void tearDown() {
        limpiarCarrito(carritoId);
    }

    @Test
    public void testCarritoCreado() {
        assertTrue("El id del carrito debe ser mayor que 0", carritoId > 0);
    }

    @Test
    public void testAgregarProductoApareceEnCarrito() throws Exception {
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, 1);
        assertTrue("El producto debe existir en el carrito tras ser agregado",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
    }

    @Test
    public void testAgregarProductoConCantidadCorrecta() throws Exception {
        int cantidad = 3;
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, cantidad);
        assertEquals("La cantidad almacenada debe coincidir con la solicitada",
                     cantidad, getCantidadEnCarrito(carritoId, PRODUCTO_ID_A));
    }

    @Test
    public void testAgregarVariosProductosAlMismoCarrito() throws Exception {
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, 1);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_B, 2);

        assertTrue("El primer producto debe estar en el carrito",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID_A));
        assertTrue("El segundo producto debe estar en el carrito",
                   existeProductoEnCarrito(carritoId, PRODUCTO_ID_B));
    }

    @Test
    public void testActualizarCantidadDeProductoEnCarrito() throws Exception {
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, 1);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID_A, 5); // upsert actualiza la cantidad
        assertEquals("La cantidad debe actualizarse al hacer upsert sobre un producto ya existente",
                     5, getCantidadEnCarrito(carritoId, PRODUCTO_ID_A));
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

    private int getCantidadEnCarrito(int cId, int pId) throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "SELECT cantidad_p FROM carrito_producto WHERE carrito_id = ? AND producto_id = ?");
        ps.setInt(1, cId);
        ps.setInt(2, pId);
        ResultSet rs = ps.executeQuery();
        int cantidad = rs.next() ? rs.getInt(1) : 0;
        rs.close();
        ps.close();
        con.close();
        return cantidad;
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
            System.err.println("Advertencia: error en limpieza RF7 - " + e.getMessage());
        }
    }
}
