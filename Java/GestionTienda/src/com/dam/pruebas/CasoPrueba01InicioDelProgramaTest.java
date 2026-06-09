package com.dam.pruebas;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import com.dam.model.acessbd.AccessDBProp;
import com.dam.model.acessbd.TableUsuarioDAO;

public class CasoPrueba01InicioDelProgramaTest {

    @Test
    public void testConexionBaseDatosDisponible() throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        assertNotNull("La conexión a la base de datos no debe ser null al arrancar", con);
        con.close();
    }

    @Test
    public void testDaoLoginInstanciableSinExcepcion() {
        TableUsuarioDAO dao = new TableUsuarioDAO();
        assertNotNull("El DAO de login debe poder instanciarse", dao);
    }

    @Test
    public void testConexionNoEstaaCerrada() throws Exception {
        AccessDBProp acc = new AccessDBProp();
        Connection con = acc.getConnection();
        assertFalse("La conexión no debe estar cerrada", con.isClosed());
        con.close();
    }
}
