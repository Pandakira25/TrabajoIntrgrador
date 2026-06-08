package com.dam.pruebas;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.TableProductoDAO;
import com.dam.model.pojos.Producto;


public class ProductoConsultaTest {

    private TableProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = new TableProductoDAO();
    }

    @Test
    public void testConsultarTodosLosProductos() {
        ArrayList<Producto> lista = productoDAO.selectProductos(null, null, null, false);
        assertNotNull("La lista de productos no debe ser null", lista);
        assertFalse("Debe existir al menos un producto en el sistema", lista.isEmpty());
    }

    @Test
    public void testConsultarSoloProductosActivos() {
        ArrayList<Producto> activos = productoDAO.selectProductos(null, null, null, true);
        assertNotNull(activos);
        for (Producto p : activos) {
            assertTrue("Todos los resultados de la consulta de activos deben estar activos", p.isActivo());
        }
    }

    @Test
    public void testConsultarProductosPorNombreParcial() {
        // "The Last of Us Part II" existe en los datos de inicialización
        ArrayList<Producto> lista = productoDAO.selectProductos("The Last", null, null, false);
        assertFalse("Debe encontrar el producto por prefijo de nombre", lista.isEmpty());
    }

    @Test
    public void testConsultarProductosPorCategoria() {
        ArrayList<Producto> lista = productoDAO.selectProductos(null, null, "Videojuego", false);
        assertFalse("Debe encontrar productos en la categoría Videojuego", lista.isEmpty());
        for (Producto p : lista) {
            assertEquals("Todos los resultados deben pertenecer a la categoría consultada",
                         "Videojuego", p.getCategoria());
        }
    }

    @Test
    public void testConsultarProductosPorRangoPrecioMenor10() {
        ArrayList<Producto> lista = productoDAO.selectProductos(null, "< 10 €", null, false);
        assertNotNull(lista);
        for (Producto p : lista) {
            assertTrue("El precio debe ser menor a 10 €", p.getPrecio() < 10);
        }
    }

    @Test
    public void testConsultarProductosPorRangoPrecioEntre10Y50() {
        ArrayList<Producto> lista = productoDAO.selectProductos(null, "10 - 50 €", null, false);
        assertNotNull(lista);
        for (Producto p : lista) {
            assertTrue("El precio debe estar entre 10 y 50 €", p.getPrecio() >= 10 && p.getPrecio() <= 50);
        }
    }

    @Test
    public void testConsultarCategorias() {
        ArrayList<String> categorias = productoDAO.selectCategorias();
        assertNotNull(categorias);
        assertFalse("Deben existir categorías en el sistema", categorias.isEmpty());
        assertTrue("Debe incluir la categoría Videojuego", categorias.contains("Videojuego"));
        assertTrue("Debe incluir la categoría Audio", categorias.contains("Audio"));
    }
}
