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
import com.dam.model.pojos.Comprador;
import com.dam.model.pojos.Usuario;

public class CasoPrueba27VerModificarCuentaTest {

    private static final String NOMBRE_ORIGINAL  = "Test_CP27_CompOrig";
    private static final String NOMBRE_MODIFICADO = "Test_CP27_CompMod";
    private TableUsuarioDAO usuarioDAO;
    private int compradorId = -1;

    @Before
    public void setUp() {
        usuarioDAO = new TableUsuarioDAO();
        limpiarUsuario(NOMBRE_ORIGINAL);
        limpiarUsuario(NOMBRE_MODIFICADO);
        usuarioDAO.insertUsr(new Comprador(NOMBRE_ORIGINAL, "pass_orig", 600000027, "Calle Original 27", "2700000027000000"));
        ArrayList<Usuario> todos = usuarioDAO.selectAllUsuarios();
        for (Usuario u : todos) {
            if (NOMBRE_ORIGINAL.equals(u.getNombre())) {
                compradorId = u.getUserId();
                break;
            }
        }
    }

    @After
    public void tearDown() {
        limpiarUsuario(NOMBRE_ORIGINAL);
        limpiarUsuario(NOMBRE_MODIFICADO);
    }

    @Test
    public void testSelectCompradorDevuelveDatos() {
        assertNotEquals(-1, compradorId);
        Comprador c = usuarioDAO.selectComprador(compradorId);
        assertNotNull("selectComprador debe devolver datos del comprador", c);
        assertEquals(NOMBRE_ORIGINAL, c.getNombre());
    }

    @Test
    public void testActualizarTelefonoRetornaMensajeExito() {
        assertNotEquals(-1, compradorId);
        Comprador actualizado = new Comprador(compradorId, 3, NOMBRE_MODIFICADO, "newpass789",
                                             600000099, true, "Calle Nueva 27", "9900000099000000");
        String resultado = usuarioDAO.updateComprador(actualizado);
        assertEquals("Perfil actualizado con éxito", resultado);
    }

    @Test
    public void testDatosActualizadosSonRecuperables() {
        assertNotEquals(-1, compradorId);
        Comprador actualizado = new Comprador(compradorId, 3, NOMBRE_MODIFICADO, "newpass789",
                                             600000099, true, "Calle Nueva 27", "9900000099000000");
        usuarioDAO.updateComprador(actualizado);
        Comprador recuperado = usuarioDAO.selectComprador(compradorId);
        assertNotNull(recuperado);
        assertEquals("El teléfono debe haberse actualizado", 600000099, recuperado.getTel());
    }

    private void limpiarUsuario(String nombre) {
        try {
            AccessDBProp acc = new AccessDBProp();
            Connection con = acc.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM comprador WHERE comprador_id IN (SELECT usuario_id FROM usuario WHERE nombre = ?)");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement("DELETE FROM usuario WHERE nombre = ?");
            ps.setString(1, nombre);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Advertencia limpieza CP27: " + e.getMessage());
        }
    }
}
