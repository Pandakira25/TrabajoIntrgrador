package com.dam.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Producto;
import javax.swing.SwingConstants;

/**
 * Clase de la interfaz gráfica que representa el panel de administración de
 * inventario ({@code VGestionStock}).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Esta vista proporciona
 * al personal de la empresa un entorno interactivo para la monitorización,
 * filtrado y modificación ágil de las existencias de los artículos del almacén.
 * Permite buscar por múltiples criterios (nombre, precio, categoría),
 * visualizar información detallada mediante un panel de descripción colapsable
 * y realizar incrementos o decrementos masivos del stock en base a un valor
 * cuantitativo de entrada.
 * </p>
 *
 * @author Zoe
 * @version 1.0
 */
public class VGestionStock extends JPanel implements IPanels {

	/**
	 * Identificador único asignado al panel para la gestión y alternancia en el
	 * layout de la aplicación.
	 */
	public static final String NAME = "VGestionStock";

	/**
	 * Anchura neta calculada del panel basada en las dimensiones de la ventana
	 * principal y sus insets.
	 */
	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;

	/**
	 * Altura neta calculada del panel basada en las dimensiones de la ventana
	 * principal, insets y menú.
	 */
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	/**
	 * Campo de texto destinado a introducir la cadena o patrón para el filtrado por
	 * nombre de artículos.
	 */
	private JTextField txtBuscarNombre;

	/**
	 * Componente de selección desplegable para acotar los artículos por rangos o
	 * umbrales económicos.
	 */
	private JComboBox<String> cmbPrecio;

	/**
	 * Componente de selección desplegable para filtrar los artículos por su sector
	 * o categoría.
	 */
	private JComboBox<String> cmbCategoria;

	/**
	 * Modelo de datos dinámico asignado para la gestión del contenido del combo box
	 * de categorías.
	 */
	private DefaultComboBoxModel<String> dcbmCategoria;

	/**
	 * Botón disparador encargado de iniciar la búsqueda aplicando la colección de
	 * filtros configurada.
	 */
	private JButton btnBuscar;

	/**
	 * Componente gráfico de tabla para renderizar los detalles analíticos de los
	 * productos mapeados.
	 */
	private JTable tblProductos;

	/**
	 * Modelo de datos subyacente para el control estructurado de filas y columnas
	 * de la tabla.
	 */
	private DefaultTableModel dtmProductos;

	/**
	 * Contenedor con barras de desplazamiento para albergar y permitir la
	 * navegación sobre la tabla.
	 */
	private JScrollPane scrpProductos;

	/**
	 * Campo de texto interactivo para indicar la cantidad de unidades físicas que
	 * se sumarán o restarán del inventario.
	 */
	private JSpinner spnCantidad;

	/**
	 * Botón iconográfico encargado de procesar la suma de unidades de stock
	 * añadidas en el formulario.
	 */
	private JButton btnMas;

	/**
	 * Botón iconográfico encargado de procesar la resta o sustracción de unidades
	 * de stock del inventario.
	 */
	private JButton btnMenos;

	/**
	 * Etiqueta informativa que sirve de cabecera para el área de previsualización
	 * descriptiva.
	 */
	private JLabel lblDescripcion;

	/**
	 * Área de texto enriquecida y multilínea dedicada a previsualizar la
	 * descripción del producto seleccionado.
	 */
	private JTextArea txaDescripcion;

	/**
	 * Contenedor con barras de desplazamiento para albergar y permitir la lectura
	 * fluida en el área de texto.
	 */
	private JScrollPane scrpDescripcion;

	/**
	 * Botón interactivo para alternar dinámicamente la visibilidad del panel de
	 * descripción colapsable.
	 */
	private JButton btnVerMas;

	/**
	 * Colección indexada que almacena temporalmente las referencias de tipo
	 * {@link Producto} cargadas en la tabla.
	 */
	private ArrayList<Producto> productosCargados = new ArrayList<>();

