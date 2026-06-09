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

public class CasoPrueba19VerDescripcionProductoTest {

    private static final String NOMBRE = "Producto A";
    private static final String DESCRIPCION = "Descripción del Producto A para test";
    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProducto(NOMBRE);
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, DESCRIPCION));
    }

    @After
    public void tearDown() {
        limpiarProducto(NOMBRE);
    }

    @Test
    public void testDescripcionNoEsNull() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertNotNull("La descripción del producto no debe ser null", lista.get(0).getDescripcion());
    }

    @Test
    public void testDescripcionEsLaEsperada() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("La descripción debe coincidir con la insertada", DESCRIPCION, lista.get(0).getDescripcion());
    }

    @Test
    public void testProductoTieneNombreYCategoria() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        Producto p = lista.get(0);
        assertNotNull("El nombre no debe ser null", p.getNombre());
        assertNotNull("La categoría no debe ser null", p.getCategoria());
        assertTrue("El precio debe ser mayor que 0", p.getPrecio() > 0);
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
            System.err.println("Advertencia limpieza CP19: " + e.getMessage());
        }
    }
}
