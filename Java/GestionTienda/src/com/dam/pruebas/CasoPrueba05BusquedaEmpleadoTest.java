package com.dam.pruebas;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Empleado;

public class CasoPrueba05BusquedaEmpleadoTest {

    private TableUsuarioDAO usuarioDAO;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
    }

    @Test
    public void testBuscarEmpleadoNoLanzaExcepcion() {
        ArrayList<Empleado> resultado = usuarioDAO.selectEmpleados("María López");
        assertNotNull("La búsqueda de empleados no debe retornar null", resultado);
    }

    @Test
    public void testBuscarEmpleadoExistenteDevuelveResultados() {
        ArrayList<Empleado> resultado = usuarioDAO.selectEmpleados("empleado");
        assertFalse("La búsqueda de 'empleado' debe devolver al menos un resultado", resultado.isEmpty());
    }

    @Test
    public void testBuscarEmpleadoInexistenteDevuelveListaVacia() {
        ArrayList<Empleado> resultado = usuarioDAO.selectEmpleados("María López");
        assertNotNull(resultado);
        assertTrue("La búsqueda de un empleado que no existe debe devolver lista vacía", resultado.isEmpty());
    }

    @Test
    public void testResultadoBusquedaContieneDatosDelEmpleado() {
        ArrayList<Empleado> resultado = usuarioDAO.selectEmpleados("empleado");
        assertFalse(resultado.isEmpty());
        assertNotNull("El nombre del empleado encontrado no debe ser null", resultado.get(0).getNombre());
    }
}
