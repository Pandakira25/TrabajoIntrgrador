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

public class StockEliminarTest {

    private static final String NOMBRE_TEST   = "Test_RF10_StockEliminar";
    private static final int    STOCK_INICIAL = 100;
    private TableProductoDAO productoDAO;
    private int productoId;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProductos(NOMBRE_TEST);

        Producto p = new Producto(NOMBRE_TEST, "Test", 9.99, STOCK_INICIAL, "Producto para prueba de stock RF10");
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
    public void testDecrementarStockReduceElValor() {
        int decremento = 30;
        productoDAO.decrementarStock(productoId, decremento);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock debe haber disminuido en " + decremento,
                     STOCK_INICIAL - decremento, lista.get(0).getStock());
    }

    @Test
    public void testDecrementarStockVariasVecesAcumula() {
        productoDAO.decrementarStock(productoId, 20);
        productoDAO.decrementarStock(productoId, 30);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("Los decrementos sucesivos deben acumularse correctamente",
                     STOCK_INICIAL - 50, lista.get(0).getStock());
    }

    @Test
    public void testDecrementarStockConCeroNoModifica() {
        productoDAO.decrementarStock(productoId, 0);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("Un decremento de 0 no debe modificar el stock",
                     STOCK_INICIAL, lista.get(0).getStock());
    }

    @Test
    public void testDecrementarTodoElStock() {
        productoDAO.decrementarStock(productoId, STOCK_INICIAL);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock debe ser 0 tras decrementar el total", 0, lista.get(0).getStock());
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
            System.err.println("Advertencia: error en limpieza RF10 - " + e.getMessage());
        }
    }
}
