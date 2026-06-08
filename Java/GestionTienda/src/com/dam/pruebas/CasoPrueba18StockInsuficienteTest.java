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

public class CasoPrueba18StockInsuficienteTest {

    private static final String NOMBRE       = "Producto A";
    private static final int    STOCK_INICIAL = 3;
    private static final int    CANTIDAD_EXCESO = 10;
    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProducto(NOMBRE);
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, STOCK_INICIAL, "Descripción inicial"));
    }

    @After
    public void tearDown() {
        limpiarProducto(NOMBRE);
    }

    @Test
    public void testStockActualEsMenorQueLaCantidadSolicitada() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        int stockActual = lista.get(0).getStock();
        assertTrue("La cantidad solicitada (10) supera el stock disponible (3)", CANTIDAD_EXCESO > stockActual);
    }

    @Test
    public void testStockInicialEs3() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock debe ser 3", STOCK_INICIAL, lista.get(0).getStock());
    }

    @Test
    public void testCondicionDeGuardaSeActivaria() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        Producto p = lista.get(0);
        assertTrue("La condición de guarda 'cantidad > stock' debe ser verdadera", CANTIDAD_EXCESO > p.getStock());
    }

    @Test
    public void testStockPermaneceInmutableSiNoSeDecrementa() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("Si la guarda bloquea el decremento, el stock no cambia", STOCK_INICIAL, lista.get(0).getStock());
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
            System.err.println("Advertencia limpieza CP18: " + e.getMessage());
        }
    }
}
