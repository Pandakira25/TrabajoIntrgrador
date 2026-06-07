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


public class ProductoModificacionTest {

    private static final String NOMBRE_ORIGINAL  = "Test_RF3_Original";
    private static final String NOMBRE_MODIFICADO = "Test_RF3_Modificado";
    private TableProductoDAO productoDAO;
    private int productoId;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
        limpiarProductos(NOMBRE_ORIGINAL);
        limpiarProductos(NOMBRE_MODIFICADO);

        Producto p = new Producto(NOMBRE_ORIGINAL, "CategoriaTest", 9.99, 5, "Descripcion original RF3");
        productoDAO.insertProducto(p);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_ORIGINAL, null, null, false);
        assertFalse("El producto de prueba debe haberse insertado en setUp", lista.isEmpty());
        productoId = lista.get(0).getId();
    }

    @After
    public void tearDown() {
        limpiarProductos(NOMBRE_ORIGINAL);
        limpiarProductos(NOMBRE_MODIFICADO);
    }

    @Test
    public void testModificarProductoRetornaMensajeExito() {
        Producto actualizado = new Producto(productoId, NOMBRE_MODIFICADO, "CategoriaActualizada",
                                            49.99, "Descripcion actualizada RF3", 15, true);
        String resultado = productoDAO.updateProducto(actualizado);
        assertEquals("Producto modificado con éxito", resultado);
    }

    @Test
    public void testProductoModificadoTieneNuevoNombre() {
        Producto actualizado = new Producto(productoId, NOMBRE_MODIFICADO, "CategoriaActualizada",
                                            49.99, "Descripcion actualizada RF3", 15, true);
        productoDAO.updateProducto(actualizado);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_MODIFICADO, null, null, false);
        assertFalse("El producto debe encontrarse con el nombre nuevo tras la modificación", lista.isEmpty());
        assertEquals(NOMBRE_MODIFICADO, lista.get(0).getNombre());
    }

    @Test
    public void testProductoModificadoTieneNuevoPrecio() {
        Producto actualizado = new Producto(productoId, NOMBRE_MODIFICADO, "CategoriaActualizada",
                                            49.99, "Descripcion actualizada RF3", 15, true);
        productoDAO.updateProducto(actualizado);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_MODIFICADO, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals(49.99, lista.get(0).getPrecio(), 0.01);
    }

    @Test
    public void testProductoModificadoTieneNuevaCategoria() {
        Producto actualizado = new Producto(productoId, NOMBRE_MODIFICADO, "CategoriaActualizada",
                                            49.99, "Descripcion actualizada RF3", 15, true);
        productoDAO.updateProducto(actualizado);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_MODIFICADO, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("CategoriaActualizada", lista.get(0).getCategoria());
    }

    @Test
    public void testNombreOriginalDesapareceTrasModificacion() {
        Producto actualizado = new Producto(productoId, NOMBRE_MODIFICADO, "CategoriaActualizada",
                                            49.99, "Descripcion actualizada RF3", 15, true);
        productoDAO.updateProducto(actualizado);

        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_ORIGINAL, null, null, false);
        assertTrue("El nombre original no debe existir tras la modificación", lista.isEmpty());
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
            System.err.println("Advertencia: error en limpieza RF3 - " + e.getMessage());
        }
    }
}
