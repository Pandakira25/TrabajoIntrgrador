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

public class CasoPrueba07EliminacionEmpleadoTest {

    private static final String NOMBRE = "Carlos Ruiz";
    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarEmpleado(NOMBRE);
        usuarioDAO.insertUsr(new Empleado(2, NOMBRE, "emp456", 600000002, true, "28/CARLOS/01", "ES1234000000000000000099"));
    }

    @After
    public void tearDown() {
        limpiarEmpleado(NOMBRE);
    }

    @Test
    public void testEliminarEmpleadoRetornaMensajeExito() {
        String resultado = usuarioDAO.deleteEmp(NOMBRE);
        assertEquals("Se ha eliminado el empleado con exito", resultado);
    }

    @Test
    public void testEmpleadoEliminadoNoApareceEnBusqueda() {
        usuarioDAO.deleteEmp(NOMBRE);
        assertTrue("El empleado eliminado no debe aparecer en la búsqueda", usuarioDAO.selectEmpleados(NOMBRE).isEmpty());
    }

    @Test
    public void testEmpleadoEliminadoNoPuedeIniciarSesion() {
        usuarioDAO.deleteEmp(NOMBRE);
        assertNull("El empleado eliminado no debe poder hacer login", usuarioDAO.login(NOMBRE, "emp456"));
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
            System.err.println("Advertencia limpieza CP07: " + e.getMessage());
        }
    }
}
