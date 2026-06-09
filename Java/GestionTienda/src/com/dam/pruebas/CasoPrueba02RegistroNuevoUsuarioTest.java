package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Comprador;
import com.dam.model.pojos.Usuario;

public class CasoPrueba02RegistroNuevoUsuarioTest {

    private static final String NOMBRE = "Juan García";
    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarUsuario(NOMBRE);
    }

    @After
    public void tearDown() {
        limpiarUsuario(NOMBRE);
    }

    @Test
    public void testRegistroCompradorRetornaExito() {
        Comprador c = new Comprador(NOMBRE, "pass123", 600000001, "Calle García 1", "1234000012340000");
        int resultado = usuarioDAO.insertUsr(c);
        assertEquals(1, resultado);
    }

    @Test
    public void testCompradorRegistradoPuedeIniciarSesion() {
        usuarioDAO.insertUsr(new Comprador(NOMBRE, "pass123", 600000001, "Calle García 1", "1234000012340000"));
        Usuario u = usuarioDAO.login(NOMBRE, "pass123");
        assertNotNull("El comprador registrado debe poder hacer login", u);
    }

    @Test
    public void testCompradorRegistradoTieneRolComprador() {
        usuarioDAO.insertUsr(new Comprador(NOMBRE, "pass123", 600000001, "Calle García 1", "1234000012340000"));
        Usuario u = usuarioDAO.login(NOMBRE, "pass123");
        assertNotNull(u);
        assertEquals("El rol del usuario registrado debe ser Comprador (3)", 3, u.getAutorizacion());
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
            System.err.println("Advertencia limpieza CP02: " + e.getMessage());
        }
    }
}
