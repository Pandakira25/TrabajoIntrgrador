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

public class CasoPrueba20BusquedaProductoTiendaTest {

    private static final String NOMBRE = "Producto A";
    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProducto(NOMBRE);
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
    }

    @After
    public void tearDown() {
        limpiarProducto(NOMBRE);
    }

    @Test
    public void testBusquedaEnTiendaSoloDevuelveProductosActivos() {
        ArrayList<Producto> lista = productoDAO.selectProductos(null, null, null, true);
        assertFalse("La tienda debe tener productos activos", lista.isEmpty());
        for (Producto p : lista) {
            assertTrue("Solo deben mostrarse productos activos en la tienda", p.isActivo());
        }
    }

    @Test
    public void testBuscarProductoActivoPorNombre() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, true);
        assertFalse("El producto activo debe encontrarse en la tienda", lista.isEmpty());
        assertEquals(NOMBRE, lista.get(0).getNombre());
    }

    @Test
    public void testBuscarProductoActivoPorCategoria() {
        ArrayList<Producto> lista = productoDAO.selectProductos(null, null, "Electrónica", true);
        assertFalse("La tienda debe tener productos activos en la categoría Electrónica", lista.isEmpty());
        for (Producto p : lista) {
            assertEquals("Electrónica", p.getCategoria());
        }
    }

    @Test
    public void testBuscarPorNombreYCategoriaFiltracorrectamente() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, "Electrónica", true);
        assertFalse(lista.isEmpty());
        assertEquals(NOMBRE, lista.get(0).getNombre());
        assertEquals("Electrónica", lista.get(0).getCategoria());
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
            System.err.println("Advertencia limpieza CP20: " + e.getMessage());
        }
    }
}
