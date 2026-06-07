package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableCarritoDAO;
import com.dam.model.acessbd.TableCarritoProductoDAO;
import com.dam.model.acessbd.TableTransaccionesDAO;


public class SimulacroPagoTest {

    private static final int COMPRADOR_ID = 4; // "comprador" del init.sql
    private static final int EMPLEADO_ID  = 3; // "empleado" del init.sql (autorizacion=2)
    private static final int PRODUCTO_ID  = 2; // Sony WH-1000XM5

    private TableCarritoDAO carritoDAO;
    private TableCarritoProductoDAO carritoProductoDAO;
    private TableTransaccionesDAO transaccionesDAO;
    private int carritoId;

    @Before
    public void setUp() {
        carritoDAO = new TableCarritoDAO();
        carritoProductoDAO = new TableCarritoProductoDAO();
        transaccionesDAO = new TableTransaccionesDAO();

        carritoId = carritoDAO.crearCarrito(COMPRADOR_ID);
        assertTrue("El carrito de prueba debe crearse correctamente en setUp", carritoId > 0);
        carritoProductoDAO.upsertProducto(carritoId, PRODUCTO_ID, 1);
    }

    @After
    public void tearDown() {
        limpiarTransaccion(COMPRADOR_ID, carritoId);
        limpiarCarrito(carritoId);
    }

    @Test
    public void testSimulacroPagoRetornaMensajeExito() {
        String resultado = transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, null);
        assertEquals("Compra realizada con éxito", resultado);
    }

    @Test
    public void testPagoSinEmpleadoAsignadoEsValido() {
        String resultado = transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, null);
        assertEquals("El pago sin empleado asignado debe ser aceptado", "Compra realizada con éxito", resultado);
    }

    @Test
    public void testPagoConEmpleadoAsignadoEsValido() {
        String resultado = transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, EMPLEADO_ID);
        assertEquals("El pago con empleado asignado debe ser aceptado", "Compra realizada con éxito", resultado);
    }

    @Test
    public void testTransaccionApareceEnHistorialTrasElPago() {
        transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, null);

        String[][] historial = transaccionesDAO.selectTransacciones();
        assertNotNull("El historial no debe ser null", historial);
        assertTrue("Debe existir al menos una transacción en el historial", historial.length > 0);

        boolean encontrado = false;
        for (String[] fila : historial) {
            if ("comprador".equals(fila[0])) {
                encontrado = true;
                break;
            }
        }
        assertTrue("La transacción del comprador debe aparecer en el historial", encontrado);
    }

    @Test
    public void testCarritoActivoYaNoDisponibleTrasElPago() {
        transaccionesDAO.insertTransaccion(COMPRADOR_ID, carritoId, null);
        // Tras el pago, el carrito queda registrado en transacciones
        // y getCarritoActivo NO debe devolverlo (ya fue procesado)
        int carritoActivo = carritoDAO.getCarritoActivo(COMPRADOR_ID);
        assertNotEquals("El carrito pagado no debe ser devuelto como carrito activo", carritoId, carritoActivo);
    }

    // --- Helpers ---

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
            System.err.println("Advertencia: error en limpieza de transacción RF11 - " + e.getMessage());
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
            System.err.println("Advertencia: error en limpieza de carrito RF11 - " + e.getMessage());
        }
    }
}
