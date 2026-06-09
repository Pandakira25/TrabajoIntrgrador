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

public class CasoPrueba14DeshabilitarProductoTest {

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
    public void testDeshabilitarProductoRetornaMensajeExito() {
        String resultado = productoDAO.disableProd(NOMBRE);
        assertEquals("Se ha deshabilitado el producto con éxito", resultado);
    }

    @Test
    public void testProductoDeshabilitadoNoApareceEnCatalogoActivo() {
        productoDAO.disableProd(NOMBRE);
        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE, null, null, true);
        assertTrue("El producto deshabilitado no debe aparecer en el catálogo activo", activos.isEmpty());
    }

    @Test
    public void testProductoEstabaActivoAntesDeDeshabilitarse() {
        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE, null, null, true);
        assertFalse("El producto debe estar activo antes de ser deshabilitado", activos.isEmpty());
    }

    @Test
    public void testProductoDeshabilitadoTieneActivoFalse() {
        productoDAO.disableProd(NOMBRE);
        ArrayList<Producto> todos = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(todos.isEmpty());
        assertFalse("El campo activo debe ser false tras deshabilitar", todos.get(0).isActivo());
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
            System.err.println("Advertencia limpieza CP14: " + e.getMessage());
        }
    }
}
