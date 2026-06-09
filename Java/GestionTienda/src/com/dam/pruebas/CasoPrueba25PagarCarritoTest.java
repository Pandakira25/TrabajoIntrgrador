package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableCarritoDAO;
import com.dam.model.acessbd.TableCarritoProductoDAO;
import com.dam.model.acessbd.TableProductoDAO;
import com.dam.model.acessbd.TableTransaccionesDAO;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Empleado;
import com.dam.model.pojos.Producto;
import com.dam.model.pojos.Usuario;

public class CasoPrueba25PagarCarritoTest {

    private static final String NOMBRE_EMPLEADO = "Carlos Ruiz";
    private static final String NOMBRE_PRODUCTO = "Test_CP25_Prod";
    private static final int    COMPRADOR_ID    = 4;
    private TableUsuarioDAO usuarioDAO;
    private TableProductoDAO productoDAO;
    private TableCarritoDAO carritoDAO;
    private TableCarritoProductoDAO carritoProductoDAO;
    private TableTransaccionesDAO transaccionesDAO;
    private int empleadoId = -1;
    private int productoId = -1;
    private int carritoId  = -1;

    @Before
    public void setUp() {
        usuarioDAO       = new TableUsuarioDAO();
        productoDAO      = new TableProductoDAO();
        carritoDAO       = new TableCarritoDAO();
        carritoProductoDAO = new TableCarritoProductoDAO();
        transaccionesDAO = new TableTransaccionesDAO();

        limpiarEmpleado(NOMBRE_EMPLEADO);
        limpiarProducto(NOMBRE_PRODUCTO);

        usuarioDAO.insertUsr(new Empleado(2, NOMBRE_EMPLEADO, "emp456", 600000002, true, "28/CARLOS/01", "ES1234000000000000000099"));
        ArrayList<Usuario> todos = usuarioDAO.selectAllUsuarios();
        for (Usuario u : todos) {
            if (NOMBRE_EMPLEADO.equals(u.getNombre())) {
                empleadoId = u.getUserId();
                break;
            }
        }

        productoDAO.insertProducto(new Producto(NOMBRE_PRODUCTO, "Electrónica", 10.00, 10, "Producto para pago CP25"));
        ArrayList<Producto> prods = productoDAO.selectProductos(NOMBRE_PRODUCTO, null, null, false);
        if (!prods.isEmpty()) {
            productoId = prods.get(0).getId();
        }

        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        assertTrue("El carrito debe crearse correctamente", carritoId > 0);
        carritoProductoDAO.upsertProducto(carritoId, productoId, 2);
    }

    @After
    public void tearDown() {
        limpiarTransaccion(COMPRADOR_ID, carritoId);
        limpiarCarrito(carritoId);
        limpiarProducto(NOMBRE_PRODUCTO);
        limpiarEmpleado(NOMBRE_EMPLEADO);
    }

    @Test
    public void testPagarCarritoRetornaMensajeExito() {
        assertNotEquals(-1, empleadoId);
        String resultado = transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, empleadoId);
        assertEquals("Compra realizada con éxito", resultado);
    }

    @Test
    public void testStockDecrementadoTrasElPago() {
        assertNotEquals(-1, productoId);
        productoDAO.decrementarStock(productoId, 2);
        transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, empleadoId);
        ArrayList<Producto> lista = productoDAO.selectProductos(NOMBRE_PRODUCTO, null, null, false);
        assertFalse(lista.isEmpty());
        assertEquals("El stock debe haberse decrementado en 2", 8, lista.get(0).getStock());
    }

    @Test
    public void testCarritoYaNoEsActivoTrasElPago() {
        transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, empleadoId);
        int activo = carritoDAO.getCarritoActivo(COMPRADOR_ID);
        assertNotEquals("El carrito pagado no debe seguir como activo", carritoId, activo);
    }

    @Test
    public void testTransaccionApareceEnHistorial() {
        transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, empleadoId);
        String[][] historial = transaccionesDAO.selectTransacciones();
        assertNotNull(historial);
        assertTrue("El historial debe contener al menos una transacción", historial.length > 0);
    }

    private void limpiarTransaccion(int compradorId, int cId) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM transacciones WHERE comprador_id = ? AND carrito_id = ?");
            ps.setInt(1, compradorId);
            ps.setInt(2, cId);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia limpieza transacción CP25: " + e.getMessage());
        }
    }

    private void limpiarCarrito(int cId) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM carrito_producto WHERE carrito_id = ?");
            ps.setInt(1, cId);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("DELETE FROM carrito WHERE carrito_id = ?");
            ps.setInt(1, cId);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia limpieza carrito CP25: " + e.getMessage());
        }
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
            System.err.println("Advertencia limpieza producto CP25: " + e.getMessage());
        }
    }

    private void limpiarEmpleado(String nombre) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM empleado WHERE empleado_id IN (SELECT usuario_id FROM usuario WHERE nombre = ?)");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("DELETE FROM usuario WHERE nombre = ?");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia limpieza empleado CP25: " + e.getMessage());
        }
    }
}
