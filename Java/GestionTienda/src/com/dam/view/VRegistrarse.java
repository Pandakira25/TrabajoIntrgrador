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

/**
 * Panel de la vista de Registro de Compradores (`VRegistrarse`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Esta vista proporciona un formulario 
 * interactivo destinado al alta de nuevos usuarios compradores en el sistema. Realiza 
 * validaciones de datos en tiempo de ejecución antes de consolidar el registro.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class VRegistrarse extends JPanel implements IPanels {
	/** Identificador único para el gestor de diseño o la navegación de paneles. */
	public static final String NAME = "VRegistrarse";

	/** Ancho útil en píxeles calculado para este panel según las dimensiones de la ventana principal. */
	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	/** Alto útil en píxeles calculado para este panel considerando los márgenes de ventana. */
	private static final int ALTO  = VPrincipal.ALTO  - VPrincipal.insetsT - VPrincipal.insetsB;

	/** Campo de texto destinado al ingreso del nombre del nuevo comprador. */
	private JTextField txtNombre;
	/** Campo de contraseña enmascarado para la clave del nuevo perfil. */
	private JPasswordField txtContrasenia;
	/** Campo de texto estructurado para capturar el teléfono del usuario. */
	private JTextField txtTel;
	/** Campo de texto destinado a almacenar la dirección física de correspondencia. */
	private JTextField txtDireccion;
	/** Campo de texto destinado a la captura del número de tarjeta bancaria. */
	private JTextField txtNTarjeta;
	/** Botón encargado de validar y procesar la solicitud de alta en el sistema. */
	private JButton btnRegistrar;
	/** Botón encargado de abortar el flujo actual y regresar a la vista previa. */
	private JButton btnCancelar;

	/**
	 * Constructor por defecto del panel de registro.
	 * Inicializa las propiedades de dimensionamiento geométrico y ensambla los componentes.
	 */
	public VRegistrarse() {
		configurarVentana();
		crearComponentes();
	}

	/**
	 * Configura los parámetros espaciales iniciales del panel, asigna el fondo cromático
	 * pálido y establece el nombre clave de identidad del contenedor.
	 */
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setBackground(VPrincipal.colorPalido);
		setName(NAME);
	}

	/**
	 * Instancia, personaliza con estilos tipográficos y posiciona de manera absoluta
	 * mediante coordenadas fijas todos los componentes interactivos y etiquetas del formulario.
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Registrarse como Comprador");
		lblTitulo.setFont(Fuentes.BOLD.deriveFont(20f));
		lblTitulo.setForeground(VPrincipal.colorLetras);
		lblTitulo.setBounds(15, 16, 350, 25);
		add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(15, 65, 90, 20);
		lblNombre.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblNombre.setForeground(VPrincipal.colorLetras);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(128, 62, 250, 26);
		txtNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
		add(txtNombre);

		JLabel lblContrasenia = new JLabel("Contraseña:");
		lblContrasenia.setBounds(15, 105, 90, 20);
		lblContrasenia.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblContrasenia.setForeground(VPrincipal.colorLetras);
		add(lblContrasenia);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(128, 102, 250, 26);
		txtContrasenia.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtContrasenia.setForeground(VPrincipal.colorLetras);
		add(txtContrasenia);

		JLabel lblTel = new JLabel("Teléfono:");
		lblTel.setBounds(15, 145, 90, 20);
		lblTel.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblTel.setForeground(VPrincipal.colorLetras);
		add(lblTel);

		txtTel = new JTextField();
		txtTel.setBounds(128, 142, 150, 26);
		txtTel.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtTel.setForeground(VPrincipal.colorLetras);
		add(txtTel);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setBounds(15, 185, 90, 20);
		lblDireccion.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblDireccion.setForeground(VPrincipal.colorLetras);
		add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(128, 182, 450, 26);
		txtDireccion.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtDireccion.setForeground(VPrincipal.colorLetras);
		add(txtDireccion);

		JLabel lblNTarjeta = new JLabel("N. Tarjeta:");
		lblNTarjeta.setBounds(15, 225, 90, 20);
		lblNTarjeta.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblNTarjeta.setForeground(VPrincipal.colorLetras);
		add(lblNTarjeta);

		txtNTarjeta = new JTextField();
		txtNTarjeta.setBounds(128, 222, 250, 26);
		txtNTarjeta.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtNTarjeta.setForeground(VPrincipal.colorLetras);
		add(txtNTarjeta);

		btnRegistrar = new JButton(ConstantesBotones.REGISTRARSE);
		btnRegistrar.setBounds(128, 271, 130, 30);
		btnRegistrar.setBackground(VPrincipal.colorNaranjaPatito);
		btnRegistrar.setForeground(VPrincipal.colorLetras);
		btnRegistrar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		add(btnRegistrar);

		btnCancelar = new JButton(ConstantesBotones.CANCELAR);
		btnCancelar.setBounds(279, 271, 130, 30);
		btnCancelar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnCancelar.setForeground(VPrincipal.colorLetras);
		btnCancelar.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnCancelar);
	}

	/**
	 * Restablece y vacía el texto contenido en todos los campos editables del formulario.
	 */
	public void limpiarDatos() {
		txtNombre.setText("");
		txtContrasenia.setText("");
		txtTel.setText("");
		txtDireccion.setText("");
		txtNTarjeta.setText("");
	}

	/**
	 * Conecta los botones de la vista al controlador del patrón MVC, configurando
	 * adicionalmente sus respectivos comandos de acción.
	 * * @param c Instancia de la clase controladora principal {@link Ctrl}.
	 */
	@Override
	public void setControlador(Ctrl c) {
		btnRegistrar.addActionListener(c);
		btnRegistrar.setActionCommand(ConstantesBotones.REGISTRARSE);

		btnCancelar.addActionListener(c);
		btnCancelar.setActionCommand(ConstantesBotones.CANCELAR);
	}
	
	
	//TODO: Comprobaciones
	/**
	 * Extrae, sanea de espacios en blanco y valida la integridad de los datos ingresados en el formulario.
	 * <p>
	 * Evalúa que ningún campo requerido se encuentre vacío y comprueba mediante captura de excepciones
	 * que la cadena del teléfono corresponda a un formato numérico entero válido. Despliega ventanas emergentes
	 * informativas en caso de error.
	 * </p>
	 * * @return Un objeto {@link Comprador} debidamente instanciado con los valores del formulario si pasan 
	 * la validación; o {@code null} en caso de detectarse incongruencias en el rellenado.
	 */
	public Comprador obtenerDatos() {
		try {
			return new Comprador(
					txtNombre.getText(),
					new String(txtContrasenia.getPassword()),
					txtTel.getText(),
					txtDireccion.getText(),
					txtNTarjeta.getText());
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error de datos", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}