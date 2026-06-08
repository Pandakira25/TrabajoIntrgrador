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

public class CasoPrueba17ReducirStockTest {

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
    public void testReducirStockDecrementa5Unidades() {
        assertNotEquals(-1, productoId);
        productoDAO.decrementarStock(productoId, 5);
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertEquals("El stock debe haberse reducido en 5", STOCK_INICIAL - 5, lista.get(0).getStock());
    }

    @Test
    public void testStockReducidoEsMenorQueElInicial() {
        assertNotEquals(-1, productoId);
        productoDAO.decrementarStock(productoId, 5);
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertTrue("El stock reducido debe ser menor que el inicial", lista.get(0).getStock() < STOCK_INICIAL);
    }

    @Test
    public void testStockSuficientePermiteReduccion() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertTrue("El stock es suficiente para reducir 5 unidades", lista.get(0).getStock() >= 5);
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
            System.err.println("Advertencia limpieza CP17: " + e.getMessage());
        }
    }
}
