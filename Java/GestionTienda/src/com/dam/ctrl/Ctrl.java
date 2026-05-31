package com.dam.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dam.view.VloginForm;

public class Ctrl implements ActionListener {

	private VloginForm vloginForm;

	public Ctrl() {
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
		String usuario = vloginForm.getTxtuser().getText().trim();
		String pwd = new String(vloginForm.getTxtpwd().getPassword());
		// TODO: validar credenciales con BD y usar usuario.getAutorizacion()
		if (usuario.isEmpty() || pwd.isEmpty()) {
			return;
		}

		// Provisional hasta tener login en BD
		vloginForm.autorizacionesMenu(VloginForm.ADMIN);
	}

	private void registrarse() {
		// TODO: abrir VRegistrarse
	}

	private void cerrarSesion() {
		vloginForm.quitarMenu();
	}

}
