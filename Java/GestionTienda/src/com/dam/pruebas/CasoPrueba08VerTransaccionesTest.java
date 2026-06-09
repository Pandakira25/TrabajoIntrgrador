package com.dam.pruebas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dam.model.acessbd.TableTransaccionesDAO;

public class CasoPrueba08VerTransaccionesTest {

    private TableTransaccionesDAO transaccionesDAO;

    @Before
    public void setUp() {
        transaccionesDAO = new TableTransaccionesDAO();
    }

    @Test
    public void testConsultaTransaccionesNoRetornaNull() {
        String[][] resultado = transaccionesDAO.selectTransacciones();
        assertNotNull("La consulta de transacciones no debe retornar null", resultado);
    }

    @Test
    public void testExisteAlMenosUnaTransaccion() {
        String[][] resultado = transaccionesDAO.selectTransacciones();
        assertTrue("Debe existir al menos una transacción registrada", resultado.length > 0);
    }

    @Test
    public void testTransaccionTieneNombreCompradorNoVacio() {
        String[][] resultado = transaccionesDAO.selectTransacciones();
        assertTrue(resultado.length > 0);
        assertNotNull("El nombre del comprador en la transacción no debe ser null", resultado[0][0]);
        assertFalse("El nombre del comprador no debe estar vacío", resultado[0][0].isEmpty());
    }

    @Test
    public void testTransaccionTieneImporteNoVacio() {
        String[][] resultado = transaccionesDAO.selectTransacciones();
        assertTrue(resultado.length > 0);
        assertNotNull("El importe de la transacción no debe ser null", resultado[0][2]);
        assertFalse("El importe no debe estar vacío", resultado[0][2].isEmpty());
    }

    @Test
    public void testFiltrarPorCompradorDevuelveResultados() {
        String[][] resultado = transaccionesDAO.selectTransaccionesCons(new String[]{"comprador", null});
        assertNotNull(resultado);
        assertTrue("Filtrar por el comprador del sistema debe devolver resultados", resultado.length > 0);
    }
}
