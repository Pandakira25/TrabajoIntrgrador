package com.dam.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Usuario;
import com.dam.view.VloginForm;

public class Ctrl implements ActionListener {

	private VloginForm vloginForm;
	private TableUsuarioDAO usuarioDAO;
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
		} else if (e.getSource() == vloginForm.getMntmGestionProd()) {
			// TODO: cargarPanel VGestionProd
		} else if (e.getSource() == vloginForm.getMntmTransacciones()) {
			// TODO: cargarPanel VTrans
		} else if (e.getSource() == vloginForm.getMntmGestionStock()) {
			// TODO: cargarPanel VGestionStock
		} else if (e.getSource() == vloginForm.getMntmShop()) {
			// TODO: cargarPanel VShop
		} else if (e.getSource() == vloginForm.getMntmCarrito()) {
			// TODO: cargarPanel VCarrito
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
