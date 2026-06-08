package com.dam.pruebas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Usuario;

public class CasoPrueba03InicioSesionValidoTest {

    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
    }

    @Test
    public void testLoginAdminValido() {
        Usuario u = usuarioDAO.login("admin", "admin");
        assertNotNull("El admin debe poder iniciar sesión con credenciales correctas", u);
    }

    @Test
    public void testLoginAdminTieneRolAdministrador() {
        Usuario u = usuarioDAO.login("admin", "admin");
        assertNotNull(u);
        assertEquals("El admin debe tener autorizacion = 1", 1, u.getAutorizacion());
    }

    @Test
    public void testLoginEmpleadoValido() {
        Usuario u = usuarioDAO.login("empleado", "empleado");
        assertNotNull("El empleado debe poder iniciar sesión con credenciales correctas", u);
        assertEquals(2, u.getAutorizacion());
    }

    @Test
    public void testLoginCompradorValido() {
        Usuario u = usuarioDAO.login("comprador", "comprador");
        assertNotNull("El comprador debe poder iniciar sesión con credenciales correctas", u);
        assertEquals(3, u.getAutorizacion());
    }
}
