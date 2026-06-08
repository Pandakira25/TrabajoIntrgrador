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

public class ProductoAltaTest {

    private static final String NOMBRE_TEST = "Test_RF1_Alta";
    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProductos(NOMBRE_TEST);
    }

    @After
    public void tearDown() {
        limpiarProductos(NOMBRE_TEST);
    }

    @Test
    public void testInsertarProductoRetornaMensajeExito() {
        Producto p = new Producto(NOMBRE_TEST, "Electronica", 99.99, 10, "Producto de prueba para RF1");
        String resultado = productoDAO.insertProducto(p);
        assertEquals("Producto agregado con éxito", resultado);
    }

    @Test
    public void testProductoInsertadoEsRecuperable() {
        Producto p = new Producto(NOMBRE_TEST, "Electronica", 99.99, 10, "Producto de prueba para RF1");
        productoDAO.insertProducto(p);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse("El producto debe aparecer en la consulta tras el alta", lista.isEmpty());
        assertEquals(NOMBRE_TEST, lista.get(0).getNombre());
    }

    @Test
    public void testProductoInsertadoEstaActivoPorDefecto() {
        Producto p = new Producto(NOMBRE_TEST, "Electronica", 99.99, 10, "Producto de prueba para RF1");
        productoDAO.insertProducto(p);

        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE_TEST, null, null, true);
        assertFalse("El producto recién insertado debe estar activo", activos.isEmpty());
    }

    @Test
    public void testProductoInsertadoTieneDatosCorrectos() {
        Producto p = new Producto(NOMBRE_TEST, "Electronica", 99.99, 10, "Producto de prueba para RF1");
        productoDAO.insertProducto(p);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse(lista.isEmpty());
        Producto recuperado = lista.get(0);
        assertEquals("Electronica", recuperado.getCategoria());
        assertEquals(99.99, recuperado.getPrecio(), 0.01);
        assertEquals(10, recuperado.getStock());
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
            System.err.println("Advertencia: error en limpieza RF1 - " + e.getMessage());
        }
    }
}
