package com.dam.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.dam.ctrl.Ctrl;

/**
 * Clase de la interfaz gráfica que representa el formulario de inicio de sesión (`VloginForm`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Proporciona un entorno visual adaptado 
 * con campos de texto específicos para capturar de forma segura las credenciales del usuario 
 * (nombre de usuario y contraseña) y botones para desencadenar acciones de autenticación o registro.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class VloginForm extends JPanel implements IPanels {
	/** Identificador único asignado al panel para la gestión y alternancia en el layout de la aplicación. */
	public static final String NAME = "VloginForm";

	/** Anchura estática base de referencia para el diseño del componente. */
	public static final int WIDTH = 600;
	/** Altura estática base de referencia para el diseño del componente. */
	public static final int HEIGHT = 800;

	/** Anchura neta calculada del panel basada en las dimensiones de la ventana principal y sus insets. */
	private static final int ANCHO = VPrincipal.ANCHO 
			- VPrincipal.insetsL - VPrincipal.insetsR;
	/** Altura neta calculada del panel basada en las dimensiones de la ventana principal, insets y menú. */
	private static final int ALTO = VPrincipal.ALTO 
			- VPrincipal.insetsT - VPrincipal.insetsB 
			- VPrincipal.menuH;

	/** Etiqueta que renderiza el encabezado o título principal del formulario. */
	private JLabel lblTitulo;
	/** Etiqueta informativa para identificar el campo del nombre de usuario. */
	private JLabel lblUsuario;
	/** Etiqueta informativa para identificar el campo de la contraseña. */
	private JLabel lblContrasena;
	/** Campo de texto para la introducción del alias o nombre de usuario de acceso. */
	private JTextField txtUsuario;
	/** Campo de texto enmascarado para la introducción segura de la contraseña. */
	private JPasswordField txtContrasenia;
	/** Botón disparador para procesar e iniciar la validación de las credenciales introducidas. */
	private JButton btnEntrar;
	/** Botón disparador para redirigir al flujo de creación o alta de una nueva cuenta. */
	private JButton btnRegistrarse;

	/**
	 * Constructor por defecto de la clase.
	 * Coordina de forma secuencial la definición de las propiedades del lienzo gráfico y la construcción de widgets.
	 */
	public VloginForm() {
		configurarVentana();
		crearComponentes();
	}

	/**
	 * Inicializa, dimensiona y añade al layout absoluto (nulo) todos los componentes visuales del formulario.
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);
		
		lblTitulo = new JLabel("Iniciar sesión");
		lblTitulo.setBounds(591, 273, 129, 28);
		lblTitulo.setFont(Fuentes.MEDIUM.deriveFont(20f));
		lblTitulo.setForeground(VPrincipal.colorLetras);
		add(lblTitulo);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(506, 332, 90, 25);
		lblUsuario.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblUsuario.setForeground(VPrincipal.colorLetras);
		add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(615, 331, 210, 25);
		txtUsuario.setFont(Fuentes.MEDIUM.deriveFont(16f));
		txtUsuario.setForeground(VPrincipal.colorLetras);
		add(txtUsuario);

		lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(506, 373, 90, 25);
		lblContrasena.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblContrasena.setForeground(VPrincipal.colorLetras);
		add(lblContrasena);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(615, 372, 210, 25);
		txtContrasenia.setFont(Fuentes.MEDIUM.deriveFont(16f));
		txtContrasenia.setForeground(VPrincipal.colorLetras);
		add(txtContrasenia);

		btnEntrar = new JButton(ConstantesBotones.ENTRAR);
		btnEntrar.setBounds(517, 418, 95, 30);
		btnEntrar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnEntrar.setForeground(VPrincipal.colorLetras);
		btnEntrar.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnEntrar);

		btnRegistrarse = new JButton(ConstantesBotones.REGISTRARSE);
		btnRegistrarse.setBounds(637, 418, 122, 30);
		btnRegistrarse.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnRegistrarse.setForeground(VPrincipal.colorLetras);
		btnRegistrarse.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnRegistrarse);
	}

	/**
	 * Configura las propiedades geométricas del panel.
	 * Define el nombre identificativo, la dimensión calculada y el color de fondo persistente.
	 */
	@Override
	public void configurarVentana() {
		setName(NAME);
		setSize(ANCHO, ALTO);
		setBackground(VPrincipal.colorPalido);
	}

	/**
	 * Vincula el objeto controlador unificado a los disparadores de eventos de la vista de login.
	 * * @param c Instancia de la clase controladora principal {@link Ctrl}.
	 */
	@Override
	public void setControlador(Ctrl c) {
		btnEntrar.addActionListener(c);
		btnEntrar.setActionCommand(ConstantesBotones.ENTRAR);

		btnRegistrarse.addActionListener(c);
		btnRegistrarse.setActionCommand(ConstantesBotones.REGISTRARSE);
	}
	
	/**
	 * Extrae los datos textuales de los campos de entrada del formulario de login.
	 * * @return Un array unidimensional de cadenas de texto ({@code String[]}) de tamaño 2, donde:
	 * `index[0]` contiene el nombre de usuario y `index[1]` la contraseña introducida.
	 */
	public String[] getUsr() {
		String usr [] = {txtUsuario.getText(), txtContrasenia.getText()};
		return usr;
	}

	/**
	 * Proporciona acceso directo al campo de texto del nombre de usuario.
	 * * @return El componente gráfico {@link JTextField} asignado al usuario.
	 */
	public JTextField getTxtUsuario() {
		return txtUsuario;
	}
	
	/**
	 * Modifica o inicializa el contenido de texto visible del campo usuario.
	 * * @param txt Cadena de texto con el nuevo valor a renderizar en el campo.
	 */
	public void setTxtUsuario(String txt) {
		txtUsuario.setText(txt);
	}

	/**
	 * Proporciona acceso directo al campo de texto oculto de la contraseña.
	 * * @return El componente gráfico {@link JPasswordField} asignado a la contraseña.
	 */
	public JPasswordField getTxtContrasenia() {
		return txtContrasenia;
	}
	
	/**
	 * Proporciona acceso al botón encargado de disparar la acción de entrada al sistema.
	 * * @return El componente gráfico {@link JButton} asociado a la acción de entrar.
	 */
	public JButton getBtnEntrar() {
		return btnEntrar;
	}
}