package com.dam.view;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Empleado;
import com.dam.model.pojos.Producto;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * Panel de la vista de Gestión de Empleados (`VGestionEmp`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Esta vista provee las
 * herramientas de interfaz gráfica necesarias para que los administradores den
 * de alta nuevos miembros del personal, realicen búsquedas filtradas por
 * nombre, consulten el nivel de autorización y den de baja registros de la base
 * de datos de manera intuitiva.
 * </p>
 * * @author Zoe
 * 
 * @version 1.0
 */
public class VGestionEmp extends JPanel implements IPanels {
	/** Identificador único para el gestor de diseño o la navegación de paneles. */
	public static final String NAME = "VGestionEmp";

	/**
	 * Ancho útil calculado en píxeles para el panel según los márgenes de la
	 * ventana principal.
	 */
	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	/**
	 * Alto útil calculado en píxeles para el panel descontando la barra de menús.
	 */
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	/**
	 * Almacén secuencial de los objetos Empleado recuperados y renderizados en la
	 * cuadrícula.
	 */
	private ArrayList<Empleado> empleadosCargados = new ArrayList<>();

	/** Campo de texto destinado a la inserción del nombre del empleado. */
	private JTextField txtNombre;
	/** Campo de contraseña enmascarado para la clave de acceso del empleado. */
	private JPasswordField txtContrasenia;
	/** Campo de texto estructurado para el número telefónico de contacto. */
	private JTextField txtTel;
	/** Campo de texto destinado al Número de la Seguridad Social (NSS). */
	private JTextField txtNSeguridad;
	/**
	 * Campo de texto extendido para la inserción del código de cuenta bancaria
	 * IBAN.
	 */
	private JTextField txtIban;
	/** Botón encargado de procesar y disparar el registro de un nuevo empleado. */
	private JButton btnRegistrarEmp;
	/**
	 * Botón encargado de restablecer el estado inicial de los campos del
	 * formulario.
	 */
	private JButton btnLimpiar;
	/** Tabla interactiva que presenta los registros del personal contratado. */
	private JTable tblEmpleados;
	/**
	 * Modelo de datos estructurado para la gestión lógica de las celdas de la
	 * tabla.
	 */
	private DefaultTableModel dtmEmpleados;
	/**
	 * Panel de soporte con barras de desplazamiento verticales y horizontales para
	 * la tabla.
	 */
	private JScrollPane scrpEmpleados;
	/**
	 * Botón para llevar a cabo la revocación o eliminación del empleado
	 * seleccionado.
	 */
	private JButton btnEliminarEmp;
	/** Etiqueta informativa que precede a la cuadrícula de datos del personal. */
	private JLabel lblListado;
	/**
	 * Campo de texto para ingresar los criterios de búsqueda o filtrado analítico.
	 */
	private JTextField txtBuscarNombre;
	/** Etiqueta descriptiva para el componente de rango de autorización. */
	private JLabel lblAutorizacion;
	/** Selector numérico continuo para determinar el rango o rol de privilegios. */
	private JSpinner spnAutorizacion;
	/**
	 * Botón encargado de invocar la consulta de búsqueda según el filtro de texto.
	 */
	private JButton btnBuscarNombre;

	/**
	 * Constructor por defecto del panel de gestión de empleados. Inicializa las
	 * propiedades de la ventana y construye el árbol de componentes gráficos.
	 */
	public VGestionEmp() {
		configurarVentana();
		crearComponentes();
	}

	/**
	 * Define las propiedades físicas básicas del panel, tales como su tamaño
	 * absoluto, el color de fondo institucional y el nombre de identidad de la
	 * vista.
	 */
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setBackground(VPrincipal.colorPalido);
		setName(NAME);
	}

	/**
	 * Instancia, personaliza con fuentes específicas y posiciona de forma absoluta
	 * todos los componentes interactivos y etiquetas que conforman el formulario de
	 * gestión.
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Empleados");
		lblTitulo.setFont(Fuentes.BOLD.deriveFont(24f));
		lblTitulo.setForeground(VPrincipal.colorLetras);
		lblTitulo.setBounds(36, 23, 300, 25);
		add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(35, 59, 80, 26);
		lblNombre.setFont(Fuentes.MEDIUM.deriveFont(20f));
		lblNombre.setForeground(VPrincipal.colorLetras);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(225, 59, 200, 26);
		txtNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtNombre.setForeground(VPrincipal.colorLetras);
		add(txtNombre);

		JLabel lblTel = new JLabel("Teléfono:");
		lblTel.setBounds(35, 173, 95, 26);
		lblTel.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblTel.setForeground(VPrincipal.colorLetras);
		add(lblTel);

		txtTel = new JTextField();
		txtTel.setBounds(225, 173, 200, 26);
		txtTel.setForeground(VPrincipal.colorLetras);
        txtTel.setFont(Fuentes.REGULAR.deriveFont(16f));
		add(txtTel);

		JLabel lblContrasenia = new JLabel("Contraseña:");
		lblContrasenia.setBounds(35, 97, 112, 26);
		lblContrasenia.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblContrasenia.setForeground(VPrincipal.colorLetras);
		add(lblContrasenia);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(225, 97, 200, 26);
		txtContrasenia.setForeground(VPrincipal.colorLetras);
        txtContrasenia.setFont(Fuentes.REGULAR.deriveFont(16f));
		add(txtContrasenia);

		JLabel lblNSeguridad = new JLabel("N. Seguridad Social:");
		lblNSeguridad.setHorizontalAlignment(SwingConstants.LEFT);
		lblNSeguridad.setBounds(35, 211, 190, 26);
		lblNSeguridad.setFont(Fuentes.MEDIUM.deriveFont(20f));
		lblNSeguridad.setForeground(VPrincipal.colorLetras);
		add(lblNSeguridad);

		txtNSeguridad = new JTextField();
		txtNSeguridad.setBounds(225, 211, 200, 26);
		txtNSeguridad.setFont(Fuentes.REGULAR.deriveFont(16f));
		add(txtNSeguridad);

		JLabel lblIban = new JLabel("IBAN:");
		lblIban.setBounds(35, 135, 80, 26);
		lblIban.setFont(Fuentes.MEDIUM.deriveFont(20f));
		lblIban.setForeground(VPrincipal.colorLetras);
		add(lblIban);

		txtIban = new JTextField();
		txtIban.setBounds(225, 135, 325, 26);
		txtIban.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtIban.setForeground(VPrincipal.colorLetras);
		add(txtIban);

		btnRegistrarEmp = new JButton(ConstantesBotones.REGISTRAR_EMPLEADO);
		btnRegistrarEmp.setBounds(225, 310, 193, 30);
		btnRegistrarEmp.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnRegistrarEmp.setForeground(VPrincipal.colorLetras);
		btnRegistrarEmp.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnRegistrarEmp);

		btnLimpiar = new JButton(ConstantesBotones.LIMPIAR);
		btnLimpiar.setBounds(428, 310, 100, 30);
		btnLimpiar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnLimpiar.setForeground(VPrincipal.colorLetras);
		btnLimpiar.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnLimpiar);

		lblListado = new JLabel("Listado de Empleados:");
		lblListado.setBounds(577, 102, 220, 20);
		lblListado.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblListado.setForeground(VPrincipal.colorLetras);
		add(lblListado);

		scrpEmpleados = new JScrollPane();
		scrpEmpleados.setBounds(577, 136, 827, 688);
		scrpEmpleados.getViewport().setBackground(VPrincipal.colorVibrante);
		add(scrpEmpleados);

		tblEmpleados = new JTable() {
			@Override
			public String getToolTipText(MouseEvent e) {
				int fila = rowAtPoint(e.getPoint());
				if (fila != -1 && empleadosCargados != null && fila < empleadosCargados.size()) {
					int autorizacion = empleadosCargados.get(fila).getAutorizacion();
					return autorizacion == Ctrl.ADMIN ? "Administrador" : "Empleado";
				}
				return null;
			}
		};
		tblEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblEmpleados.getTableHeader().setBackground(VPrincipal.colorNaranjaPatito);
		tblEmpleados.getTableHeader().setForeground(VPrincipal.colorLetras);
		tblEmpleados.getTableHeader().setFont(Fuentes.MEDIUM.deriveFont(16f));
		tblEmpleados.setFont(Fuentes.REGULAR.deriveFont(14f));
		tblEmpleados.setForeground(VPrincipal.colorLetras);
		tblEmpleados.setRowHeight(28);
		scrpEmpleados.setViewportView(tblEmpleados);
		configurarTabla();

		btnEliminarEmp = new JButton(ConstantesBotones.ELIMINAR_EMPLEADO);
		btnEliminarEmp.setBounds(577, 845, 175, 30);
		btnEliminarEmp.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnEliminarEmp.setForeground(VPrincipal.colorLetras);
		btnEliminarEmp.setBackground(VPrincipal.colorNaranjaPatito);
		btnEliminarEmp.setEnabled(false);
		add(btnEliminarEmp);

		JLabel lblBuscarNombre = new JLabel("Nombre:");
		lblBuscarNombre.setBounds(577, 62, 80, 25);
		lblBuscarNombre.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblBuscarNombre.setForeground(VPrincipal.colorLetras);
		add(lblBuscarNombre);

		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(653, 65, 118, 18);
		txtBuscarNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtBuscarNombre.setForeground(VPrincipal.colorLetras);
		add(txtBuscarNombre);

		btnBuscarNombre = new JButton(ConstantesBotones.BUSCAR_EMPLEADO);
		btnBuscarNombre.setBounds(781, 64, 161, 20);
		btnBuscarNombre.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnBuscarNombre.setForeground(VPrincipal.colorLetras);
		btnBuscarNombre.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnBuscarNombre);

		lblAutorizacion = new JLabel("Autorización:");
		lblAutorizacion.setBounds(36, 249, 144, 24);
		lblAutorizacion.setFont(Fuentes.MEDIUM.deriveFont(20f));
		lblAutorizacion.setForeground(VPrincipal.colorLetras);
		add(lblAutorizacion);

		spnAutorizacion = new JSpinner();
		spnAutorizacion.setModel(new SpinnerNumberModel(2, 1, 2, 1));
		spnAutorizacion.setFont(Fuentes.REGULAR.deriveFont(14f));
		spnAutorizacion.setForeground(VPrincipal.colorLetras);
		spnAutorizacion.setBounds(225, 249, 44, 35);
		((JSpinner.DefaultEditor) spnAutorizacion.getEditor()).getTextField().setEditable(false);
		((JSpinner.DefaultEditor) spnAutorizacion.getEditor()).getTextField().setFocusable(false);
		add(spnAutorizacion);

	}

	/**
	 * Configura el modelo interno de la tabla para impedir la edición directa de
	 * celdas, restringe la reordenación de cabeceras mediante arrastre y determina
	 * los títulos de columna con sus respectivos anchos.
	 */
	private void configurarTabla() {
		dtmEmpleados = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblEmpleados.getTableHeader().setReorderingAllowed(false);
		tblEmpleados.setModel(dtmEmpleados);

		dtmEmpleados.addColumn("Nombre");
		dtmEmpleados.addColumn("Teléfono");
		dtmEmpleados.addColumn("N. Seguridad");
		dtmEmpleados.addColumn("IBAN");

		tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(160);
		tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(90);
		tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(130);
		tblEmpleados.getColumnModel().getColumn(3).setPreferredWidth(220);
	}

	/**
	 * Despeja las selecciones visuales e inyecta la colección actualizada de
	 * empleados en la tabla. Si la lista suministrada está vacía, emite un aviso
	 * informativo. * @param empleados Estructura {@link ArrayList} que alberga a
	 * los objetos {@link Empleado} vigentes.
	 */
	public void cargarTabla(ArrayList<Empleado> empleados) {
		tblEmpleados.clearSelection();
		dtmEmpleados.getDataVector().clear();
		empleadosCargados = empleados;

		if (empleados.size() != 0) {
			clearTable();
			Object[] row = new Object[5];
			for (Empleado emp : empleados) {
				row[0] = emp.getNombre();
				row[1] = emp.getTel();
				row[2] = emp.getnSeguridad();
				row[3] = emp.getIban();

				dtmEmpleados.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se han encontrado items con los filtros seleccionados", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Remueve de forma secuencial cada una de las filas activas en el modelo de la
	 * tabla.
	 */
	private void clearTable() {
		int r = dtmEmpleados.getRowCount();
		for (int i = 0; i < r; i++) {
			// System.out.println(i);
			dtmEmpleados.removeRow(0);
		}
	}

	/**
	 * Altera el estado de activación interactiva del botón encargado de suprimir
	 * empleados. * @param b {@code true} para habilitar el botón; {@code false}
	 * para deshabilitarlo.
	 */
	public void setEliminarEnabled(boolean b) {
		btnEliminarEmp.setEnabled(b);
	}

	// TODO: validar datos
	/**
	 * Sanea y comprueba exhaustivamente la validez sintáctica de las entradas
	 * recolectadas en el formulario. Genera diálogos contextuales de error si se
	 * detectan campos vacíos o tipos numéricos inválidos. * @return Una nueva
	 * instancia mapeada de {@link Empleado} si los datos cumplen el criterio de
	 * validación; o {@code null} si se detectan anomalías en el llenado.
	 */
	public Empleado obtenerDatosFormulario() {
		String nombre = txtNombre.getText().trim();
		String contra = new String(txtContrasenia.getPassword()).trim();
		String telStr = txtTel.getText().trim();
		String nSeguridad = txtNSeguridad.getText().trim();
		String iban = txtIban.getText().trim();
		int autorizacion = (int) spnAutorizacion.getValue();
		;

		boolean valid = true;

		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		} else if (contra.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		} else if (telStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El teléfono es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		} else if (nSeguridad.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El número de seguridad social es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		} else if (iban.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El IBAN es obligatorio.", "Error de datos", JOptionPane.ERROR_MESSAGE);
			valid = false;
		} else if (!telStr.isEmpty()) {
			try {
				Integer.parseInt(telStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "El teléfono debe ser un número.", "Error de datos",
						JOptionPane.ERROR_MESSAGE);
				valid = false;
			}
		}

		if (valid) {
			return new Empleado(autorizacion, nombre, contra, Integer.parseInt(telStr), true, nSeguridad, iban);
		} else {
			return null;
		}
	}

	/**
	 * Restablece las cadenas de caracteres vacías en todos los campos editables del
	 * registro.
	 */
	public void limpiarDatos() {
		txtNombre.setText("");
		txtContrasenia.setText("");
		txtTel.setText("");
		txtNSeguridad.setText("");
		txtIban.setText("");
	}

	/**
	 * Obtiene el criterio de búsqueda alfabético ingresado por el usuario en el
	 * campo de filtrado. * @return Cadena de caracteres con el texto de consulta.
	 */
	public String getConsulta() {
		return txtBuscarNombre.getText();
	}

	/**
	 * Proporciona el componente de tabla donde se visualiza el personal. * @return
	 * El objeto {@link JTable} interno de la vista.
	 */
	public JTable getTblEmpleados() {
		return tblEmpleados;
	}

	/**
	 * Facilita el acceso al modelo de gestión de selecciones asignado a la tabla de
	 * empleados. * @return La instancia de {@link ListSelectionModel} en uso.
	 */
	public ListSelectionModel getSelectionModel() {
		return tblEmpleados.getSelectionModel();
	}

	// En VGestionEmp
	/**
	 * Captura la cadena de texto con el nombre del empleado que se encuentra
	 * seleccionado en la fila activa. * @return El nombre del empleado como
	 * {@link String}.
	 */
	public String getNombreSeleccionado() {
		return (String) tblEmpleados.getValueAt(tblEmpleados.getSelectedRow(), 0);
	}

	/**
	 * Vincula de forma centralizada la lógica de los botones y el gestor de
	 * selecciones de la tabla a las directivas del controlador principal. * @param
	 * c Instancia de la clase controladora central {@link Ctrl}.
	 */
	@Override
	public void setControlador(Ctrl c) {
		btnRegistrarEmp.addActionListener(c);
		btnRegistrarEmp.setActionCommand(ConstantesBotones.REGISTRAR_EMPLEADO);

		btnLimpiar.addActionListener(c);
		btnLimpiar.setActionCommand(ConstantesBotones.LIMPIAR);

		btnEliminarEmp.addActionListener(c);
		btnEliminarEmp.setActionCommand(ConstantesBotones.ELIMINAR_EMPLEADO);

		btnBuscarNombre.addActionListener(c);
		btnBuscarNombre.setActionCommand(ConstantesBotones.BUSCAR_EMPLEADO);

		tblEmpleados.getSelectionModel().addListSelectionListener(c);
	}

	/**
	 * Proporciona el componente de la tabla (mantiene concordancia con la
	 * referencia de empleados). * @return El objeto {@link JTable} representativo.
	 */
	public JTable getTblProductos() {
		return tblEmpleados;
	}
}