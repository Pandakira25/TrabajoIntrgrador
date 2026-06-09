package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Empleado;
import com.dam.model.pojos.Usuario;

public class CasoPrueba06RegistroEmpleadoTest {

    private static final String NOMBRE = "Carlos Ruiz";
    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarEmpleado(NOMBRE);
    }

    @After
    public void tearDown() {
        limpiarEmpleado(NOMBRE);
    }

    @Test
    public void testRegistroEmpleadoRetornaExito() {
        Empleado emp = new Empleado(2, NOMBRE, "emp456", 600000002, true, "28/CARLOS/01", "ES1234000000000000000099");
        int resultado = usuarioDAO.insertUsr(emp);
        assertEquals("El registro del empleado debe retornar 1", 1, resultado);
    }

    @Test
    public void testEmpleadoRegistradoPuedeIniciarSesion() {
        usuarioDAO.insertUsr(new Empleado(2, NOMBRE, "emp456", 600000002, true, "28/CARLOS/01", "ES1234000000000000000099"));
        Usuario u = usuarioDAO.login(NOMBRE, "emp456");
        assertNotNull("El empleado registrado debe poder hacer login", u);
    }

    @Test
    public void testEmpleadoRegistradoTieneRolEmpleado() {
        usuarioDAO.insertUsr(new Empleado(2, NOMBRE, "emp456", 600000002, true, "28/CARLOS/01", "ES1234000000000000000099"));
        Usuario u = usuarioDAO.login(NOMBRE, "emp456");
        assertNotNull(u);
        assertEquals("El rol del empleado registrado debe ser 2", 2, u.getAutorizacion());
    }

    @Test
    public void testEmpleadoAparecaEnBusqueda() {
        usuarioDAO.insertUsr(new Empleado(2, NOMBRE, "emp456", 600000002, true, "28/CARLOS/01", "ES1234000000000000000099"));
        assertFalse("El empleado registrado debe aparecer en la búsqueda", usuarioDAO.selectEmpleados(NOMBRE).isEmpty());
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
            System.err.println("Advertencia limpieza CP06: " + e.getMessage());
        }
    }
}
