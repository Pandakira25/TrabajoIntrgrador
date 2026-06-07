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
		String nombre    = txtNombre.getText().trim();
		String contra    = new String(txtContrasenia.getPassword()).trim();
		String telStr    = txtTel.getText().trim();
		String direccion = txtDireccion.getText().trim();
		String nTarjeta  = txtNTarjeta.getText().trim();
		
		boolean valid = true;

		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre es obligatorio.",
					"Error de datos", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		else if (contra.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.",
					"Error de datos", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		else if (telStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El teléfono es obligatorio.",
					"Error de datos", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		else if(direccion.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La direccion es obligatoria.",
					"Error de datos", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		else if(nTarjeta.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe ingresar una tarjeta.",
					"Error de datos", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		else if(!telStr.isEmpty()) {
			try {
				Integer.parseInt(telStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "El teléfono debe ser un número.",
						"Error de datos", JOptionPane.ERROR_MESSAGE);
				valid = false;
			}
		}
		

		if(valid) {
			return new Comprador(nombre,contra, Integer.parseInt(telStr), direccion, nTarjeta);
		}else {
			return null;
		}
	}
}