package com.dam.view;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Producto;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

/**
 * Clase de la interfaz gráfica que representa el panel de administración del catálogo (`VGestionProd`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Esta vista proporciona un entorno unificado 
 * con un formulario de inserción/edición para controlar el ciclo de vida de las mercancías, una sección 
 * de filtrado avanzado multi-criterio (nombre, rango de precio, categoría) y un listado interactivo en forma de 
 * tabla con soporte para tooltips dinámicos que reflejan el estado de activación de cada artículo.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class VGestionProd extends JPanel implements IPanels {
	/** Identificador único asignado al panel para la gestión y alternancia en el layout de la aplicación. */
	public static final String NAME = "VGestionProd";

	/** Anchura neta calculada del panel basada en las dimensiones de la ventana principal y sus insets. */
	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	/** Altura neta calculada del panel basada en las dimensiones de la ventana principal, insets y menú. */
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	/** Colección indexada que almacena temporalmente las referencias de tipo {@link Producto} cargadas en la tabla. */
	private ArrayList<Producto> productosCargados = new ArrayList<>();
	/** Almacena el código de identificación único del artículo seleccionado en la vista; {@code -1} si no hay ninguno. */
	private int idSeleccionado = -1;

	/** Campo de texto destinado a la introducción o modificación del nombre comercial del producto. */
	private JTextField txtNombre;
	/** Campo de texto destinado a la introducción o modificación del sector o categoría del producto. */
	private JTextField txtCategoria;
	/** Campo de texto destinado a la introducción o modificación del precio de venta unitario. */
	private JTextField txtPrecio;
	/** Campo de texto destinado a la introducción o modificación del volumen físico de unidades iniciales. */
	private JTextField txtStock;
	/** Campo de texto extendido destinado a la introducción o modificación de la descripción del artículo. */
	private JTextField txtDescripcion;
	/** Botón disparador encargado de procesar la inserción de un nuevo artículo en la persistencia. */
	private JButton btnAgregarProd;
	/** Botón disparador encargado de guardar los cambios sobre un artículo seleccionado previamente. */
	private JButton btnModificarProd;
	/** Botón disparador encargado de blanquear el formulario e inicializar el estado de la vista. */
	private JButton btnLimpiar;
	/** Componente gráfico de tabla para renderizar los detalles analíticos de los productos mapeados. */
	private JTable tblProductos;
	/** Modelo de datos subyacente para el control estructurado de filas y columnas de la tabla. */
	private DefaultTableModel dtmProductos;
	/** Contenedor con barras de desplazamiento para albergar y permitir la navegación sobre la tabla. */
	private JScrollPane scrpProductos;
	/** Botón disparador encargado de inhabilitar o retirar lógicamente un producto del comercio. */
	private JButton btnDeshabilitarProd;
	/** Campo de texto destinado a introducir la cadena o patrón para el filtrado por nombre de artículos. */
	private JTextField txtNombreBuscar;
	/** Componente de selección desplegable para acotar los artículos por rangos o umbrales económicos. */
	private JComboBox<String> cmbPrecioBuscar;
	/** Componente de selección desplegable para filtrar los artículos por su sector o categoría. */
	private JComboBox<String> cmbCategoriaBuscar;
	/** Modelo de datos dinámico asignado para la gestión del contenido del combo box de categorías. */
	private DefaultComboBoxModel<String> dcbmCategoria;
	/** Botón disparador encargado de iniciar la búsqueda aplicando la colección de filtros configurada. */
	private JButton btnBuscar;
	/** Botón disparador encargado de restaurar la visibilidad y vigencia de un artículo inactivo. */
	private JButton btnHabilitarProd;
	/** Etiqueta informativa que sirve de cabecera para el área de previsualización descriptiva. */
	private JLabel lblVerDescripcion;
	/** Área de texto enriquecida y multilínea dedicada a previsualizar la descripción del producto seleccionado. */
	private JTextArea txaDescripcion;
	/** Contenedor con barras de desplazamiento para albergar y permitir la lectura fluida en el área de texto. */
	private JScrollPane scrpDescripcion;
	/** Botón interactivo para alternar dinámicamente la visibilidad del panel de descripción lateral. */
	private JButton btnVerMas;

	/**
	 * Constructor por defecto de la clase.
	 * Coordina de forma secuencial la definición de las propiedades del lienzo gráfico y la construcción de widgets.
	 */
	public VGestionProd() {
		configurarVentana();
		crearComponentes();
	}

	/**
	 * Configura las propiedades geométricas y estéticas del panel.
	 * Define su nombre identificativo, la dimensión calculada y el color de fondo persistente de la ventana principal.
	 */
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setName(NAME);
		setBackground(VPrincipal.colorPalido);
	}

	/**
	 * Inicializa, dimensiona y añade al layout absoluto todos los componentes visuales del formulario.
	 * <p>
	 * Configura etiquetas con fuentes personalizadas, los campos de captura de datos, los componentes de búsqueda,
	 * un separador estético, la tabla con una sobreescritura interna del método {@code getToolTipText} para 
	 * calcular dinámicamente si el producto apuntado por el cursor se encuentra activo o inactivo, y el panel lateral de lectura.
	 * </p>
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Productos");
		lblTitulo.setFont(Fuentes.BOLD.deriveFont(20f));
		lblTitulo.setBounds(15, 16, 300, 25);
		add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(Fuentes.MEDIUM.deriveFont(14f));
		lblNombre.setBounds(35, 60, 80, 20);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(125, 57, 200, 26);
		add(txtNombre);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(370, 60, 80, 20);
		add(lblCategoria);

		txtCategoria = new JTextField();
		txtCategoria.setBounds(455, 57, 170, 26);
		add(txtCategoria);

		JLabel lblPrecio = new JLabel("Precio (€):");
		lblPrecio.setBounds(35, 98, 80, 20);
		add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(125, 95, 100, 26);
		add(txtPrecio);

		JLabel lblStock = new JLabel("Stock inicial:");
		lblStock.setBounds(370, 98, 90, 20);
		add(lblStock);

		txtStock = new JTextField();
		txtStock.setBounds(465, 95, 80, 26);
		add(txtStock);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(35, 136, 85, 20);
		add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(125, 133, 500, 26);
		add(txtDescripcion);

		btnAgregarProd = new JButton(ConstantesBotones.ADD_PRODUCTO);
		btnAgregarProd.setBounds(100, 178, 160, 30);
		add(btnAgregarProd);

		btnModificarProd = new JButton(ConstantesBotones.MODIFICAR_PRODUCTO);
		btnModificarProd.setBounds(275, 178, 165, 30);
		btnModificarProd.setEnabled(false);
		add(btnModificarProd);

		btnLimpiar = new JButton(ConstantesBotones.LIMPIAR);
		btnLimpiar.setBounds(455, 178, 100, 30);
		add(btnLimpiar);

		JLabel lblListado = new JLabel("Listado de Productos:");
		lblListado.setBounds(33, 306, 220, 20);
		add(lblListado);

		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(35, 333, 710, 200);
		add(scrpProductos);

		tblProductos = new JTable() {
			@Override
			public String getToolTipText(MouseEvent e) {
				int fila = rowAtPoint(e.getPoint());
				if (fila != -1 && productosCargados != null && fila < productosCargados.size()) {
					return productosCargados.get(fila).isActivo() ? "Activo" : "Inactivo";
				}
				return null;
			}
		};
		tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		btnDeshabilitarProd = new JButton(ConstantesBotones.DESHABILITAR_PRODUCTO);
		btnDeshabilitarProd.setBounds(541, 543, 163, 30);
		btnDeshabilitarProd.setEnabled(false);
		add(btnDeshabilitarProd);

		btnHabilitarProd = new JButton(ConstantesBotones.HABILITAR_PRODUCTO);
		btnHabilitarProd.setBounds(365, 543, 163, 30);
		btnHabilitarProd.setEnabled(false);
		add(btnHabilitarProd);

		txtNombreBuscar = new JTextField();
		txtNombreBuscar.setBounds(98, 228, 155, 26);
		add(txtNombreBuscar);

		JLabel lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(35, 231, 60, 20);
		add(lblNombre_1);

		JLabel lblPrecio_1 = new JLabel("Precio:");
		lblPrecio_1.setBounds(268, 231, 48, 20);
		add(lblPrecio_1);

		cmbPrecioBuscar = new JComboBox<>(new String[] { "Todos", "< 10 €", "10 - 50 €", "> 50 €" });
		cmbPrecioBuscar.setBounds(313, 227, 115, 26);
		add(cmbPrecioBuscar);

		JLabel lblCategoria_1 = new JLabel("Categoría:");
		lblCategoria_1.setBounds(450, 231, 68, 20);
		add(lblCategoria_1);

		dcbmCategoria = new DefaultComboBoxModel<>();
		dcbmCategoria.addElement("Todas");
		cmbCategoriaBuscar = new JComboBox<>(dcbmCategoria);
		cmbCategoriaBuscar.setBounds(515, 227, 110, 26);
		add(cmbCategoriaBuscar);

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_PRODUCTO);
		btnBuscar.setBounds(35, 267, 115, 26);
		add(btnBuscar);

		JSeparator separator = new JSeparator();
		separator.setBounds(15, 216, 730, 6);
		add(separator);

		lblVerDescripcion = new JLabel("Descripcion:");
		lblVerDescripcion.setBounds(753, 333, 68, 12);
		add(lblVerDescripcion);

		scrpDescripcion = new JScrollPane();
		scrpDescripcion.setBounds(753, 355, 170, 178);
		add(scrpDescripcion);

		txaDescripcion = new JTextArea();
		scrpDescripcion.setViewportView(txaDescripcion);
		txaDescripcion.setLineWrap(true);
		txaDescripcion.setWrapStyleWord(true);
		txaDescripcion.setEditable(false);

		btnVerMas = new JButton(ConstantesBotones.VER_MAS);
		btnVerMas.setBounds(45, 543, 92, 28);
		add(btnVerMas);
	}

	/**
	 * Modifica el estado de habilitación del botón encargado de expandir/contraer los detalles.
	 * * @param b {@code true} para activar el componente; {@code false} para congelar su interacción.
	 */
	public void setVerMasEnabled(boolean b) {
		btnVerMas.setEnabled(b);
	}

	/**
	 * Renderiza y hace visible el panel lateral con la descripción íntegra del producto.
	 * Cambia el texto del activador y conmuta su comando de acción a la modalidad de cierre (Ver menos).
	 * * @param descripción Cadena de caracteres con la información textual a volcar.
	 */
	public void verDescripcion(String descripción) {
		lblVerDescripcion.setVisible(true);
		scrpDescripcion.setVisible(true);
		txaDescripcion.setText(descripción);

		btnVerMas.setText(ConstantesBotones.VER_MENOS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MENOS);
	}

	/**
	 * Oculta por completo los componentes del área descriptiva lateral de la interfaz.
	 * Borra el contenido actual del área de texto y reestablece el comando del botón a la modalidad inicial (Ver más).
	 */
	public void hideDescripcion() {
		lblVerDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		txaDescripcion.setText("");

		btnVerMas.setText(ConstantesBotones.VER_MAS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);
	}

	/**
	 * Vuelca de manera dinámica una lista de categorías del almacén directamente en el desplegable de búsquedas.
	 * Remueve los datos existentes y respeta la opción comodín "Todas" en el índice inicial.
	 * * @param categorias Colección de cadenas de texto con los nombres de los sectores comerciales.
	 */
	public void cargarCategorias(ArrayList<String> categories) {
		dcbmCategoria.removeAllElements();
		dcbmCategoria.addElement("Todas");
		for (String cat : categories) {
			dcbmCategoria.addElement(cat);
		}
	}

	/**
	 * Realiza la configuración técnica, cabeceras y dimensiones preferidas de la tabla de catálogo.
	 */
	private void configurarTabla() {
		dtmProductos = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblProductos.getTableHeader().setReorderingAllowed(false);
		tblProductos.setModel(dtmProductos);

		dtmProductos.addColumn("Nombre");
		dtmProductos.addColumn("Categoría");
		dtmProductos.addColumn("Precio");
		dtmProductos.addColumn("Stock");

		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(130);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(70);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
	}

	/**
	 * Vuelca una colección dinámica de productos en el modelo de datos visual de la tabla.
	 * Resetea las selecciones de fila previas y sincroniza el listado en memoria local.
	 * * @param productos Estructura {@link ArrayList} de entidades {@link Producto} a listar.
	 */
	public void cargarTabla(ArrayList<Producto> productos) {
		tblProductos.clearSelection();
		dtmProductos.getDataVector().clear();
		productosCargados = productos;

		if (productos.size() != 0) {
			clearTable();
			Object[] row = new Object[4];
			for (Producto prod : productos) {
				row[0] = prod.getNombre();
				row[1] = prod.getCategoria();
				row[2] = prod.getPrecio();
				row[3] = prod.getStock();

				dtmProductos.addRow(row);
			}
		} else {
			// JOptionPane.showMessageDialog(this, "No se han encontrado items con los
			// filtros seleccionados", "Mensaje",
			// JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Limpia y purga de forma secuencial la totalidad de las filas alojadas en el modelo de datos de la tabla.
	 */
	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for (int i = 0; i < r; i++) {
			// System.out.println(i);
			dtmProductos.removeRow(0);
		}
	}

	/**
	 * Extrae el objeto seleccionado de la lista y acopla sus campos directamente en el formulario de la cabecera.
	 * Almacena el ID interno y bloquea el botón de inserción para forzar el flujo de actualización de datos.
	 */
	public void cargarProductoEnForm() {
		int fila = tblProductos.getSelectedRow();
		Producto prod = productosCargados.get(fila);

		idSeleccionado = prod.getId();
		txtNombre.setText(prod.getNombre());
		txtCategoria.setText(prod.getCategoria());
		txtPrecio.setText(String.valueOf(prod.getPrecio()));
		txtStock.setText(String.valueOf(prod.getStock()));
		txtDescripcion.setText(prod.getDescripcion());

		btnAgregarProd.setEnabled(false);
	}

	/**
	 * Obtiene el identificador numérico único del producto cargado actualmente en edición.
	 * * @return Valor entero de {@code idSeleccionado}; retorna {@code -1} si no proviene de una fila de la tabla.
	 */
	public int getIdSeleccionado() {
		return idSeleccionado;
	}

	/**
	 * Inicializa por completo el estado lógico de los widgets de la pantalla.
	 * Blanquea los cuadros de entrada, remueve selecciones de fila y reestablece los estados de habilitación de botones.
	 */
	public void limpiarDatos() {
		idSeleccionado = -1;
		txtNombre.setText("");
		txtCategoria.setText("");
		txtPrecio.setText("");
		txtStock.setText("");
		txtDescripcion.setText("");
		tblProductos.clearSelection();
		btnAgregarProd.setEnabled(true);
		btnModificarProd.setEnabled(false);
		btnDeshabilitarProd.setEnabled(false);
		btnHabilitarProd.setEnabled(false);
	}

	/**
	 * Modifica la propiedad de habilitación del botón encargado de confirmar los cambios de un artículo.
	 * * @param b {@code true} para permitir la edición; {@code false} para restringirla.
	 */
	public void setModificarEnabled(boolean b) {
		btnModificarProd.setEnabled(b);
	}

	/**
	 * Modifica la propiedad de habilitación del botón encargado de deshabilitar de forma lógica un artículo.
	 * * @param b {@code true} para activar el botón; {@code false} para restringirlo.
	 */
	public void setEliminarEnabled(boolean b) {
		btnDeshabilitarProd.setEnabled(b);
	}

	/**
	 * Modifica la propiedad de habilitación del botón encargado de volver a activar comercialmente un artículo.
	 * * @param b {@code true} para activar el botón; {@code false} para restringirlo.
	 */
	public void setHabilitarEnabled(boolean b) {
		btnHabilitarProd.setEnabled(b);
	}

	/**
	 * Modifica la propiedad de habilitación del botón encargado de dar de alta un nuevo artículo.
	 * * @param b {@code true} para activar la inserción; {@code false} para restringirla.
	 */
	public void setAgregarEnabled(boolean b) {
		btnAgregarProd.setEnabled(b);
	}

	// TODO: validación de datos
	/**
	 * Extrae y valida rigurosamente las cadenas de texto del formulario para consolidar un objeto Producto válido.
	 * <p>
	 * Realiza la conversión de formatos numéricos para el precio (double) y el stock (int). Ante cualquier anomalía, 
	 * interrumpe el flujo y alerta al operario mediante ventanas de diálogo emergentes de tipo {@code ERROR_MESSAGE}.
	 * </p>
	 * * @return Una nueva instancia mapeada de {@link Producto} lista para ser procesada si la entrada de datos es válida; 
	 * o {@code null} si se detectó algún campo erróneo o vacío.
	 */
	public Producto obtenerDatosFormulario() {
		String nombre = txtNombre.getText().trim();
		String categoria = txtCategoria.getText().trim();
		String precioStr = txtPrecio.getText().trim();
		String stockStr = txtStock.getText().trim();
		String descripcion = txtDescripcion.getText().trim();

		boolean valid = true;

		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		}

		double precio = 0;
		int stock = 0;

		try {
			if (!precioStr.isEmpty()) {
				precio = Double.parseDouble(precioStr);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El precio debe ser un número decimal.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		try {
			if (!stockStr.isEmpty()) {
				stock = Integer.parseInt(stockStr);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		}

		if (valid) {
			return new Producto(idSeleccionado, nombre, categoria, precio, descripcion, stock, true);
		} else {
			return null;
		}
	}

	/**
	 * Compila y recopila los estados de filtrado vigentes en el subformulario de búsqueda de catálogo.
	 * <p>
	 * Evalúa si las entradas corresponden a las opciones por defecto ("Todos", "Todas" o vacío), 
	 * traduciéndolas a referencias {@code null} para simplificar la composición de la consulta SQL dinámica en el controlador.
	 * </p>
	 * * @return Un array unidimensional de cadenas de texto ({@code String[]}) de tamaño 3, donde `index[0]` 
	 * es el nombre buscado, `index[1]` representa la escala de precio y `index[2]` contiene la categoría seleccionada. 
	 * Retorna {@code null} si no hay ninguna regla restrictiva establecida.
	 */
	public String[] getConsulta() {
		String nombre = txtNombreBuscar.getText().trim();
		String precio = (String) cmbPrecioBuscar.getSelectedItem();
		String categoria = (String) cmbCategoriaBuscar.getSelectedItem();

		// Si no hay ningún filtro activo devuelve null
		if (nombre.isEmpty() && precio.equals("Todos") && categoria.equals("Todas")) {
			return null;
		}

		return new String[] { nombre.isEmpty() ? null : nombre, precio.equals("Todos") ? null : precio,
				categoria.equals("Todas") ? null : categoria };
	}

	/**
	 * Registra el objeto controlador centralizado como oyente y gestor de eventos de todos los elementos interactivos.
	 * <p>
	 * Vincula los botones del formulario, los botones de búsqueda/administración, los escuchadores del ratón 
	 * y los cambios de selección en las filas del componente tabla.
	 * </p>
	 * * @param c Instancia de la clase controladora unificada {@link Ctrl}.
	 */
	@Override
	public void setControlador(Ctrl c) {
		btnAgregarProd.addActionListener(c);
		btnAgregarProd.setActionCommand(ConstantesBotones.ADD_PRODUCTO);

		btnModificarProd.addActionListener(c);
		btnModificarProd.setActionCommand(ConstantesBotones.MODIFICAR_PRODUCTO);

		btnLimpiar.addActionListener(c);
		btnLimpiar.setActionCommand(ConstantesBotones.LIMPIAR);

		btnDeshabilitarProd.addActionListener(c);
		btnDeshabilitarProd.setActionCommand(ConstantesBotones.DESHABILITAR_PRODUCTO);

		btnHabilitarProd.addActionListener(c);
		btnHabilitarProd.setActionCommand(ConstantesBotones.HABILITAR_PRODUCTO);

		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand(ConstantesBotones.BUSCAR_PRODUCTO);
		
		btnVerMas.addActionListener(c);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);

		tblProductos.addMouseListener(c);
		tblProductos.getSelectionModel().addListSelectionListener(c);
	}

	/**
	 * Proporciona el modelo encargado del control de las selecciones de filas del componente listado.
	 * * @return El objeto {@link ListSelectionModel} interno asociado a la tabla de productos.
	 */
	public Object getSelectedModel() {
		return tblProductos.getSelectionModel();
	}

	/**
	 * Proporciona acceso directo al componente visual de la tabla de catálogo.
	 * * @return La instancia {@link JTable} empleada para renderizar el inventario.
	 */
	public JTable getTblProductos() {
		return tblProductos;
	}

	/**
	 * Extrae textualmente el valor almacenado en la columna de nombre de la fila actualmente seleccionada por el operario.
	 * * @return Cadena de caracteres correspondiente al nombre del producto seleccionado.
	 */
	public String getNombreSeleccionado() {
		return (String) tblProductos.getValueAt(tblProductos.getSelectedRow(), 0);
	}

	/**
	 * Recupera el objeto completo con la información de negocio indexado en una posición específica de la lista.
	 * * @param fila Índice numérico de la fila seleccionada.
	 * @return El objeto de tipo {@link Producto} almacenado en esa posición del vector.
	 */
	public Producto getProductoEnFila(int fila) {
		return productosCargados.get(fila);
	}
}