package com.dam.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.dam.ctrl.Ctrl;

public class VloginForm extends JPanel implements IPanels {
	public static final String NAME = "VloginForm";

	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;

	private static final int ANCHO = VPrincipal.ANCHO 
			- VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO 
			- VPrincipal.insetsT - VPrincipal.insetsB 
			- VPrincipal.menuH;

	private JLabel lblTitulo;
	private JLabel lblUsuario;
	private JLabel lblContrasena;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JButton btnEntrar;
	private JButton btnRegistrarse;

	public VloginForm() {
		configurarVentana();
		crearComponentes();
	}

	@Override
	public void crearComponentes() {
		setLayout(null);
		
		lblTitulo = new JLabel("Iniciar sesión");
		lblTitulo.setBounds(110, 15, 180, 28);
		add(lblTitulo);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(40, 60, 90, 25);
		add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(130, 60, 210, 25);
		add(txtUsuario);

		lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(40, 100, 90, 25);
		add(lblContrasena);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(130, 100, 210, 25);
		add(txtContrasenia);

		btnEntrar = new JButton(ConstantesBotones.ENTRAR);
		btnEntrar.setBounds(130, 150, 95, 30);
		add(btnEntrar);

		btnRegistrarse = new JButton(ConstantesBotones.REGISTRARSE);
		btnRegistrarse.setBounds(235, 150, 105, 30);
		add(btnRegistrarse);
	}

	@Override
	public void configurarVentana() {
		setName(NAME);
		setSize(ANCHO, ALTO);
	}
	
	

	@Override
	public void setControlador(Ctrl c) {
		btnEntrar.addActionListener(c);
		btnEntrar.setActionCommand(ConstantesBotones.ENTRAR);

		btnRegistrarse.addActionListener(c);
		btnRegistrarse.setActionCommand(ConstantesBotones.REGISTRARSE);
		
		
	}
	
	public String[] getUsr() {
		String usr [] = {txtUsuario.getText(), txtContrasenia.getText()};
		return usr;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public JPasswordField getTxtContrasenia() {
		return txtContrasenia;
	}
	
	public JButton getBtnEntrar() {
		return btnEntrar;
	}
}
