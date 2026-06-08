package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Comprador;
import com.dam.model.pojos.Usuario;

public class CasoPrueba28EliminarCuentaTest {

    private static final String NOMBRE = "Test_CP28_DarseBaja";
    private TableUsuarioDAO usuarioDAO;
    private int compradorId = -1;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarUsuario(NOMBRE);
        usuarioDAO.insertUsr(new Comprador(NOMBRE, "pass_cp28", 600000028, "Calle Baja 28", "2828000028280000"));
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
    public void testDeshabilitarCuentaRetornaMensajeExito() {
        assertNotEquals("Se necesita un ID válido", -1, compradorId);
        String resultado = usuarioDAO.deshabilitarUsuario(compradorId);
        assertEquals("Usuario deshabilitado con éxito", resultado);
    }

    @Test
    public void testCuentaDeshabilitadaTieneActivoCero() throws Exception {
        assertNotEquals(-1, compradorId);
        usuarioDAO.deshabilitarUsuario(compradorId);
        assertEquals("La cuenta dada de baja debe tener activo = 0", 0, getActivoEnBD(compradorId));
    }

    @Test
    public void testCuentaEstabaActivaAntesDeDeshabilitarse() throws Exception {
        assertNotEquals(-1, compradorId);
        assertEquals("La cuenta debe estar activa antes de darse de baja", 1, getActivoEnBD(compradorId));
    }

    private int getActivoEnBD(int id) throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT activo FROM usuario WHERE usuario_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        int activo = rs.next() ? rs.getInt(1) : -1;
        rs.close(); ps.close(); con.close();
        return activo;
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
            System.err.println("Advertencia limpieza CP28: " + e.getMessage());
        }
    }
}
