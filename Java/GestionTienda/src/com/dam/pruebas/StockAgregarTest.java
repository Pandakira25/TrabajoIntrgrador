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


public class StockAgregarTest {

    private static final String NOMBRE_TEST   = "Test_RF9_StockAgregar";
    private static final int    STOCK_INICIAL = 50;
    private TableProductoDAO productoDAO;
    private int productoId;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProductos(NOMBRE_TEST);

        Producto p = new Producto(NOMBRE_TEST, "Test", 9.99, STOCK_INICIAL, "Producto para prueba de stock RF9");
        productoDAO.insertProducto(p);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse("El producto de prueba debe haberse creado en setUp", lista.isEmpty());
        productoId = lista.get(0).getId();
    }

    @After
    public void tearDown() {
        limpiarProductos(NOMBRE_TEST);
    }

    @Test
    public void testIncrementarStockAumentaElValor() {
        int incremento = 25;
        productoDAO.incrementarStock(productoId, incremento);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock debe haber aumentado en " + incremento,
                     STOCK_INICIAL + incremento, lista.get(0).getStock());
    }

    @Test
    public void testIncrementarStockVariasVecesAcumula() {
        productoDAO.incrementarStock(productoId, 10);
        productoDAO.incrementarStock(productoId, 15);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("Los incrementos sucesivos deben acumularse correctamente",
                     STOCK_INICIAL + 25, lista.get(0).getStock());
    }

    @Test
    public void testIncrementarStockConCeroNoModifica() {
        productoDAO.incrementarStock(productoId, 0);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("Un incremento de 0 no debe modificar el stock",
                     STOCK_INICIAL, lista.get(0).getStock());
    }

    @Test
    public void testStockInicialEsCorrecto() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock inicial del producto de prueba debe ser " + STOCK_INICIAL,
                     STOCK_INICIAL, lista.get(0).getStock());
    }

    private void limpiarProductos(String nombre) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM producto WHERE nombre = ?");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia: error en limpieza RF9 - " + e.getMessage());
        }
    }
}
