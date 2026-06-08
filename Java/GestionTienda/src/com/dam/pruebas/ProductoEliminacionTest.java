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


public class ProductoEliminacionTest {

    private static final String NOMBRE_TEST = "Test_RF4_Eliminacion";
    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProductos(NOMBRE_TEST);
        Producto p = new Producto(NOMBRE_TEST, "Test", 5.99, 3, "Producto a eliminar RF4");
        productoDAO.insertProducto(p);
    }

    @After
    public void tearDown() {
        limpiarProductos(NOMBRE_TEST);
    }

    @Test
    public void testEliminarProductoRetornaMensajeExito() {
        String resultado = productoDAO.disableProd(NOMBRE_TEST);
        assertEquals("Se ha deshabilitado el producto con éxito", resultado);
    }

    @Test
    public void testProductoEliminadoNoApareceEnConsultaDeActivos() {
        productoDAO.disableProd(NOMBRE_TEST);

        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE_TEST, null, null, true);
        assertTrue("El producto deshabilitado no debe aparecer en la consulta de activos", activos.isEmpty());
    }

    @Test
    public void testProductoEliminadoSigueFigurandoEnBDConEstadoInactivo() {
        productoDAO.disableProd(NOMBRE_TEST);

        ArrayList<Producto> todos = productoDAO.selectProductos(NOMBRE_TEST, null, null, false);
        assertFalse("El producto debe seguir en la BD tras la baja lógica", todos.isEmpty());
        assertFalse("El producto debe tener estado activo = false", todos.get(0).isActivo());
    }

    @Test
    public void testProductoSePuedeDarDeAltaNuevamente() {
        productoDAO.disableProd(NOMBRE_TEST);
        String resultado = productoDAO.enableProd(NOMBRE_TEST);
        assertEquals("Se ha habilitado el producto con éxito", resultado);

        ArrayList<Producto> activos = productoDAO.selectProductos(NOMBRE_TEST, null, null, true);
        assertFalse("El producto reactivado debe volver a aparecer entre los activos", activos.isEmpty());
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
            System.err.println("Advertencia: error en limpieza RF4 - " + e.getMessage());
        }
    }
}
