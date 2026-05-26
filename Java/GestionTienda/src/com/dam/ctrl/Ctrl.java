package com.dam.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dam.view.VloginForm;

public class Ctrl implements ActionListener {

	private VloginForm vloginForm;

	public Ctrl() {
		vloginForm = new VloginForm();
		registrarListeners();
		vloginForm.hacerVisible();
	}

	private void registrarListeners() {
		vloginForm.getBtnEntrar().addActionListener(this);
		vloginForm.getBtnregister().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == vloginForm.getBtnEntrar()) {
			entrar();
		} else if (e.getSource() == vloginForm.getBtnregister()) {
			registrarse();
		}
	}

	private void entrar() {
		String usuario = vloginForm.getTxtuser().getText();
		String contrasena = new String(vloginForm.getTxtpwd().getPassword());
		// TODO: validar credenciales y abrir ventana principal
	}

	private void registrarse() {
		// TODO: abrir VRegistrarse
	}

}
