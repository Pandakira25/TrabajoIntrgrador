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

public class CasoPrueba10DeshabilitarUsuarioTest {

    private static final String NOMBRE = "Juan García";
    private TableUsuarioDAO usuarioDAO;
    private int userId = -1;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarUsuario(NOMBRE);
        usuarioDAO.insertUsr(new Comprador(NOMBRE, "pass123", 600000001, "Calle García 1", "1234000012340000"));
        ArrayList<Usuario> todos = usuarioDAO.selectAllUsuarios();
        for (Usuario u : todos) {
            if (NOMBRE.equals(u.getNombre())) {
                userId = u.getUserId();
                break;
            }
        }
    }

    @After
    public void tearDown() {
        limpiarUsuario(NOMBRE);
    }

    @Test
    public void testDeshabilitarUsuarioRetornaMensajeExito() {
        assertNotEquals("Se necesita un ID válido", -1, userId);
        String resultado = usuarioDAO.deshabilitarUsuario(userId);
        assertEquals("Usuario deshabilitado con éxito", resultado);
    }

    @Test
    public void testUsuarioDeshabilitadoTieneActivoCero() throws Exception {
        assertNotEquals(-1, userId);
        usuarioDAO.deshabilitarUsuario(userId);
        assertEquals("El usuario deshabilitado debe tener activo = 0", 0, getActivoEnBD(userId));
    }

    @Test
    public void testUsuarioEstabaActivoAntesDeDeshabilitarse() throws Exception {
        assertNotEquals(-1, userId);
        assertEquals("El usuario debe estar activo antes de ser deshabilitado", 1, getActivoEnBD(userId));
    }

    private int getActivoEnBD(int id) throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT activo FROM usuario WHERE usuario_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        int activo = rs.next() ? rs.getInt(1) : -1;
        rs.close();
        ps.close();
        con.close();
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
            System.err.println("Advertencia limpieza CP10: " + e.getMessage());
        }
    }
}