	/**
	 * Constructor por defecto de la clase. Coordina de forma secuencial la
	 * definición de las propiedades del lienzo gráfico y la construcción de
	 * widgets.
	 */
	public VGestionStock() {
		configurarVentana();
		crearComponentes();
	}

	/**
	 * Configura las propiedades geométricas y estéticas del panel. Define la
	 * dimensión neta calculada, el color de fondo pálido y su nombre identificativo
	 * en el layout.
	 */
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setBackground(VPrincipal.colorPalido);
		setName(NAME);
	}

	/**
	 * Inicializa, dimensiona y añade al layout absoluto todos los componentes
	 * visuales del flujo de control de existencias.
	 * <p>
	 * Configura los criterios de filtrado de productos, la tabla de datos
	 * principal, los botones de acción para incremento/decremento mediante iconos
	 * específicos cargados desde las constantes, y el área lateral colapsable de
	 * texto descriptivo (inicializada oculta por defecto).
	 * </p>
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Stock");
		lblTitulo.setBounds(15, 12, 280, 25);
		lblTitulo.setFont(Fuentes.BOLD.deriveFont(20f));
		lblTitulo.setForeground(VPrincipal.colorLetras);
		add(lblTitulo);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(Fuentes.REGULAR.deriveFont(16f));
		lblProductos.setBounds(15, 130, 100, 20);
		add(lblProductos);

		// --- Filtros ---
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(15, 82, 73, 20);
		lblNombre.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblNombre.setForeground(VPrincipal.colorLetras);
		add(lblNombre);

		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(86, 79, 155, 26);
		txtBuscarNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtBuscarNombre.setForeground(VPrincipal.colorLetras);
		add(txtBuscarNombre);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(251, 82, 60, 20);
		lblPrecio.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblPrecio.setForeground(VPrincipal.colorLetras);
		add(lblPrecio);

		cmbPrecio = new JComboBox<>(new String[] { "Todos", "< 10 €", "10 - 50 €", "> 50 €" });
		cmbPrecio.setBounds(312, 79, 115, 26);
		cmbPrecio.setFont(Fuentes.REGULAR.deriveFont(14f));
		cmbPrecio.setForeground(VPrincipal.colorLetras);
		add(cmbPrecio);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(437, 82, 86, 20);
		lblCategoria.setForeground(VPrincipal.colorLetras);
		lblCategoria.setFont(Fuentes.MEDIUM.deriveFont(16f));
		add(lblCategoria);

		dcbmCategoria = new DefaultComboBoxModel<>();
		dcbmCategoria.addElement("Todas");
		cmbCategoria = new JComboBox<>(dcbmCategoria);
		cmbCategoria.setBounds(519, 79, 110, 26);
		cmbCategoria.setFont(Fuentes.REGULAR.deriveFont(14f));
		cmbCategoria.setForeground(VPrincipal.colorLetras);
		add(cmbCategoria);

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_PRODUCTO);
		btnBuscar.setBounds(649, 79, 143, 26);
		btnBuscar.setBackground(VPrincipal.colorNaranjaPatito);
		btnBuscar.setForeground(VPrincipal.colorLetras);
		btnBuscar.setFont(Fuentes.REGULAR.deriveFont(14f));
		add(btnBuscar);

		// --- Tabla ---
		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(15, 166, 827, 688);
		scrpProductos.getViewport().setBackground(VPrincipal.colorVibrante);
		add(scrpProductos);

		tblProductos = new JTable();
		tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblProductos.getTableHeader().setFont(Fuentes.MEDIUM.deriveFont(16f));
		tblProductos.getTableHeader().setForeground(VPrincipal.colorLetras);
		tblProductos.getTableHeader().setBackground(VPrincipal.colorNaranjaPatito);
		tblProductos.setFont(Fuentes.REGULAR.deriveFont(14f));
		tblProductos.setForeground(VPrincipal.colorLetras);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		// --- Botón Ver más ---
		btnVerMas = new JButton(ConstantesBotones.VER_MAS);
		btnVerMas.setBounds(732, 864, 110, 28);
		btnVerMas.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnVerMas.setForeground(VPrincipal.colorLetras);
		btnVerMas.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnVerMas);

		// --- Panel derecho: cantidad ---
		JLabel lblCantInfo = new JLabel("Ingrese la cantidad a añadir/eliminar");
		lblCantInfo.setHorizontalAlignment(SwingConstants.LEFT);
		lblCantInfo.setBounds(869, 166, 286, 26);
		lblCantInfo.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblCantInfo.setForeground(VPrincipal.colorLetras);
		add(lblCantInfo);

		spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		spnCantidad.setBounds(869, 193, 62, 38);
		spnCantidad.setFont(Fuentes.REGULAR.deriveFont(16f));
		spnCantidad.setForeground(VPrincipal.colorLetras);
		((JSpinner.DefaultEditor) spnCantidad.getEditor()).getTextField().setEditable(false);
		((JSpinner.DefaultEditor) spnCantidad.getEditor()).getTextField().setFocusable(false);
		add(spnCantidad);

		btnMas = new JButton(new ImageIcon(ConstantesBotones.MASICONO));
		btnMas.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnMas.setForeground(VPrincipal.colorLetras);
		btnMas.setBackground(VPrincipal.colorNaranjaPatito);
		btnMas.setBounds(941, 203, 38, 28);
		add(btnMas);

		btnMenos = new JButton(new ImageIcon(ConstantesBotones.MENOSICONO));
		btnMenos.setBounds(989, 203, 38, 28);
		btnMenos.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnMenos.setForeground(VPrincipal.colorLetras);
		btnMenos.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnMenos);

		// --- Descripción (oculta por defecto) ---
		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblDescripcion.setForeground(VPrincipal.colorLetras);
		lblDescripcion.setBounds(869, 254, 100, 20);
		lblDescripcion.setVisible(false);
		add(lblDescripcion);

		scrpDescripcion = new JScrollPane();
		scrpDescripcion.setBounds(869, 284, 531, 264);
		scrpDescripcion.setVisible(false);
		add(scrpDescripcion);
				
						txaDescripcion = new JTextArea();
						scrpDescripcion.setViewportView(txaDescripcion);
						txaDescripcion.setLineWrap(true);
						txaDescripcion.setWrapStyleWord(true);
						txaDescripcion.setEditable(false);
						txaDescripcion.setFont(Fuentes.REGULAR.deriveFont(14f));
						txaDescripcion.setForeground(VPrincipal.colorLetras);
						txaDescripcion.setBackground(VPrincipal.colorVibrante);
	}

	/**
	 * Realiza la definición estructural, inhabilitación de edición de celdas,
	 * cabeceras y asignación de anchos preferidos para las columnas de la tabla de
	 * control de stock.
	 * <p>
	 * Las columnas definidas son: {@code Nombre}, {@code Precio (€)} y
	 * {@code Stock}. El reordenamiento de columnas está deshabilitado para
	 * garantizar la consistencia con el vector {@link #productosCargados}.
	 * </p>
	 */
	// corregir: hecho
	// No se debería cargar el id
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
		dtmProductos.addColumn("Precio (€)");
		dtmProductos.addColumn("Stock");

		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(470);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(50);
	}

	/**
	 * Vuelca una colección dinámica de productos en el modelo de datos visual de la
	 * tabla.
	 * <p>
	 * Resetea las selecciones de fila, vacía los detalles informativos previos en
	 * pantalla, sincroniza el vector de datos e informa al usuario mediante diálogo
	 * flotante si el filtro no obtuvo coincidencias.
	 * </p>
	 *
	 * @param productos Estructura {@link ArrayList} de entidades {@link Producto}
	 *                  a listar.
	 */
	// corregir el cargar tabla: hecho
	public void cargarTabla(ArrayList<Producto> productos) {
		tblProductos.clearSelection();
		dtmProductos.getDataVector().clear();
		productosCargados = productos;
		btnVerMas.setEnabled(false);
		hideDescripcion();

		if (productos.size() != 0) {
			clearTable();
			Object[] row = new Object[3];
			for (Producto prod : productos) {
				row[0] = prod.getNombre();
				row[1] = prod.getPrecio();
				row[2] = prod.getStock();

				dtmProductos.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se han encontrado items con los filtros seleccionados", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Limpia y remueve físicamente la totalidad de las filas contenidas en el
	 * modelo de datos de la tabla.
	 */
	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for (int i = 0; i < r; i++) {
			// System.out.println(i);
			dtmProductos.removeRow(0);
		}
	}

	/**
	 * Vuelca de manera dinámica una lista de categorías del almacén directamente en
	 * el desplegable de búsquedas.
	 * <p>
	 * Remueve los datos existentes y respeta la opción comodín {@code "Todas"} en
	 * el índice inicial.
	 * </p>
	 *
	 * @param categorias Colección de cadenas de texto con los nombres de los
	 *                   sectores comerciales.
	 */
	public void cargarCategorias(ArrayList<String> categorias) {
		dcbmCategoria.removeAllElements();
		dcbmCategoria.addElement("Todas");
		for (String cat : categorias) {
			dcbmCategoria.addElement(cat);
		}
	}

	/**
	 * Hace visibles el encabezado y el visor con la descripción textual del
	 * artículo seleccionado.
	 * <p>
	 * Actualiza el estado estético del conmutador a modo de cierre
	 * ({@code Ver menos}).
	 * </p>
	 *
	 * @param descripción Cadena de caracteres con la información textual a volcar.
	 */
	public void verDescripcion(String descripción) {
		lblDescripcion.setVisible(true);
		scrpDescripcion.setVisible(true);
		txaDescripcion.setText(descripción);

		btnVerMas.setText(ConstantesBotones.VER_MENOS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MENOS);
	}

	/**
	 * Oculta por completo de la pantalla el área y la cabecera del texto
	 * descriptivo.
	 * <p>
	 * Borra el contenido actual del área de texto y reestablece el botón al modo
	 * inicial ({@code Ver más}).
	 * </p>
	 */
	public void hideDescripcion() {
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		txaDescripcion.setText("");

		btnVerMas.setText(ConstantesBotones.VER_MAS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);
	}

	/**
	 * Modifica el estado de habilitación del botón encargado de expandir/contraer
	 * la descripción.
	 *
	 * @param b {@code true} para activar el componente; {@code false} para
	 *          congelar su interacción.
	 */
	public void setVerMasEnabled(boolean b) {
		btnVerMas.setEnabled(b);
	}

	/**
	 * Modifica el estado de habilitación del botón para añadir stock
	 * ({@code btnMas}).
	 *
	 * @param b {@code true} para activar el componente; {@code false} para
	 *          restringirlo.
	 */
	public void setBtnMasEnabled(boolean b) {
		btnMas.setEnabled(b);
	}

	/**
	 * Modifica el estado de habilitación del botón para reducir stock
	 * ({@code btnMenos}).
	 *
	 * @param b {@code true} para activar el componente; {@code false} para
	 *          restringirlo.
	 */
	public void setBtnMenosEnabled(boolean b) {
		btnMenos.setEnabled(b);
	}

	/**
	 * Extrae y procesa aritméticamente el contenido del spinner de cantidad.
	 * <p>
	 * En caso de que se introduzca un valor inválido, el componente
	 * {@link JSpinner} garantiza por su propia lógica el retorno del último valor
	 * válido configurado.
	 * </p>
	 *
	 * @return Valor entero de las unidades indicadas en el spinner de cantidad.
	 */
	public int getCantidad() {
		return (int) spnCantidad.getValue();
	}

	/**
	 * Inicializa por completo el estado analítico y gráfico de todos los elementos
	 * del panel.
	 * <p>
	 * Purga los datos mapeados en la tabla, limpia los filtros de búsqueda,
	 * reestablece la cantidad base a {@code 1} y congela la interacción de los
	 * botones de operación de stock.
	 * </p>
	 */
	public void limpiarDatos() {
		dtmProductos.getDataVector().clear();
		dtmProductos.fireTableDataChanged();
		txtBuscarNombre.setText("");
		cmbPrecio.setSelectedIndex(0);
		cmbCategoria.setSelectedIndex(0);
		spnCantidad.setValue(1);
		btnMas.setEnabled(false);
		btnMenos.setEnabled(false);
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		btnVerMas.setText(ConstantesBotones.VER_MAS);
	}

	/**
	 * Registra el controlador unificado de la aplicación como oyente y disparador
	 * en todos los elementos interactivos.
	 * <p>
	 * Acopla escuchadores a los botones de búsqueda, sumadores de inventario,
	 * visualizadores y eventos de cambio de selección sobre las filas del listado
	 * de productos.
	 * </p>
	 *
	 * @param c Instancia de la clase controladora principal {@link Ctrl}.
	 */
	@Override
	public void setControlador(Ctrl c) {
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand(ConstantesBotones.BUSCAR_PRODUCTO);

		btnMas.addActionListener(c);
		btnMas.setActionCommand(ConstantesBotones.MAS);

		btnMenos.addActionListener(c);
		btnMenos.setActionCommand(ConstantesBotones.MENOS);

		btnVerMas.addActionListener(c);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);

		tblProductos.getSelectionModel().addListSelectionListener(c);
	}

	/**
	 * Compila y agrupa los estados de filtrado vigentes en el subformulario de
	 * búsqueda de almacén.
	 * <p>
	 * Evalúa si las entradas corresponden a los valores comodín por defecto
	 * ({@code "Todos"}, {@code "Todas"} o vacío), traduciéndolos a referencias
	 * {@code null} para simplificar el filtrado dinámico en la capa del
	 * controlador.
	 * </p>
	 *
	 * @return Un array unidimensional de cadenas de texto ({@code String[]}) de
	 *         tamaño 3, donde {@code index[0]} es el nombre buscado,
	 *         {@code index[1]} representa la escala de precio y {@code index[2]}
	 *         contiene la categoría seleccionada. Retorna {@code null} si no hay
	 *         ninguna regla restrictiva establecida.
	 */
	public String[] getConsulta() {
		String nombre = txtBuscarNombre.getText().trim();
		String precio = (String) cmbPrecio.getSelectedItem();
		String categoria = (String) cmbCategoria.getSelectedItem();

		if (nombre.isEmpty() && precio.equals("Todos") && categoria.equals("Todas")) {
			return null;
		}

		return new String[] { nombre.isEmpty() ? null : nombre, precio.equals("Todos") ? null : precio,
				categoria.equals("Todas") ? null : categoria };
	}

	/**
	 * Comprueba el estado actual de visibilidad del contenedor deslizable de
	 * descripción de la interfaz.
	 *
	 * @return {@code true} si el panel está desplegado en pantalla; {@code false}
	 *         si permanece oculto.
	 */
	public boolean isDescripcionVisible() {
		return scrpDescripcion.isVisible();
	}

	/**
	 * Proporciona acceso directo al componente visual de la tabla de catálogo.
	 *
	 * @return La instancia {@link JTable} empleada para renderizar el inventario.
	 */
	public JTable getTblProductos() {
		return tblProductos;
	}

	/**
	 * Recupera el objeto completo con la información de negocio indexado en una
	 * posición específica de la lista de productos cargados.
	 *
	 * @param fila Índice numérico de la fila seleccionada en la tabla.
	 * @return El objeto de tipo {@link Producto} almacenado en esa posición del
	 *         vector {@link #productosCargados}.
	 */
	public Producto getProductoEnFila(int fila) {
		return productosCargados.get(fila);
	}

}