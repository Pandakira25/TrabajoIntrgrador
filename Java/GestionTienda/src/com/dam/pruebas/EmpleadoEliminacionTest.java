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


public class EmpleadoEliminacionTest {

    private static final String NOMBRE_TEST = "Test_RF6_EmpElim";
    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarUsuario(NOMBRE_TEST);
        Empleado emp = new Empleado(2, NOMBRE_TEST, "pass456", 600333444, true,
                                   "12/111222333/01", "ES1234567890123456789012");
        usuarioDAO.insertUsr(emp);
    }

    @After
    public void tearDown() {
        limpiarUsuario(NOMBRE_TEST);
    }

    @Test
    public void testEliminarEmpleadoRetornaMensajeExito() {
        String resultado = usuarioDAO.deleteEmp(NOMBRE_TEST);
        assertEquals("Se ha eliminado el empleado con exito", resultado);
    }

    @Test
    public void testEmpleadoEliminadoNoApareceEnListaDeActivos() {
        usuarioDAO.deleteEmp(NOMBRE_TEST);

        ArrayList<Empleado> lista = usuarioDAO.selectEmpleados(NOMBRE_TEST);
        assertTrue("El empleado dado de baja no debe aparecer en la lista de activos", lista.isEmpty());
    }

    @Test
    public void testEmpleadoEsActivoAntesDeEliminar() {
        ArrayList<Empleado> lista = usuarioDAO.selectEmpleados(NOMBRE_TEST);
        assertFalse("El empleado debe estar activo antes de la eliminación", lista.isEmpty());
        assertTrue(lista.get(0).isActivo());
    }

    @Test
    public void testEliminarEmpleadoInexistenteNoRetornaExito() {
        String resultado = usuarioDAO.deleteEmp("EmpleadoQueNoExisteXYZ9999");
        assertNotEquals("Eliminar un empleado inexistente no debe retornar éxito",
                        "Se ha eliminado el empleado con exito", resultado);
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
            System.err.println("Advertencia: error en limpieza RF6 - " + e.getMessage());
        }
    }
}
