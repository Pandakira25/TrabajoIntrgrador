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

public class CasoPrueba12ModificarProductoTest {

    private static final String NOMBRE = "Producto A";
    private TableProductoDAO productoDAO;
    private int productoId = -1;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProducto(NOMBRE);
        productoDAO.insertProducto(new Producto(NOMBRE, "Electrónica", 10.00, 50, "Descripción inicial"));
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        if (!lista.isEmpty()) {
            productoId = lista.get(0).getId();
        }
    }

    @After
    public void tearDown() {
        limpiarProducto(NOMBRE);
    }

    @Test
    public void testModificarProductoRetornaMensajeExito() {
        assertNotEquals("Se necesita un ID válido", -1, productoId);
        Producto modificado = new Producto(productoId, NOMBRE, "Electrónica", 12.00, "Descripción actualizada", 50, true);
        String resultado = productoDAO.updateProducto(modificado);
        assertEquals("Producto modificado con éxito", resultado);
    }

    @Test
    public void testProductoTieneNuevoPrecio() {
        assertNotEquals(-1, productoId);
        productoDAO.updateProducto(new Producto(productoId, NOMBRE, "Electrónica", 12.00, "Descripción actualizada", 50, true));
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El precio debe haberse actualizado a 12.00", 12.00, lista.get(0).getPrecio(), 0.01);
    }

    @Test
    public void testProductoTieneNuevaDescripcion() {
        assertNotEquals(-1, productoId);
        productoDAO.updateProducto(new Producto(productoId, NOMBRE, "Electrónica", 12.00, "Descripción actualizada", 50, true));
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("La descripción debe haberse actualizado", "Descripción actualizada", lista.get(0).getDescripcion());
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
            System.err.println("Advertencia limpieza CP12: " + e.getMessage());
        }
    }
}
