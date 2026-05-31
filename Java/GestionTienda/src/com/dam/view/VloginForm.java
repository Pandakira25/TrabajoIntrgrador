package com.dam.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.dam.ctrl.Ctrl;

public class VloginForm extends JFrame implements IFrames {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;

	public static int insetsR;
	public static int insetsL;
	public static int insetsT;
	public static int insetsB;
	public static int menuH;

	public static final int ADMIN = 1;
	public static final int EMPLEADO = 2;
	public static final int COMPRADOR = 3;

	private Ctrl controlador;

	private JPanel contentPane;
	private JLabel lblTitulo;
	private JLabel lblUsuario;
	private JLabel lblContrasena;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JButton btnEntrar;
	private JButton btnRegistrarse;

	private JMenuBar menuBar;
	private JMenuItem mntmShop;
	private JMenuItem mntmCarrito;
	private JMenuItem mntmGestionEmp;
	private JMenuItem mntmGestionProd;
	private JMenuItem mntmGestionStock;
	private JMenuItem mntmTransacciones;
	private JMenuItem mntmCerrarSesion;
	private JScrollPane scrlCont;

	public VloginForm() {
		crearComponentes();
		configurarVentana();
		quitarMenu();
	}

	@Override
	public void crearComponentes() {
		contentPane = new JPanel(null);
		setContentPane(contentPane);
		setName("VloginForm");

		scrlCont = new JScrollPane();
		getContentPane().add(scrlCont, BorderLayout.CENTER);

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

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(130, 100, 210, 25);
		contentPane.add(txtContrasenia);

		btnEntrar = new JButton(ConstantesBotones.ENTRAR);
		btnEntrar.setBounds(130, 150, 95, 30);
		contentPane.add(btnEntrar);

		btnRegistrarse = new JButton(ConstantesBotones.REGISTRARSE);
		btnRegistrarse.setBounds(235, 150, 105, 30);
		contentPane.add(btnRegistrarse);
	}

	@Override
	public void configurarVentana() {
		setTitle("* * G U I A  M I C H E L I N * *");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setSize(WIDTH, HEIGHT);

		insetsR = this.getInsets().right;
		insetsL = this.getInsets().left;
		insetsT = this.getInsets().top;
		insetsB = this.getInsets().bottom;

		centerWindow();
		setResizable(false);
	}
	
	private void centerWindow() {
		Dimension pantalla = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = new Dimension(WIDTH, HEIGHT);
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	private void crearMenuBase() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mntmCerrarSesion = new JMenuItem("Cerrar Sesión");
		menuBar.add(mntmCerrarSesion);
	}

	public void menuAdmin() {
		crearMenuBase();

		JMenu mnEmpleados = new JMenu("Empleados");
		menuBar.add(mnEmpleados, 0);
		mntmGestionEmp = new JMenuItem("Gestión de Empleados");
		mnEmpleados.add(mntmGestionEmp);

		JMenu mnProductos = new JMenu("Productos");
		menuBar.add(mnProductos, 1);
		mntmGestionProd = new JMenuItem("Gestión de Productos");
		mnProductos.add(mntmGestionProd);

		JMenu mnTransacciones = new JMenu("Transacciones");
		menuBar.add(mnTransacciones, 2);
		mntmTransacciones = new JMenuItem("Ver Transacciones");
		mnTransacciones.add(mntmTransacciones);
	}

	public void menuEmpleado() {
		crearMenuBase();

		JMenu mnGestion = new JMenu("Gestión");
		menuBar.add(mnGestion, 0);

		mntmGestionStock = new JMenuItem("Gestión de Stock");
		mnGestion.add(mntmGestionStock);
	}

	public void menuComprador() {
		crearMenuBase();

		JMenu mnTienda = new JMenu("Menú");
		menuBar.add(mnTienda, 0);

		mntmShop = new JMenuItem("Comprar");
		mnTienda.add(mntmShop);

		mntmCarrito = new JMenuItem("Carrito");
		mnTienda.add(mntmCarrito);
	}

	// TODO: Corregirlo para que esté en el controlador
	public void autorizacionesMenu(int autorizacion) {
		if (controlador == null) {
			return;
		}
		switch (autorizacion) {
		case ADMIN:
			menuAdmin();
			controladorMenuAdmin(controlador);
			break;
		case EMPLEADO:
			menuEmpleado();
			controladorMenuEmpleado(controlador);
			break;
		case COMPRADOR:
			menuComprador();
			controladorMenuComprador(controlador);
			break;
		default:
			quitarMenu();
			break;
		}
	}

	public void quitarMenu() {
		setJMenuBar(null);
		menuBar = null;
		mntmShop = null;
		mntmCarrito = null;
		mntmGestionEmp = null;
		mntmGestionProd = null;
		mntmGestionStock = null;
		mntmTransacciones = null;
		mntmCerrarSesion = null;
	}

	@Override
	public void cargarPanel(JPanel panel) {
		scrlCont.setViewportView(panel);
	}

	@Override
	public void hacerVisible() {
		setVisible(true);
	}

	@Override
	public void setControlador(Ctrl c) {
		btnEntrar.addActionListener(c);

		btnRegistrarse.addActionListener(c);
		btnRegistrarse.setActionCommand(ConstantesBotones.REGISTRARSE);
	}

	private void controladorMenuAdmin(Ctrl c) {
		mntmCerrarSesion.addActionListener(c);
		mntmGestionEmp.addActionListener(c);
		mntmGestionProd.addActionListener(c);
		mntmTransacciones.addActionListener(c);
	}

	private void controladorMenuEmpleado(Ctrl c) {
		mntmCerrarSesion.addActionListener(c);
		mntmGestionStock.addActionListener(c);
	}

	private void controladorMenuComprador(Ctrl c) {
		mntmCerrarSesion.addActionListener(c);
		mntmShop.addActionListener(c);
		mntmCarrito.addActionListener(c);
	}

	public JTextField getTxtuser() {
		return txtUsuario;
	}

	public JPasswordField getTxtpwd() {
		return txtContrasenia;
	}

	public JButton getBtnEntrar() {
		return btnEntrar;
	}

	public JButton getBtnregister() {
		return btnRegistrarse;
	}

	public JMenuItem getMntmShop() {
		return mntmShop;
	}

	public JMenuItem getMntmCarrito() {
		return mntmCarrito;
	}

	public JMenuItem getMntmGestionEmp() {
		return mntmGestionEmp;
	}

	public JMenuItem getMntmGestionProd() {
		return mntmGestionProd;
	}

	public JMenuItem getMntmGestionStock() {
		return mntmGestionStock;
	}

	public JMenuItem getMntmTransacciones() {
		return mntmTransacciones;
	}

	public JMenuItem getMntmCerrarSesion() {
		return mntmCerrarSesion;
	}
}
