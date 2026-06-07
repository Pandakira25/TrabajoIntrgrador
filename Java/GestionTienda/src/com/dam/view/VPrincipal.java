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

/**
 * Ventana Principal de la aplicación (`VPrincipal`).
 * <p>
 * Hereda de {@link JFrame} e implementa la interfaz {@link IFrames}. Actúa como el contenedor 
 * maestro de la interfaz gráfica de usuario (GUI), gestionando dinámicamente la barra de menús 
 * y la carga de los diferentes paneles internos según el rol del usuario autenticado 
 * (Administrador, Empleado o Comprador).
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class VPrincipal extends JFrame implements IFrames {
	/** Ancho por defecto de la ventana principal en píxeles. */
	public static final int ANCHO = 1440;
	/** Alto por defecto de la ventana principal en píxeles. */
	public static final int ALTO = 1024;

	/** Margen (inset) derecho de la ventana obtenido en tiempo de ejecución. */
	public static int insetsR;
	/** Margen (inset) izquierdo de la ventana obtenido en tiempo de ejecución. */
	public static int insetsL;
	/** Margen (inset) superior de la ventana obtenido en tiempo de ejecución. */
	public static int insetsT;
	/** Margen (inset) inferior de la ventana obtenido en tiempo de ejecución. */
	public static int insetsB;
	/** Altura calculada o reservada para la barra de menú. */
	public static int menuH;

	/** Constante numérica que identifica el rol de Administrador. */
	public static final int ADMIN = 1;
	/** Constante numérica que identifica el rol de Empleado. */
	public static final int EMPLEADO = 2;
	/** Constante numérica que identifica el rol de Comprador. */
	public static final int COMPRADOR = 3;
	
	/** Color corporativo vibrante para barras de menú y elementos destacados. */
	public static Color colorVibrante = new Color(226, 245, 114);
	/** Color corporativo pálido utilizado como fondo base en los paneles secundarios. */
	public static Color colorPalido = new Color(240, 250, 180);
	/** Color secundario naranja patito para acentos visuales específicos. */
	public static Color colorNaranjaPatito = new Color(245, 199, 114);
	/** Color verde fuerte complementario para estados o botones destacados. */
	public static Color colorVerdeFuerte = new Color(160,245,114);
	/** Color oscuro estandarizado para la tipografía y rotulados tipográficos. */
	public static Color colorLetras = new Color(57,67,5);

	/** Contenedor principal de la barra de herramientas y menús de navegación. */
	private JMenuBar menuBar;
	/** Ítem de menú que redirige al catálogo de la tienda. */
	private JMenuItem mntmShop;
	/** Ítem de menú para abrir la sección del carrito de compras. */
	private JMenuItem mntmCarrito;
	/** Ítem de menú destinado al panel de gestión del personal de la empresa. */
	private JMenuItem mntmGestionEmp;
	/** Ítem de menú destinado al panel de alta y edición de productos. */
	private JMenuItem mntmGestionProd;
	/** Ítem de menú para el control físico de existencias o inventario. */
	private JMenuItem mntmGestionStock;
	/** Ítem de menú para la consulta histórica de auditorías o facturas. */
	private JMenuItem mntmTransacciones;
	/** Menú de acción directa que gestiona la desconexión del usuario actual. */
	private JMenu mnCerrarSesion;
	/** Ítem de menú destinado al panel de administración de perfiles de usuarios. */
	private JMenuItem mntmGestionUsr;
	/** Panel de soporte con barras de desplazamiento donde se montan los paneles dinámicos. */
	private JScrollPane scrlCont;

	/** Menú desplegable para agrupar las acciones comerciales de la tienda. */
	private JMenu mnTienda;

	/** Menú desplegable para agrupar los comandos de administración de empleados. */
	private JMenu mnEmpleados;

	/** Menú desplegable para englobar el control de catálogo e inventarios. */
	private JMenu mnProductos;

	/** Menú desplegable para el acceso a listados analíticos de movimientos de caja. */
	private JMenu mnTransacciones;
	/** Ítem de menú que permite al cliente consultar y modificar sus datos de cuenta. */
	private JMenuItem mntmCuenta;
	/** Menú desplegable para agrupar la gestión de usuarios del sistema. */
	private JMenu mnUsuarios;

	/**
	 * Constructor de la ventana principal.
	 * Inicializa la configuración geométrica de la ventana, instancia los menús y
	 * ensambla los contenedores primarios de la aplicación.
	 */
	public VPrincipal() {
		configurarVentana();
		crearMenu();
		crearComponentes();
	}

	/**
	 * Inicializa el contenedor de scroll principal asignándole un color gris base, 
	 * y lo acopla en la región central del esquema de diseño BorderLayout.
	 */
	@Override
	public void crearComponentes() {
		scrlCont = new JScrollPane();
		scrlCont.setBackground(new Color(240, 240, 240));
		getContentPane().add(scrlCont, BorderLayout.CENTER);
	}

	/**
	 * Instancia por separado cada menú desplegable e ítem de selección rápida, aplicando
	 * los colores corporativos estandarizados y asignando tipografías de tamaño dinámico.
	 */
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

	/**
	 * Configura el título del marco, establece la finalización del proceso al cerrar, 
	 * fija el tamaño absoluto y calcula los insets nativos antes de centrar la pantalla.
	 */
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
	
	/**
	 * Calcula las coordenadas del monitor de destino basándose en el Toolkit nativo
	 * para posicionar el marco perfectamente centrado en pantalla.
	 */
	private void centerWindow() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = new Dimension(ANCHO, ALTO);
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	/**
	 * Genera e inicializa la estructura de soporte de la barra de menús, situando 
	 * de forma alineada hacia la derecha el botón disparador de cierre de sesión.
	 */
	public void crearMenuBase() {
	    menuBar.setVisible(true);
	    menuBar.setPreferredSize(new Dimension(ANCHO, 30));
	    menuBar.add(Box.createHorizontalGlue());
	    menuBar.add(mnCerrarSesion);
	    menuBar.add(Box.createRigidArea(new Dimension(5, 0)));
	}

	/**
	 * Acopla de manera ordenada en las posiciones primarias de la barra de menús 
	 * las opciones exclusivas del perfil directivo o de administración.
	 */
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

	/**
	 * Ensambla en la barra de herramientas principal los accesos y menús interactivos 
	 * que corresponden a las opciones comerciales habilitadas para el Comprador.
	 */
	public void menuComprador() {
		menuBar.add(mnTienda, 0);
		mnTienda.add(mntmShop);
		mnTienda.add(mntmCarrito);
		
		mnTienda.add(mntmCuenta);
		
		crearMenuBase();
	}

	/**
	 * Oculta la barra de menús y vacía por completo todos sus componentes internos, 
	 * forzando un redibujado estricto en la interfaz gráfica.
	 */
	public void quitarMenu() {
		menuBar.setPreferredSize(new Dimension(0, 0));
		menuBar.removeAll();
		menuBar.setVisible(false);
		menuBar.revalidate();
		menuBar.repaint();
	}

	/**
	 * Reemplaza el contenido visual visible del área central inyectando un nuevo panel
	 * en el Viewport del contenedor con soporte de scroll.
	 * * @param panel El subcontenedor {@link JPanel} destinado a ser renderizado en pantalla.
	 */
	@Override
	public void cargarPanel(JPanel panel) {
		scrlCont.setViewportView(panel);
		scrlCont.revalidate();
		scrlCont.repaint();
	}

	/**
	 * Modifica el estado del Frame principal para presentarlo de forma visible en el escritorio.
	 */
	@Override
	public void hacerVisible() {
		setVisible(true);
	}

	/**
	 * Enlaza de manera centralizada la totalidad de los ítems de menú e interceptores 
	 * interactivos del Frame a la lógica de escucha del controlador de la aplicación.
	 * * @param c Instancia de la clase controladora centralizada {@link Ctrl}.
	 */
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
	
	/**
	 * Proporciona el componente de scroll principal donde se alojan las vistas dinámicas.
	 * * @return El contenedor general {@link JScrollPane}.
	 */
	public JScrollPane getScrlCont() {
		return scrlCont;
	}
	
	/**
	 * Proporciona el componente de menú encargado de la desconexión del sistema.
	 * * @return El control de tipo {@link JMenu} correspondiente.
	 */
	public JMenu getMnCerrarSesion() {
		return mnCerrarSesion;
	}
}