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

public class CasoPrueba13HabilitarProductoTest {

    private static final String NOMBRE = "Producto A";
    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProducto(NOMBRE);
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
        productoDAO.disableProd(NOMBRE);
    }

    @After
    public void tearDown() {
        limpiarProducto(NOMBRE);
    }

    @Test
    public void testHabilitarProductoRetornaMensajeExito() {
        String resultado = productoDAO.enableProd(NOMBRE);
        assertEquals("Se ha habilitado el producto con éxito", resultado);
    }

    @Test
    public void testProductoHabilitadoApareceEnCatalogoActivo() {
        productoDAO.enableProd(NOMBRE);
        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE, null, null, true);
        assertFalse("El producto habilitado debe aparecer en el catálogo activo", activos.isEmpty());
    }

    @Test
    public void testProductoEstabaInactivoAntesDeHabilitar() {
        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE, null, null, true);
        assertTrue("El producto debe estar inactivo antes de ser habilitado", activos.isEmpty());
    }

    @Test
    public void testProductoHabilitadoTieneActivoTrue() {
        productoDAO.enableProd(NOMBRE);
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertTrue("El campo activo debe ser true tras habilitar", lista.get(0).isActivo());
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
            System.err.println("Advertencia limpieza CP13: " + e.getMessage());
        }
    }
}
