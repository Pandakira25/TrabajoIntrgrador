package com.dam.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.dam.model.acessbd.TableCarritoDAO;
import com.dam.model.acessbd.TableCarritoProductoDAO;
import com.dam.model.acessbd.TableCompradorDAO;
import com.dam.model.acessbd.TableEmpleadoDAO;
import com.dam.model.acessbd.TableProductoDAO;
import com.dam.model.acessbd.TableTransaccionesDAO;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Usuario;
import com.dam.view.VCarrito;
import com.dam.view.VGestionEmp;
import com.dam.view.VGestionProd;
import com.dam.view.VGestionStock;
import com.dam.view.VRegistrarse;
import com.dam.view.VShop;
import com.dam.view.VTrans;
import com.dam.view.VloginForm;

public class Ctrl implements ActionListener {

	private VloginForm vloginForm;
	private VCarrito vca;
	private VGestionEmp vgemp;
	private VGestionProd vgprod;
	private VGestionStock vgstk;
	private VRegistrarse vr;
	private VShop vsh;
	private VTrans vtr;
	
	private TableUsuarioDAO usuarioDAO;
	private TableCarritoDAO carritoDao;
	private TableEmpleadoDAO empleadoDAO;
	private TableCompradorDAO compradorDAO;
	private TableProductoDAO productoDAO;
	private TableCarritoProductoDAO carritoProductoDAO;
	private TableTransaccionesDAO transaccionesDAO;
	
	private Usuario usuarioLogueado;

	public Ctrl() {
		usuarioDAO = new TableUsuarioDAO();
		vloginForm = new VloginForm();
		vloginForm.setControlador(this);
		vloginForm.hacerVisible();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == vloginForm.getBtnEntrar()) {
			entrar();
		} else if (e.getSource() == vloginForm.getBtnregister()) {
			registrarse();
		} else if (e.getSource() == vloginForm.getMntmCerrarSesion()) {
			cerrarSesion();
		} else if (e.getSource() == vloginForm.getMntmGestionEmp()) {
			// TODO: cargarPanel VGestionEmp
			vloginForm.cargarPanel(vgemp);
		} else if (e.getSource() == vloginForm.getMntmGestionProd()) {
			// TODO: cargarPanel VGestionProd
			vloginForm.cargarPanel(vgprod);
		} else if (e.getSource() == vloginForm.getMntmTransacciones()) {
			// TODO: cargarPanel VTrans
			vloginForm.cargarPanel(vtr);
		} else if (e.getSource() == vloginForm.getMntmGestionStock()) {
			// TODO: cargarPanel VGestionStock
			vloginForm.cargarPanel(vgstk);
		} else if (e.getSource() == vloginForm.getMntmShop()) {
			// TODO: cargarPanel VShop
			vloginForm.cargarPanel(vsh);
		} else if (e.getSource() == vloginForm.getMntmCarrito()) {
			// TODO: cargarPanel VCarrito
			vloginForm.cargarPanel(vca);
		}
	}

	private void entrar() {
		String nombre = vloginForm.getTxtuser().getText().trim();
		String pwd = new String(vloginForm.getTxtpwd().getPassword());

		if (nombre.isEmpty() || pwd.isEmpty()) {
			JOptionPane.showMessageDialog(vloginForm, "Introduce usuario y contraseña.", "Login",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Usuario u = usuarioDAO.login(nombre, pwd);

		if (u == null) {
			JOptionPane.showMessageDialog(vloginForm, "Usuario o contraseña incorrectos.", "Login",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!u.isActivo()) {
			JOptionPane.showMessageDialog(vloginForm, "Esta cuenta está desactivada.", "Login",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		usuarioLogueado = u;
		vloginForm.autorizacionesMenu(u.getAutorizacion());
	}

	private void registrarse() {
		// TODO: abrir VRegistrarse
	}

	private void cerrarSesion() {
		usuarioLogueado = null;
		vloginForm.quitarMenu();
		vloginForm.getTxtuser().setText("");
		vloginForm.getTxtpwd().setText("");
	}

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

}
