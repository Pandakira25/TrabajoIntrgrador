package com.dam.pruebas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Usuario;

public class CasoPrueba04InicioSesionInvalidoTest {

    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
    }

    @Test
    public void testLoginConContrasenaIncorrectaRetornaNull() {
        Usuario u = usuarioDAO.login("admin", "wrongpass");
        assertNull("Con contraseña incorrecta no debe iniciarse sesión", u);
    }

    @Test
    public void testLoginConContrasenaVaciaRetornaNull() {
        Usuario u = usuarioDAO.login("admin", "");
        assertNull("Con contraseña vacía no debe iniciarse sesión", u);
    }

    @Test
    public void testLoginConCredencialesVaciasRetornaNull() {
        Usuario u = usuarioDAO.login("", "");
        assertNull("Con credenciales vacías no debe iniciarse sesión", u);
    }

    @Test
    public void testLoginConCredencialesNullRetornaNull() {
        Usuario u = usuarioDAO.login(null, null);
        assertNull("Con credenciales null no debe iniciarse sesión", u);
    }
}
