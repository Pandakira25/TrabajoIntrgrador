package com.dam.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dam.ctrl.Ctrl;

public class VloginForm extends JPanel implements IPanels{

    private JPanel contentPane;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnEntrar;
    private JButton btnRegistrarse;

    //TODO: arreglarla para que sea un panel
    
    public VloginForm() {
        initAbsoluteLayout();
        crearComponentes();
        configurarVentana();
    }

    private void initAbsoluteLayout() {
        contentPane = new JPanel(null);
        setContentPane(contentPane);
    }

    public void crearComponentes() {
        lblTitulo = new JLabel("Iniciar sesión");
        lblTitulo.setBounds(110, 15, 180, 28);
        contentPane.add(lblTitulo);

        lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(40, 60, 90, 25);
        contentPane.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(130, 60, 210, 25);
        contentPane.add(txtUsuario);

        lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setBounds(40, 100, 90, 25);
        contentPane.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(130, 100, 210, 25);
        contentPane.add(txtContrasena);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(130, 150, 95, 30);
        contentPane.add(btnEntrar);

        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBounds(235, 150, 105, 30);
        contentPane.add(btnRegistrarse);
    }

    public void configurarVentana() {
        setTitle("Login - Gestión Tienda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public JTextField getTxtuser() {
        return txtUsuario;
    }

    public JPasswordField getTxtpwd() {
        return txtContrasena;
    }

    public JButton getBtnEntrar() {
        return btnEntrar;
    }

    public JButton getBtnregister() {
        return btnRegistrarse;
    }

	@Override
	public void setControlador(Ctrl c) {
		// TODO Auto-generated method stub
		
	}
}
