package com.dam.view;

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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

/**
 * Clase de la interfaz gráfica que representa el panel de administración del
 * catálogo (`VGestionProd`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Esta vista proporciona
 * un entorno unificado con un formulario de inserción/edición para controlar el
 * ciclo de vida de las mercancías, una sección de filtrado avanzado
 * multi-criterio (nombre, rango de precio, categoría) y un listado interactivo
 * en forma de tabla con soporte para tooltips dinámicos que reflejan el estado
 * de activación de cada artículo.
 * </p>
 * * @author Zoe
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class VGestionProd extends JPanel implements IPanels {
	/**
	 * Identificador único asignado al panel para la gestión y alternancia en el
	 * layout de la aplicación.
	 */
	public static final String NAME = "VGestionProd";

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
	 * Colección indexada que almacena temporalmente las referencias de tipo
	 * {@link Producto} cargadas en la tabla.
	 */
	private ArrayList<Producto> productosCargados = new ArrayList<>();
	/**
	 * Almacena el código de identificación único del artículo seleccionado en la
	 * vista; {@code -1} si no hay ninguno.
	 */
	private int idSeleccionado = -1;

	/**
	 * Campo de texto destinado a la introducción o modificación del nombre
	 * comercial del producto.
	 */
	private JTextField txtNombre;
	/**
	 * Campo de texto destinado a la introducción o modificación del sector o
	 * categoría del producto.
	 */
	private JTextField txtCategoria;
	/**
	 * Campo de texto destinado a la introducción o modificación del precio de venta
	 * unitario.
	 */
	private JTextField txtPrecio;
	/**
	 * Campo de texto destinado a la introducción o modificación del volumen físico
	 * de unidades iniciales.
	 */
	private JTextField txtStock;
	/**
	 * Campo de texto extendido destinado a la introducción o modificación de la
	 * descripción del artículo.
	 */
	private JTextField txtDescripcion;
	/**
	 * Botón disparador encargado de procesar la inserción de un nuevo artículo en
	 * la persistencia.
	 */
	private JButton btnAgregarProd;
	/**
	 * Botón disparador encargado de guardar los cambios sobre un artículo
	 * seleccionado previamente.
	 */
	private JButton btnModificarProd;
	/**
	 * Botón disparador encargado de blanquear el formulario e inicializar el estado
	 * de la vista.
	 */
	private JButton btnLimpiar;
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
	 * Botón disparador encargado de inhabilitar o retirar lógicamente un producto
	 * del comercio.
	 */
	private JButton btnDeshabilitarProd;
	/**
	 * Campo de texto destinado a introducir la cadena o patrón para el filtrado por
	 * nombre de artículos.
	 */
	private JTextField txtNombreBuscar;
	/**
	 * Componente de selección desplegable para acotar los artículos por rangos o
	 * umbrales económicos.
	 */
	private JComboBox<String> cmbPrecioBuscar;
	/**
	 * Componente de selección desplegable para filtrar los artículos por su sector
	 * o categoría.
	 */
	private JComboBox<String> cmbCategoriaBuscar;
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
	 * Botón disparador encargado de restaurar la visibilidad y vigencia de un
	 * artículo inactivo.
	 */
	private JButton btnHabilitarProd;
	/**
	 * Etiqueta informativa que sirve de cabecera para el área de previsualización
	 * descriptiva.
	 */
	private JLabel lblVerDescripcion;
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
	 * descripción lateral.
	 */
	private JButton btnVerMas;

	/**
	 * Constructor por defecto de la clase. Coordina de forma secuencial la
	 * definición de las propiedades del lienzo gráfico y la construcción de
	 * widgets.
	 */
	public VGestionProd() {
		configurarVentana();
		crearComponentes();
	}

	/**
	 * Configura las propiedades geométricas y estéticas del panel. Define su nombre
	 * identificativo, la dimensión calculada y el color de fondo persistente de la
	 * ventana principal.
	 */
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setName(NAME);
		setBackground(VPrincipal.colorPalido);
	}

	/**
	 * Inicializa, dimensiona y añade al layout absoluto todos los componentes
	 * visuales del formulario.
	 * <p>
	 * Configura etiquetas con fuentes personalizadas, los campos de captura de
	 * datos, los componentes de búsqueda, un separador estético, la tabla con una
	 * sobreescritura interna del método {@code getToolTipText} para calcular
	 * dinámicamente si el producto apuntado por el cursor se encuentra activo o
	 * inactivo, y el panel lateral de lectura.
	 * </p>
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Productos");
		lblTitulo.setFont(Fuentes.BOLD.deriveFont(20f));
		lblTitulo.setForeground(VPrincipal.colorLetras);
		lblTitulo.setBounds(15, 16, 300, 25);
		add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblNombre.setForeground(VPrincipal.colorLetras);
		lblNombre.setBounds(35, 60, 80, 20);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(112, 57, 200, 26);
		txtNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtNombre.setForeground(VPrincipal.colorLetras);
		add(txtNombre);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(322, 60, 80, 20);
		lblCategoria.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblCategoria.setForeground(VPrincipal.colorLetras);
		add(lblCategoria);

		txtCategoria = new JTextField();
		txtCategoria.setBounds(412, 57, 170, 26);
		txtCategoria.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtCategoria.setForeground(VPrincipal.colorLetras);
		add(txtCategoria);

		JLabel lblPrecio = new JLabel("Precio (€):");
		lblPrecio.setBounds(592, 60, 80, 20);
		lblPrecio.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblPrecio.setForeground(VPrincipal.colorLetras);
		add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(682, 57, 100, 26);
		txtPrecio.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtPrecio.setForeground(VPrincipal.colorLetras);
		add(txtPrecio);

		JLabel lblStock = new JLabel("Stock inicial:");
		lblStock.setBounds(792, 60, 90, 20);
		lblStock.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblStock.setForeground(VPrincipal.colorLetras);
		add(lblStock);

		txtStock = new JTextField();
		txtStock.setBounds(892, 57, 80, 26);
		txtStock.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtStock.setForeground(VPrincipal.colorLetras);
		add(txtStock);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(35, 97, 100, 20);
		lblDescripcion.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblDescripcion.setForeground(VPrincipal.colorLetras);
		add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(135, 94, 500, 26);
		txtDescripcion.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtDescripcion.setForeground(VPrincipal.colorLetras);
		add(txtDescripcion);

		btnAgregarProd = new JButton(ConstantesBotones.ADD_PRODUCTO);
		btnAgregarProd.setBounds(35, 130, 176, 30);
		btnAgregarProd.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnAgregarProd.setForeground(VPrincipal.colorLetras);
		btnAgregarProd.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnAgregarProd);

		btnModificarProd = new JButton(ConstantesBotones.MODIFICAR_PRODUCTO);
		btnModificarProd.setBounds(237, 130, 178, 30);
		btnModificarProd.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnModificarProd.setForeground(VPrincipal.colorLetras);
		btnModificarProd.setBackground(VPrincipal.colorNaranjaPatito);
		btnModificarProd.setEnabled(false);
		add(btnModificarProd);

		btnLimpiar = new JButton(ConstantesBotones.LIMPIAR);
		btnLimpiar.setBounds(443, 130, 100, 30);
		btnLimpiar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnLimpiar.setForeground(VPrincipal.colorLetras);
		btnLimpiar.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnLimpiar);

		JLabel lblListado = new JLabel("Listado de Productos:");
		lblListado.setBounds(35, 220, 220, 20);
		lblListado.setFont(Fuentes.REGULAR.deriveFont(16f));
		lblListado.setForeground(VPrincipal.colorLetras);
		add(lblListado);

		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(35, 250, 827, 688);
		scrpProductos.getViewport().setBackground(VPrincipal.colorVibrante);
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
		tblProductos.getTableHeader().setFont(Fuentes.MEDIUM.deriveFont(16f));
		tblProductos.getTableHeader().setForeground(VPrincipal.colorLetras);
		tblProductos.getTableHeader().setBackground(VPrincipal.colorNaranjaPatito);
		tblProductos.getTableHeader().setReorderingAllowed(false);
        tblProductos.getTableHeader().setResizingAllowed(false);
		tblProductos.setFont(Fuentes.REGULAR.deriveFont(14f));
		tblProductos.setForeground(VPrincipal.colorLetras);
		tblProductos.setRowHeight(28);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		btnDeshabilitarProd = new JButton(ConstantesBotones.DESHABILITAR_PRODUCTO);
		btnDeshabilitarProd.setBounds(443, 959, 192, 30);
		btnDeshabilitarProd.setBackground(VPrincipal.colorNaranjaPatito);
		btnDeshabilitarProd.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnDeshabilitarProd.setForeground(VPrincipal.colorLetras);
		btnDeshabilitarProd.setEnabled(false);
		add(btnDeshabilitarProd);

		btnHabilitarProd = new JButton(ConstantesBotones.HABILITAR_PRODUCTO);
		btnHabilitarProd.setBounds(682, 959, 180, 30);
		btnHabilitarProd.setBackground(VPrincipal.colorNaranjaPatito);
		btnHabilitarProd.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnHabilitarProd.setForeground(VPrincipal.colorLetras);
		btnHabilitarProd.setEnabled(false);
		add(btnHabilitarProd);

		txtNombreBuscar = new JTextField();
		txtNombreBuscar.setBounds(112, 184, 155, 26);
		txtNombreBuscar.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtNombreBuscar.setForeground(VPrincipal.colorLetras);
		add(txtNombreBuscar);

		JLabel lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(35, 187, 80, 20);
		lblNombre_1.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblNombre_1.setForeground(VPrincipal.colorLetras);
		add(lblNombre_1);

		JLabel lblPrecio_1 = new JLabel("Precio:");
		lblPrecio_1.setBounds(277, 187, 68, 20);
		lblPrecio_1.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblPrecio_1.setForeground(VPrincipal.colorLetras);
		add(lblPrecio_1);

		cmbPrecioBuscar = new JComboBox<>(new String[] { "Todos", "< 10 €", "10 - 50 €", "> 50 €" });
		cmbPrecioBuscar.setBounds(332, 184, 115, 26);
		cmbPrecioBuscar.setFont(Fuentes.REGULAR.deriveFont(16f));
		cmbPrecioBuscar.setForeground(VPrincipal.colorLetras);
		add(cmbPrecioBuscar);

		JLabel lblCategoria_1 = new JLabel("Categoría:");
		lblCategoria_1.setBounds(465, 187, 90, 20);
		lblCategoria_1.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblCategoria_1.setForeground(VPrincipal.colorLetras);
		add(lblCategoria_1);

		dcbmCategoria = new DefaultComboBoxModel<>();
		dcbmCategoria.addElement("Todas");
		cmbCategoriaBuscar = new JComboBox<>(dcbmCategoria);
		cmbCategoriaBuscar.setBounds(551, 184, 110, 26);
		cmbCategoriaBuscar.setFont(Fuentes.REGULAR.deriveFont(16f));
		cmbCategoriaBuscar.setForeground(VPrincipal.colorLetras);
		add(cmbCategoriaBuscar);

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_PRODUCTO);
		btnBuscar.setBounds(682, 184, 160, 26);
		btnBuscar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnBuscar.setForeground(VPrincipal.colorLetras);
		btnBuscar.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnBuscar);

		JSeparator separator = new JSeparator();
		separator.setBounds(15, 170, 1388, 2);
		separator.setBackground(VPrincipal.colorNaranjaPatito);
		add(separator);

		lblVerDescripcion = new JLabel("Descripción:");
		lblVerDescripcion.setBounds(892, 258, 115, 25);
		lblVerDescripcion.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblVerDescripcion.setForeground(VPrincipal.colorLetras);
		add(lblVerDescripcion);

		scrpDescripcion = new JScrollPane();
		scrpDescripcion.setBounds(892, 304, 511, 252);
		add(scrpDescripcion);

		txaDescripcion = new JTextArea();
		scrpDescripcion.setViewportView(txaDescripcion);
		txaDescripcion.setLineWrap(true);
		txaDescripcion.setWrapStyleWord(true);
		txaDescripcion.setEditable(false);
		txaDescripcion.setBackground(VPrincipal.colorVibrante);
		txaDescripcion.setFont(Fuentes.REGULAR.deriveFont(14f));
		txaDescripcion.setForeground(VPrincipal.colorLetras);

		btnVerMas = new JButton(ConstantesBotones.VER_MAS);
		btnVerMas.setBounds(35, 960, 130, 28);
		btnVerMas.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnVerMas.setForeground(VPrincipal.colorLetras);
		btnVerMas.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnVerMas);
	}

	/**
	 * Modifica el estado de habilitación del botón encargado de expandir/contraer
	 * los detalles. * @param b {@code true} para activar el componente;
	 * {@code false} para congelar su interacción.
	 */
	public void setVerMasEnabled(boolean b) {
		btnVerMas.setEnabled(b);
	}

	/**
	 * Renderiza y hace visible el panel lateral con la descripción íntegra del
	 * producto. Cambia el texto del activador y conmuta su comando de acción a la
	 * modalidad de cierre (Ver menos). * @param descripción Cadena de caracteres
	 * con la información textual a volcar.
	 */
	public void verDescripcion(String descripción) {
		lblVerDescripcion.setVisible(true);
		scrpDescripcion.setVisible(true);
		txaDescripcion.setText(descripción);

		btnVerMas.setText(ConstantesBotones.VER_MENOS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MENOS);
	}

	/**
	 * Oculta por completo los componentes del área descriptiva lateral de la
	 * interfaz. Borra el contenido actual del área de texto y reestablece el
	 * comando del botón a la modalidad inicial (Ver más).
	 */
	public void hideDescripcion() {
		lblVerDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		txaDescripcion.setText("");

		btnVerMas.setText(ConstantesBotones.VER_MAS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);
	}

	/**
	 * Vuelca de manera dinámica una lista de categorías del almacén directamente en
	 * el desplegable de búsquedas. Remueve los datos existentes y respeta la opción
	 * comodín "Todas" en el índice inicial. * @param categorias Colección de
	 * cadenas de texto con los nombres de los sectores comerciales.
	 */
	public void cargarCategorias(ArrayList<String> categories) {
		dcbmCategoria.removeAllElements();
		dcbmCategoria.addElement("Todas");
		for (String cat : categories) {
			dcbmCategoria.addElement(cat);
		}
	}

	/**
	 * Realiza la configuración técnica, cabeceras y dimensiones preferidas de la
	 * tabla de catálogo.
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
	 * Vuelca una colección dinámica de productos en el modelo de datos visual de la
	 * tabla. Resetea las selecciones de fila previas y sincroniza el listado en
	 * memoria local. * @param productos Estructura {@link ArrayList} de entidades
	 * {@link Producto} a listar.
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
	 * Limpia y purga de forma secuencial la totalidad de las filas alojadas en el
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
	 * Extrae el objeto seleccionado de la lista y acopla sus campos directamente en
	 * el formulario de la cabecera. Almacena el ID interno y bloquea el botón de
	 * inserción para forzar el flujo de actualización de datos.
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
	 * Obtiene el identificador numérico único del producto cargado actualmente en
	 * edición. * @return Valor entero de {@code idSeleccionado}; retorna {@code -1}
	 * si no proviene de una fila de la tabla.
	 */
	public int getIdSeleccionado() {
		return idSeleccionado;
	}

	/**
	 * Inicializa por completo el estado lógico de los widgets de la pantalla.
	 * Blanquea los cuadros de entrada, remueve selecciones de fila y reestablece
	 * los estados de habilitación de botones.
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
	 * Modifica la propiedad de habilitación del botón encargado de confirmar los
	 * cambios de un artículo. * @param b {@code true} para permitir la edición;
	 * {@code false} para restringirla.
	 */
	public void setModificarEnabled(boolean b) {
		btnModificarProd.setEnabled(b);
	}

	/**
	 * Modifica la propiedad de habilitación del botón encargado de deshabilitar de
	 * forma lógica un artículo. * @param b {@code true} para activar el botón;
	 * {@code false} para restringirlo.
	 */
	public void setEliminarEnabled(boolean b) {
		btnDeshabilitarProd.setEnabled(b);
	}

	/**
	 * Modifica la propiedad de habilitación del botón encargado de volver a activar
	 * comercialmente un artículo. * @param b {@code true} para activar el botón;
	 * {@code false} para restringirlo.
	 */
	public void setHabilitarEnabled(boolean b) {
		btnHabilitarProd.setEnabled(b);
	}

	/**
	 * Modifica la propiedad de habilitación del botón encargado de dar de alta un
	 * nuevo artículo. * @param b {@code true} para activar la inserción;
	 * {@code false} para restringirla.
	 */
	public void setAgregarEnabled(boolean b) {
		btnAgregarProd.setEnabled(b);
	}

	// ok
	/**
	 * Extrae y valida rigurosamente las cadenas de texto del formulario para
	 * consolidar un objeto Producto válido.
	 * <p>
	 * Realiza la conversión de formatos numéricos para el precio (double) y el
	 * stock (int). Ante cualquier anomalía, interrumpe el flujo y alerta al
	 * operario mediante ventanas de diálogo emergentes de tipo
	 * {@code ERROR_MESSAGE}.
	 * </p>
	 * * @return Una nueva instancia mapeada de {@link Producto} lista para ser
	 * procesada si la entrada de datos es válida; o {@code null} si se detectó
	 * algún campo erróneo o vacío.
	 */
	public Producto obtenerDatosFormulario() {
		try {
			String nombre = txtNombre.getText().trim();
			Producto.validarNombre(nombre);
			String categoria = txtCategoria.getText().trim();
			Producto.validarCategoria(categoria);
			double precio = Producto.parsePrecio(txtPrecio.getText().trim());
			Producto.validarPrecio(precio);
			int stock = Producto.parseStock(txtStock.getText().trim());
			Producto.validarStock(stock);
			String descripcion = txtDescripcion.getText().trim();
			
			return new Producto(idSeleccionado, nombre, categoria, precio, descripcion, stock, true);
		}catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error de datos", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * Compila y recopila los estados de filtrado vigentes en el subformulario de
	 * búsqueda de catálogo.
	 * <p>
	 * Evalúa si las entradas corresponden a las opciones por defecto ("Todos",
	 * "Todas" o vacío), traduciéndolas a referencias {@code null} para simplificar
	 * la composición de la consulta SQL dinámica en el controlador.
	 * </p>
	 * * @return Un array unidimensional de cadenas de texto ({@code String[]}) de
	 * tamaño 3, donde `index[0]` es el nombre buscado, `index[1]` representa la
	 * escala de precio y `index[2]` contiene la categoría seleccionada. Retorna
	 * {@code null} si no hay ninguna regla restrictiva establecida.
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
	 * Registra el objeto controlador centralizado como oyente y gestor de eventos
	 * de todos los elementos interactivos.
	 * <p>
	 * Vincula los botones del formulario, los botones de búsqueda/administración,
	 * los escuchadores del ratón y los cambios de selección en las filas del
	 * componente tabla.
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
	 * Proporciona el modelo encargado del control de las selecciones de filas del
	 * componente listado. * @return El objeto {@link ListSelectionModel} interno
	 * asociado a la tabla de productos.
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
	 * Extrae textualmente el valor almacenado en la columna de nombre de la fila
	 * actualmente seleccionada por el operario. * @return Cadena de caracteres
	 * correspondiente al nombre del producto seleccionado.
	 */
	public String getNombreSeleccionado() {
		return (String) tblProductos.getValueAt(tblProductos.getSelectedRow(), 0);
	}

	/**
	 * Recupera el objeto completo con la información de negocio indexado en una
	 * posición específica de la lista. * @param fila Índice numérico de la fila
	 * seleccionada.
	 * 
	 * @return El objeto de tipo {@link Producto} almacenado en esa posición del
	 *         vector.
	 */
	public Producto getProductoEnFila(int fila) {
		return productosCargados.get(fila);
	}
}