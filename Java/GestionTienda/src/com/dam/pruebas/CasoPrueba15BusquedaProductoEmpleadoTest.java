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

public class CasoPrueba15BusquedaProductoEmpleadoTest {

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
    public void testBuscarPorNombreDevuelveProducto() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse("La búsqueda por nombre debe encontrar el producto", lista.isEmpty());
    }

    @Test
    public void testBuscarPorCategoriaDevuelveProducto() {
        ArrayList<Producto> lista = productoDAO.selectProductos(null, null, "Electrónica", false);
        assertFalse("La búsqueda por categoría debe encontrar el producto", lista.isEmpty());
    }

    @Test
    public void testBuscarPorNombreYCategoriaDevuelveProductoCorrecto() {
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, "Electrónica", false);
        assertFalse(lista.isEmpty());
        assertEquals("El nombre del producto debe coincidir", NOMBRE, lista.get(0).getNombre());
        assertEquals("La categoría del producto debe coincidir", "Electrónica", lista.get(0).getCategoria());
    }

    @Test
    public void testBuscarPorNombreInexistenteDevuelveListaVacia() {
        ArrayList<Producto> lista = productoDAO.selectProductos("ProductoXYZ999", null, null, false);
        assertTrue("Una búsqueda sin coincidencias debe devolver lista vacía", lista.isEmpty());
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
            System.err.println("Advertencia limpieza CP15: " + e.getMessage());
        }
    }
}
