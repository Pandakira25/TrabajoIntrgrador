package com.dam.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.dam.ctrl.Ctrl;

public class VPrincipal extends JFrame implements IFrames {

	public static final int ALTO = 600;
	public static final int ANCHO = 800;

	public static int insetsR;
	public static int insetsL;
	public static int insetsT;
	public static int insetsB;
	public static int menuH;

	private JPanel pnlLogin;
	private JTextField txtUsuario;
	private JPasswordField txtContrasenia;
	private JButton btnIniciarSesion;
	private JButton btnRegistrarse;

	private JMenuBar menuBar;
	private JMenuItem mntmShop;
	private JMenuItem mntmCarrito;
	private JMenuItem mntmGestionEmp;
	private JMenuItem mntmGestionProd;
	private JMenuItem mntmGestionStock;
	private JMenuItem mntmTransacciones;
	private JMenuItem mntmCerrarSesion;
	private JScrollPane scrpContenedor;

	public VPrincipal() {
		configurarVentana();
		crearComponentes();
	}

	public void configurarVentana() {
		setTitle("Gestión tienda");
		setSize(ANCHO, ALTO);
		insetsR = getInsets().right;
		insetsL = getInsets().left;
		insetsT = getInsets().top;
		insetsB = getInsets().bottom;

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = new Dimension(WIDTH, HEIGHT);
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}
	
	@Override
	public void crearComponentes() {
		scrpContenedor = new JScrollPane();
		getContentPane().add(scrpContenedor, BorderLayout.CENTER);
	}

	@Override
	public void crearMenu() {
		menuBar = new JMenuBar();
		menuH = menuBar.getPreferredSize().height;
		setJMenuBar(menuBar);

		mntmCerrarSesion = new JMenuItem("Cerrar Sesión");
		menuBar.add(mntmCerrarSesion);
	}

	public void configurarMenuEmpleado() {
		crearMenu();

		JMenu mnGestion = new JMenu("Gestión");
		menuBar.add(mnGestion, 0);

		mntmGestionStock = new JMenuItem("Gestión de Stock");
		mnGestion.add(mntmGestionStock);
	}

	public void configurarMenuComprador() {
		crearMenu();

		JMenu mnTienda = new JMenu("Menú");
		menuBar.add(mnTienda, 0);

		mntmShop = new JMenuItem("Comprar");
		mnTienda.add(mntmShop);

		mntmCarrito = new JMenuItem("Carrito");
		mnTienda.add(mntmCarrito);
	}

	public void configurarMenuAdmin() {
		crearMenu();

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

	@Override
	public void cargarPanel(JPanel panel) {
		scrpContenedor.setViewportView(panel);
	}

	@Override
	public void hacerVisible() {
		setVisible(true);
	}
	
	
	//TODO: esto lo vamos a hacer desde el controlador
	public void confirmarSalida() {
		int opcion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión actual?", "Confirmar",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (opcion == JOptionPane.YES_OPTION) {
			mostrarLogin();
		}
	}

	@Override
	public void setControlador(Ctrl c) {
		// TODO Auto-generated method stub
		
	}

}
