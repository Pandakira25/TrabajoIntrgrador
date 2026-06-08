package com.dam.pruebas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Usuario;

public class CasoPrueba29CerrarSesionTest {

    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
    }

    @Test
    public void testSinCredencialesNoSeIniciaSesion() {
        Usuario u = usuarioDAO.login("", "");
        assertNull("Sin credenciales no debe iniciarse sesión tras el cierre", u);
    }

    @Test
    public void testCredencialesNullNoInicianSesion() {
        Usuario u = usuarioDAO.login(null, null);
        assertNull("Con credenciales null no debe iniciarse sesión", u);
    }

    @Test
    public void testAdminPuedeVolverAIniciarSesionTrasLogout() {
        Usuario u = usuarioDAO.login("admin", "admin");
        assertNotNull("El admin debe poder iniciar sesión de nuevo tras cerrar sesión", u);
        assertEquals(1, u.getAutorizacion());
    }

    @Test
    public void testEmpleadoPuedeVolverAIniciarSesionTrasLogout() {
        Usuario u = usuarioDAO.login("empleado", "empleado");
        assertNotNull("El empleado debe poder iniciar sesión de nuevo tras cerrar sesión", u);
        assertEquals(2, u.getAutorizacion());
    }

    @Test
    public void testCompradorPuedeVolverAIniciarSesionTrasLogout() {
        Usuario u = usuarioDAO.login("comprador", "comprador");
        assertNotNull("El comprador debe poder iniciar sesión de nuevo tras cerrar sesión", u);
        assertEquals(3, u.getAutorizacion());
    }
}
