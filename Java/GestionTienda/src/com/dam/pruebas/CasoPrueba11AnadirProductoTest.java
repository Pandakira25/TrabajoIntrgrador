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

public class CasoPrueba11AnadirProductoTest {

    private static final String NOMBRE = "Producto A";
    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProducto(NOMBRE);
    }

    @After
    public void tearDown() {
        limpiarProducto(NOMBRE);
    }

    @Test
    public void testAnadirProductoRetornaMensajeExito() {
        String resultado = productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
        assertEquals("Producto agregado con éxito", resultado);
    }

    @Test
    public void testProductoAnadidoApareceEnCatalogo() {
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse("El producto añadido debe aparecer en el catálogo", lista.isEmpty());
    }

    @Test
    public void testProductoAnadidoEstaActivo() {
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE, null, null, true);
        assertFalse("El producto recién añadido debe estar activo", activos.isEmpty());
    }

    @Test
    public void testProductoTieneStockCorrecto() {
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock inicial debe ser 50", 50, lista.get(0).getStock());
    }

    @Test
    public void testProductoTienePrecioCorrecto() {
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El precio debe ser 10.00", 10.00, lista.get(0).getPrecio(), 0.01);
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
            System.err.println("Advertencia limpieza CP11: " + e.getMessage());
        }
    }
}
