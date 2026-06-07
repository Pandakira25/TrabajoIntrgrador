package com.dam.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Comprador;

public class VRegistrarse extends JPanel implements IPanels {
	public static final String NAME = "VRegistrarse";

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB;

	private JTextField txtNombre;
	private JPasswordField txtContrasenia;
	private JTextField txtTel;
	private JTextField txtDireccion;
	private JTextField txtNTarjeta;
	private JButton btnRegistrar;
	private JButton btnCancelar;

	public VRegistrarse() {
		configurarVentana();
		crearComponentes();
	}

	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setName(NAME);
	}

	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Registrarse como Comprador");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(15, 16, 350, 25);
		add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(60, 65, 90, 20);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(165, 62, 250, 26);
		add(txtNombre);

		JLabel lblContrasenia = new JLabel("Contraseña:");
		lblContrasenia.setBounds(60, 105, 90, 20);
		add(lblContrasenia);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(165, 102, 250, 26);
		add(txtContrasenia);

		JLabel lblTel = new JLabel("Teléfono:");
		lblTel.setBounds(60, 145, 90, 20);
		add(lblTel);

		txtTel = new JTextField();
		txtTel.setBounds(165, 142, 150, 26);
		add(txtTel);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(60, 185, 90, 20);
		add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(165, 182, 450, 26);
		add(txtDireccion);

		JLabel lblNTarjeta = new JLabel("N. Tarjeta:");
		lblNTarjeta.setBounds(60, 225, 90, 20);
		add(lblNTarjeta);

		txtNTarjeta = new JTextField();
		txtNTarjeta.setBounds(165, 222, 250, 26);
		add(txtNTarjeta);

		btnRegistrar = new JButton(ConstantesBotones.REGISTRARSE);
		btnRegistrar.setBounds(185, 285, 130, 30);
		add(btnRegistrar);

		btnCancelar = new JButton(ConstantesBotones.CANCELAR);
		btnCancelar.setBounds(335, 285, 130, 30);
		add(btnCancelar);
	}

	public void limpiarDatos() {
		txtNombre.setText("");
		txtContrasenia.setText("");
		txtTel.setText("");
		txtDireccion.setText("");
		txtNTarjeta.setText("");
	}

	@Override
	public void setControlador(Ctrl c) {
		btnRegistrar.addActionListener(c);
		btnRegistrar.setActionCommand(ConstantesBotones.REGISTRARSE);

		btnCancelar.addActionListener(c);
		btnCancelar.setActionCommand(ConstantesBotones.CANCELAR);
	}

	public Comprador obtenerDatos() {
		String nombre = txtNombre.getText().trim();
		String contra = new String(txtContrasenia.getPassword()).trim();
		String telStr = txtTel.getText().trim();
		String direccion = txtDireccion.getText().trim();
		String nTarjeta = txtNTarjeta.getText().trim();

		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (contra.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (telStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El teléfono es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (direccion.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La dirección es obligatoria.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (nTarjeta.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe ingresar una tarjeta.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		int tel;
		try {
			tel = Integer.parseInt(telStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El teléfono debe ser un número entero.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (tel <= 0) {
			JOptionPane.showMessageDialog(this, "El teléfono debe ser un número positivo.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return new Comprador(nombre, contra, tel, direccion, nTarjeta);
	}
}
