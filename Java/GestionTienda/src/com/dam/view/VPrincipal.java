package com.dam.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.dam.ctrl.Ctrl;
import java.awt.Font;

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
	
	public static Color colorVibrante = new Color(226, 245, 114);
    public static Color colorPalido = new Color(240, 250, 180);
    public static Color colorNaranjaPatito = new Color(245, 199, 114);

	private JMenuBar menuBar;
	private JMenuItem mntmShop;
	private JMenuItem mntmCarrito;
	private JMenuItem mntmGestionEmp;
	private JMenuItem mntmGestionProd;
	private JMenuItem mntmGestionStock;
	private JMenuItem mntmTransacciones;
	private JMenu mnCerrarSesion;
	private JMenuItem mntmGestionUsr;
	private JScrollPane scrlCont;

	private JMenu mnTienda;

	private JMenu mnEmpleados;

	private JMenu mnProductos;

	private JMenu mnTransacciones;
	private JMenuItem mntmCuenta;
	private JMenu mnUsuarios;

	public VPrincipal() {
		configurarVentana();
		crearMenu();
		crearComponentes();
	}

	@Override
	public void crearComponentes() {
		scrlCont = new JScrollPane();
		scrlCont.setBackground(new Color(240, 240, 240));
		getContentPane().add(scrlCont, BorderLayout.CENTER);
	}

	private void crearMenu() {
	    menuBar = new JMenuBar();
	    menuBar.setBackground(colorVibrante);
	    setJMenuBar(menuBar);

	    mnTienda = new JMenu("Menú");
	    mnTienda.setBackground(colorVibrante);
	    mnTienda.setFont(Fuentes.BOLD.deriveFont(16f));

	    mnEmpleados = new JMenu("Empleados");
	    mnEmpleados.setBackground(colorVibrante);
	    mnEmpleados.setFont(Fuentes.BOLD.deriveFont(16f));

	    mnProductos = new JMenu("Productos");
	    mnProductos.setBackground(colorVibrante);
	    mnProductos.setFont(Fuentes.BOLD.deriveFont(16f));

	    mnTransacciones = new JMenu("Transacciones");
	    mnTransacciones.setBackground(colorVibrante);
	    mnTransacciones.setFont(Fuentes.BOLD.deriveFont(16f));

	    mnUsuarios = new JMenu("Usuarios");
	    mnUsuarios.setBackground(colorVibrante);
	    mnUsuarios.setFont(Fuentes.BOLD.deriveFont(16f));

	    mntmCuenta = new JMenuItem(ConstantesBotones.MI_CUENTA);
	    mntmCuenta.setBackground(colorPalido);
	    mntmCuenta.setFont(Fuentes.REGULAR.deriveFont(16f));

	    mnCerrarSesion = new JMenu(ConstantesBotones.CERRAR_SESION);
	    mnCerrarSesion.setBackground(colorVibrante);
	    mnCerrarSesion.setFont(Fuentes.BOLD.deriveFont(16f));

	    mntmGestionEmp = new JMenuItem(ConstantesBotones.GESTION_EMPLEADOS);
	    mntmGestionEmp.setBackground(colorPalido);
	    mntmGestionEmp.setFont(Fuentes.REGULAR.deriveFont(16f));

	    mntmGestionProd = new JMenuItem(ConstantesBotones.GESTION_PRODUCTOS);
	    mntmGestionProd.setBackground(colorPalido);
	    mntmGestionProd.setFont(Fuentes.REGULAR.deriveFont(16f));

	    mntmGestionUsr = new JMenuItem(ConstantesBotones.GESTION_USUARIOS);
	    mntmGestionUsr.setBackground(colorPalido);
	    mntmGestionUsr.setFont(Fuentes.REGULAR.deriveFont(16f));

	    mntmGestionStock = new JMenuItem(ConstantesBotones.GESTION_STOCK);
	    mntmGestionStock.setBackground(colorPalido);
	    mntmGestionStock.setFont(Fuentes.REGULAR.deriveFont(16f));

	    mntmTransacciones = new JMenuItem(ConstantesBotones.VER_TRANSACCIONES);
	    mntmTransacciones.setBackground(colorPalido);
	    mntmTransacciones.setFont(Fuentes.REGULAR.deriveFont(16f));

	    mntmShop = new JMenuItem(ConstantesBotones.COMPRAR);
	    mntmShop.setBackground(colorPalido);
	    mntmShop.setFont(Fuentes.REGULAR.deriveFont(16f));

	    mntmCarrito = new JMenuItem(ConstantesBotones.CARRITO);
	    mntmCarrito.setBackground(colorPalido);
	    mntmCarrito.setFont(Fuentes.REGULAR.deriveFont(16f));
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
	    menuBar.setPreferredSize(new Dimension(ANCHO, 30));
	    menuBar.add(Box.createHorizontalGlue());
	    menuBar.add(mnCerrarSesion);
	    menuBar.add(Box.createRigidArea(new Dimension(5, 0)));
	}

	public void menuAdmin() {
		menuBar.add(mnEmpleados, 0);
		mnEmpleados.add(mntmGestionEmp);
		
		
		menuBar.add(mnProductos, 1);
		mnProductos.add(mntmGestionProd);
		mnProductos.add(mntmGestionStock);

		menuBar.add(mnTransacciones, 2);
		mnTransacciones.add(mntmTransacciones);
		
		menuBar.add(mnUsuarios, 3);
		mnUsuarios.add(mntmGestionUsr);
		
		crearMenuBase();
	}

	public void menuComprador() {
		menuBar.add(mnTienda, 0);
		mnTienda.add(mntmShop);
		mnTienda.add(mntmCarrito);
		
		mnTienda.add(mntmCuenta);
		
		crearMenuBase();
	}

	public void quitarMenu() {
		menuBar.setPreferredSize(new Dimension(0, 0));
		menuBar.removeAll();
		menuBar.setVisible(false);
		menuBar.revalidate();
		menuBar.repaint();
	}

	@Override
	public void cargarPanel(JPanel panel) {
		scrlCont.setViewportView(panel);
		scrlCont.revalidate();
		scrlCont.repaint();
	}

	@Override
	public void hacerVisible() {
		setVisible(true);
	}

	@Override
	public void setControlador(Ctrl c) {
		
		mnCerrarSesion.addActionListener(c);
		mnCerrarSesion.addMouseListener(c);
		mnCerrarSesion.setActionCommand(ConstantesBotones.CERRAR_SESION);
		
		mntmGestionEmp.addActionListener(c);
		mntmGestionEmp.setActionCommand(ConstantesBotones.GESTION_EMPLEADOS);
		
		mntmGestionProd.addActionListener(c);
		mntmGestionProd.setActionCommand(ConstantesBotones.GESTION_PRODUCTOS);
		
		mntmGestionUsr.addActionListener(c);
		
		mntmTransacciones.addActionListener(c);
		mntmTransacciones.setActionCommand(ConstantesBotones.VER_TRANSACCIONES);
		
		mntmGestionStock.addActionListener(c);
		mntmGestionStock.setActionCommand(ConstantesBotones.GESTION_STOCK);
		
		mntmShop.addActionListener(c);
		mntmShop.setActionCommand(ConstantesBotones.COMPRAR);
		
		mntmCarrito.addActionListener(c);
		mntmCarrito.setActionCommand(ConstantesBotones.CARRITO);
		
		mntmCuenta.addActionListener(c);
		mntmCuenta.setActionCommand(ConstantesBotones.MI_CUENTA);
		
		scrlCont.addMouseListener(c);
	}
	
	public JScrollPane getScrlCont() {
		return scrlCont;
	}
	
	public JMenu getMnCerrarSesion() {
		return mnCerrarSesion;
	}
}
