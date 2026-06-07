package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Empleado;


public class EmpleadoAltaTest {

    private static final String NOMBRE_TEST = "Test_RF5_EmpAlta";
    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarUsuario(NOMBRE_TEST);
    }

    @After
    public void tearDown() {
        limpiarUsuario(NOMBRE_TEST);
    }

    @Test
    public void testDarDeAltaEmpleadoRetornaExito() {
        Empleado emp = new Empleado(2, NOMBRE_TEST, "pass123", 600111222, true,
                                   "12/333444555/01", "ES9021000418450200051332");
        int resultado = usuarioDAO.insertUsr(emp);
        assertEquals("insertUsr debe retornar 1 cuando el alta es exitosa", 1, resultado);
    }

    @Test
    public void testEmpleadoInsertadoApareceEnLista() {
        Empleado emp = new Empleado(2, NOMBRE_TEST, "pass123", 600111222, true,
                                   "12/333444555/01", "ES9021000418450200051332");
        usuarioDAO.insertUsr(emp);

        ArrayList<Empleado> lista = usuarioDAO.selectEmpleados(NOMBRE_TEST);
        assertFalse("El empleado debe aparecer en la lista de empleados activos", lista.isEmpty());
        assertEquals(NOMBRE_TEST, lista.get(0).getNombre());
    }

    @Test
    public void testEmpleadoInsertadoEstaActivo() {
        Empleado emp = new Empleado(2, NOMBRE_TEST, "pass123", 600111222, true,
                                   "12/333444555/01", "ES9021000418450200051332");
        usuarioDAO.insertUsr(emp);

        ArrayList<Empleado> lista = usuarioDAO.selectEmpleados(NOMBRE_TEST);
        assertFalse(lista.isEmpty());
        assertTrue("El empleado recién insertado debe estar activo", lista.get(0).isActivo());
    }

    @Test
    public void testEmpleadoInsertadoTieneDatosCorrectos() {
        Empleado emp = new Empleado(2, NOMBRE_TEST, "pass123", 600111222, true,
                                   "12/333444555/01", "ES9021000418450200051332");
        usuarioDAO.insertUsr(emp);

        ArrayList<Empleado> lista = usuarioDAO.selectEmpleados(NOMBRE_TEST);
        assertFalse(lista.isEmpty());
        Empleado recuperado = lista.get(0);
        assertEquals("12/333444555/01", recuperado.getnSeguridad());
        assertEquals("ES9021000418450200051332", recuperado.getIban());
    }

    private void limpiarUsuario(String nombre) {
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
            System.err.println("Advertencia: error en limpieza RF5 - " + e.getMessage());
        }
    }
}
