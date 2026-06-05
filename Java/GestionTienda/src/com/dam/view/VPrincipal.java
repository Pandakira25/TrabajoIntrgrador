package com.dam.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.dam.ctrl.Ctrl;

public class VPrincipal extends JFrame implements IFrames {
	public static final int ANCHO = 1440;
	public static final int ALTO = 1024;

	public static int insetsR;
	public static int insetsL;
	public static int insetsT;
	public static int insetsB;
	public static int menuH;

	public static final int ADMIN = 1;
	public static final int EMPLEADO = 2;
	public static final int COMPRADOR = 3;

	private JMenuBar menuBar;
	private JMenuItem mntmShop;
	private JMenuItem mntmCarrito;
	private JMenuItem mntmGestionEmp;
	private JMenuItem mntmGestionProd;
	private JMenuItem mntmGestionStock;
	private JMenuItem mntmTransacciones;
	private JMenuItem mntmCerrarSesion;
	private JScrollPane scrlCont;

	private JMenu mnTienda;

	private JMenu mnEmpleados;

	private JMenu mnProductos;

	private JMenu mnTransacciones;

	public VPrincipal() {
		configurarVentana();
		crearMenu();
		crearComponentes();
	}

	@Override
	public void crearComponentes() {
		scrlCont = new JScrollPane();
		getContentPane().add(scrlCont, BorderLayout.CENTER);
		
		
		
	}

	private void crearMenu() {
		
		
		//--------------------------------------------------
		menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		mnTienda = new JMenu("Menú");
		
		mnEmpleados = new JMenu("Empleados");
		
		mnProductos = new JMenu("Productos");
		
		mnTransacciones = new JMenu("Transacciones");
		
		//-------------------------------------------------------
		mntmCerrarSesion = new JMenuItem(ConstantesBotones.CERRAR_SESION);
		
		mntmGestionEmp = new JMenuItem(ConstantesBotones.GESTION_EMPLEADOS);
		
		mntmGestionProd = new JMenuItem(ConstantesBotones.GESTION_PRODUCTOS);
		
		mntmGestionStock = new JMenuItem(ConstantesBotones.GESTION_STOCK);
		
		mntmTransacciones = new JMenuItem(ConstantesBotones.VER_TRANSACCIONES);
		
		mntmShop = new JMenuItem(ConstantesBotones.COMPRAR);
		
		mntmCarrito = new JMenuItem(ConstantesBotones.CARRITO);
	}

	@Override
	public void configurarVentana() {
		setTitle("Gestión tienda");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setSize(ANCHO, ALTO);

		insetsR = this.getInsets().right;
		insetsL = this.getInsets().left;
		insetsT = this.getInsets().top;
		insetsB = this.getInsets().bottom;

		centerWindow();
	}
	
	private void centerWindow() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = new Dimension(ANCHO, ALTO);
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	public void crearMenuBase() {
		menuBar.setVisible(true);
		menuBar.add(mntmCerrarSesion);
	}

	public void menuAdmin() {
		crearMenuBase();
		
		menuBar.add(mnEmpleados, 0);
		
		mnEmpleados.add(mntmGestionEmp);

		menuBar.add(mnProductos, 1);
		
		mnProductos.add(mntmGestionProd);
		mnProductos.add(mntmGestionStock);

		menuBar.add(mnTransacciones, 2);
		
		mnTransacciones.add(mntmTransacciones);
		
	}

	public void menuComprador() {
		crearMenuBase();

		menuBar.add(mnTienda, 0);
		
		mnTienda.add(mntmShop);
		
		mnTienda.add(mntmCarrito);
	}

	public void quitarMenu() {
		menuBar.removeAll();
		menuBar.setVisible(false);
		menuBar.revalidate();
		menuBar.repaint();
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
		
		mntmCerrarSesion.addActionListener(c);
		mntmCerrarSesion.setActionCommand(ConstantesBotones.CERRAR_SESION);
		
		mntmGestionEmp.addActionListener(c);
		mntmGestionEmp.setActionCommand(ConstantesBotones.GESTION_EMPLEADOS);
		
		mntmGestionProd.addActionListener(c);
		mntmGestionProd.setActionCommand(ConstantesBotones.GESTION_PRODUCTOS);
		
		mntmTransacciones.addActionListener(c);
		mntmTransacciones.setActionCommand(ConstantesBotones.VER_TRANSACCIONES);
		
		mntmGestionStock.addActionListener(c);
		mntmGestionStock.setActionCommand(ConstantesBotones.GESTION_STOCK);
		
		mntmShop.addActionListener(c);
		mntmShop.setActionCommand(ConstantesBotones.COMPRAR);
		
		mntmCarrito.addActionListener(c);
		mntmCarrito.setActionCommand(ConstantesBotones.CARRITO);
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
