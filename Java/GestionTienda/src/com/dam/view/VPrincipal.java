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
		super("* * T I E N D A  O N L I N E * *");
		configurarVentana();
		mostrarLogin();
	}

	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		insetsR = getInsets().right;
		insetsL = getInsets().left;
		insetsT = getInsets().top;
		insetsB = getInsets().bottom;

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		scrpContenedor = new JScrollPane();
		getContentPane().add(scrpContenedor, BorderLayout.CENTER);

		centrarVentana();
	}

	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((pantalla.width - ANCHO) / 2, (pantalla.height - ALTO) / 2);
	}

	public void mostrarLogin() {
		if (menuBar != null) {
			setJMenuBar(null);
			menuBar = null;
			menuH = 0;
			mntmShop = null;
			mntmCarrito = null;
			mntmGestionEmp = null;
			mntmGestionProd = null;
			mntmGestionStock = null;
			mntmTransacciones = null;
			mntmCerrarSesion = null;
		}
		crearPanelLogin();
		scrpContenedor.setViewportView(pnlLogin);
		revalidate();
		repaint();
	}

	private void crearPanelLogin() {
		pnlLogin = new JPanel(null);
		pnlLogin.setPreferredSize(new Dimension(ANCHO, ALTO));

		JLabel lblTitulo = new JLabel("Iniciar Sesión");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setBounds(275, 120, 260, 30);
		pnlLogin.add(lblTitulo);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(215, 195, 100, 25);
		pnlLogin.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(330, 192, 220, 26);
		pnlLogin.add(txtUsuario);

		JLabel lblContrasenia = new JLabel("Contraseña:");
		lblContrasenia.setBounds(215, 238, 100, 25);
		pnlLogin.add(lblContrasenia);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(330, 235, 220, 26);
		pnlLogin.add(txtContrasenia);

		btnIniciarSesion = new JButton(ConstantesBotones.INICIAR_SESION);
		btnIniciarSesion.setBounds(255, 295, 155, 30);
		pnlLogin.add(btnIniciarSesion);

		btnRegistrarse = new JButton(ConstantesBotones.REGISTRARSE);
		btnRegistrarse.setBounds(430, 295, 130, 30);
		pnlLogin.add(btnRegistrarse);

		JLabel lblInfo = new JLabel("¿No tienes cuenta?");
		lblInfo.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblInfo.setBounds(433, 330, 150, 20);
		pnlLogin.add(lblInfo);
	}
	
	@Override
	public void crearComponentes() {
		// TODO Auto-generated method stub
		
	}

	private void crearMenuBase() {
		menuBar = new JMenuBar();
		menuH = menuBar.getPreferredSize().height;
		setJMenuBar(menuBar);

		mntmCerrarSesion = new JMenuItem("Cerrar Sesión");
		menuBar.add(mntmCerrarSesion);
	}

	public void configurarMenuEmpleado() {
		crearMenuBase();

		JMenu mnGestion = new JMenu("Gestión");
		menuBar.add(mnGestion, 0);

		mntmGestionStock = new JMenuItem("Gestión de Stock");
		mnGestion.add(mntmGestionStock);
	}

	public void configurarMenuComprador() {
		crearMenuBase();

		JMenu mnTienda = new JMenu("Menú");
		menuBar.add(mnTienda, 0);

		mntmShop = new JMenuItem("Comprar");
		mnTienda.add(mntmShop);

		mntmCarrito = new JMenuItem("Carrito");
		mnTienda.add(mntmCarrito);
	}

	public void configurarMenuAdmin() {
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

	@Override
	public void cargarPanel(JPanel panel) {
		scrpContenedor.setViewportView(panel);
		revalidate();
		repaint();
	}

	@Override
	public void hacerVisible() {
		setVisible(true);
	}

	public void confirmarSalida() {
		int opcion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión actual?", "Confirmar",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (opcion == JOptionPane.YES_OPTION) {
			mostrarLogin();
		}
	}

	public void setControladorLogin(ActionListener controlador) {
		btnIniciarSesion.addActionListener(controlador);
		btnRegistrarse.addActionListener(controlador);
	}

	public void setControladorMenu(ActionListener controlador) {
		if (mntmCerrarSesion != null)
			mntmCerrarSesion.addActionListener(controlador);
		if (mntmShop != null)
			mntmShop.addActionListener(controlador);
		if (mntmCarrito != null)
			mntmCarrito.addActionListener(controlador);
		if (mntmGestionEmp != null)
			mntmGestionEmp.addActionListener(controlador);
		if (mntmGestionProd != null)
			mntmGestionProd.addActionListener(controlador);
		if (mntmGestionStock != null)
			mntmGestionStock.addActionListener(controlador);
		if (mntmTransacciones != null)
			mntmTransacciones.addActionListener(controlador);
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public JPasswordField getTxtContrasenia() {
		return txtContrasenia;
	}

	public JButton getBtnIniciarSesion() {
		return btnIniciarSesion;
	}

	public JButton getBtnRegistrarse() {
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

	@Override
	public void setControlador(Ctrl c) {
		// TODO Auto-generated method stub
		
	}
}
