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
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Comprador;
import com.dam.model.pojos.Usuario;

public class CasoPrueba26PagarCarritoVacioTest {

    private static final String NOMBRE = "Test_CP26_SinCarrito";
    private TableUsuarioDAO usuarioDAO;
    private TableCarritoDAO carritoDAO;
    private int compradorId = -1;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        carritoDAO = new TableCarritoDAO();
        limpiarUsuario(NOMBRE);
        usuarioDAO.insertUsr(new Comprador(NOMBRE, "pass_cp26", 600000026, "Calle CP26 1", "2626000026260000"));
        ArrayList<Usuario> todos = usuarioDAO.selectAllUsuarios();
        for (Usuario u : todos) {
            if (NOMBRE.equals(u.getNombre())) {
                compradorId = u.getUserId();
                break;
            }
        }
    }

    @After
    public void tearDown() {
        limpiarUsuario(NOMBRE);
    }

    @Test
    public void testCarritoActivoEsMenosUnoSinCarrito() {
        assertNotEquals("Se necesita un ID de comprador válido", -1, compradorId);
        int activo = carritoDAO.getCarritoActivo(compradorId);
        assertEquals("El carritoActivoId debe ser -1 cuando no hay carrito activo", -1, activo);
    }

    @Test
    public void testCondicionDePagoCarritoVacioSeActivaria() {
        assertNotEquals(-1, compradorId);
        int activo = carritoDAO.getCarritoActivo(compradorId);
        assertTrue("La condición 'carritoActivoId == -1' debe ser verdadera", activo == -1);
    }

    @Test
    public void testNuevoCompradorNoTieneCarritoActivo() {
        assertNotEquals(-1, compradorId);
        assertEquals("Un comprador recién creado no debe tener carrito activo", -1, carritoDAO.getCarritoActivo(compradorId));
    }

    private void limpiarUsuario(String nombre) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM comprador WHERE comprador_id IN (SELECT usuario_id FROM usuario WHERE nombre = ?)");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("DELETE FROM usuario WHERE nombre = ?");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia limpieza CP26: " + e.getMessage());
        }
    }
}
