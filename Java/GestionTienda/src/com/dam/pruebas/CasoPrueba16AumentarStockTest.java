package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableProductoDAO;
import com.dam.model.pojos.Producto;

public class CasoPrueba16AumentarStockTest {

    private static final String NOMBRE       = "Producto A";
    private static final int    STOCK_INICIAL = 50;
    private TableProductoDAO productoDAO;
    private int productoId = -1;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProducto(NOMBRE);
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, STOCK_INICIAL, "Descripción inicial"));
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        if (!lista.isEmpty()) {
            productoId = lista.get(0).getId();
        }
    }

    @After
    public void tearDown() {
        limpiarProducto(NOMBRE);
    }

    @Test
    public void testStockInicialEsCorrecto() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock inicial debe ser 50", STOCK_INICIAL, lista.get(0).getStock());
    }

    @Test
    public void testAumentarStockIncrementa20Unidades() {
        assertNotEquals(-1, productoId);
        productoDAO.incrementarStock(productoId, 20);
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertEquals("El stock debe haberse incrementado en 20", STOCK_INICIAL + 20, lista.get(0).getStock());
    }

    @Test
    public void testStockIncrementadoEsMayorQueElInicial() {
        assertNotEquals(-1, productoId);
        productoDAO.incrementarStock(productoId, 20);
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertTrue("El stock incrementado debe ser mayor que el inicial", lista.get(0).getStock() > STOCK_INICIAL);
    }

    private void limpiarProducto(String nombre) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM producto WHERE nombre = ?");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia limpieza CP16: " + e.getMessage());
        }
    }
}
