package com.dam.view;


import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Producto;

/**
 * Panel de la vista de la Tienda o Catálogo (`VShop`).
 * <p>
 * Hereda de {@link JPanel} e implementa la interfaz {@link IPanels}. Esta vista
 * expone el catálogo interactivo de artículos disponibles. Permite la
 * aplicación de filtros combinados por nombre, rangos preestablecidos de precio
 * y categorías, además de gestionar la preselección de unidades y la
 * visualización de descripciones extendidas.
 * </p>
 * * @author Zoe
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class VShop extends JPanel implements IPanels {
	/**
	 * Identificador único para la gestión dinámica del diseño o de la navegación de
	 * vistas.
	 */
	public static final String NAME = "VShop";

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
	 * Campo de entrada alfabética destinado a filtrar los productos por
	 * coincidencia de nombre.
	 */
	private JTextField txtBuscarNombre;
	/** Selector de caja desplegable con los rangos acotados de precios. */
	private JComboBox<String> cmbPrecio;
	/**
	 * Selector de caja desplegable con los nombres de las categorías comerciales
	 * disponibles.
	 */
	private JComboBox<String> cmbCategoria;
	/**
	 * Modelo estructural asignado al desplegable para el manejo dinámico de las
	 * categorías.
	 */
	private DefaultComboBoxModel<String> dcbmCategoria;
	/**
	 * Botón encargado de detonar la petición de búsqueda combinada según los
	 * filtros.
	 */
	private JButton btnBuscar;

	/**
	 * Tabla interactiva principal que presenta el catálogo estructurado de
	 * artículos.
	 */
	private JTable tblProductos;
	/**
	 * Modelo lógico para la manipulación y renderizado estructural de celdas en la
	 * cuadrícula.
	 */
	private DefaultTableModel dtmProductos;
	/**
	 * Panel de soporte con barras de desplazamiento verticales y horizontales para
	 * la tabla.
	 */
	private JScrollPane scrpProductos;

	/**
	 * Etiqueta descriptiva superior del bloque lateral de información adicional.
	 */
	private JLabel lblDescripcion;
	/**
	 * Área de visualización de texto multilínea sin edición para la ficha técnica
	 * del producto.
	 */
	private JTextArea txaDescripcion;
	/**
	 * Panel contenedor de soporte para dotar de desplazamiento al bloque lateral de
	 * texto.
	 */
	private JScrollPane scrpDescripcion;
	/**
	 * Botón conmutador que expande o repliega la sección informativa lateral del
	 * ítem.
	 */
	private JButton btnVerMas;
	/**
	 * Botón encargado de procesar la navegación directa o volcado de registros
	 * hacia el carrito.
	 */
	private JButton btnCarrito;

	/**
	 * Almacén secuencial de objetos de tipo {@link Producto} cargados en el estado
	 * actual de la vista.
	 */
	private ArrayList<Producto> productosCargados = new ArrayList<>();

	/**
	 * Constructor por defecto del panel de tienda. Configura las dimensiones del
	 * lienzo y ensambla la totalidad de sus módulos visuales.
	 */
	public VShop() {
		configurarVentana();
		crearComponentes();
	}

	/**
	 * Modifica los parámetros de geometría espacial del panel, fija el color pálido
	 * de fondo institucional de la interfaz y define el nombre exclusivo de la
	 * vista.
	 */
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setBackground(VPrincipal.colorPalido);
		setName(NAME);
	}

	/**
	 * Instancia, parametriza con tipografías y fuentes corporativas, y distribuye
	 * de forma absoluta todos los componentes y controles gráficos Swing
	 * integrados.
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(Fuentes.BOLD.deriveFont(20f));
		lblProductos.setForeground(VPrincipal.colorLetras);
		lblProductos.setBounds(15, 15, 100, 20);
		add(lblProductos);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblNombre.setForeground(VPrincipal.colorLetras);
		lblNombre.setBounds(15, 45, 80, 20);
		add(lblNombre);

		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(93, 42, 155, 26);
		txtBuscarNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtBuscarNombre.setForeground(VPrincipal.colorLetras);
		add(txtBuscarNombre);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(270, 45, 60, 20);
		lblPrecio.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblPrecio.setForeground(VPrincipal.colorLetras);
		add(lblPrecio);

		cmbPrecio = new JComboBox<>(new String[] { "Todos", "< 10 €", "10 - 50 €", "> 50 €" });
		cmbPrecio.setBounds(326, 42, 115, 26);
		cmbPrecio.setFont(Fuentes.REGULAR.deriveFont(16f));
		cmbPrecio.setForeground(VPrincipal.colorLetras);
		add(cmbPrecio);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(467, 45, 97, 20);
		lblCategoria.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblCategoria.setForeground(VPrincipal.colorLetras);
		add(lblCategoria);

		dcbmCategoria = new DefaultComboBoxModel<>();
		dcbmCategoria.addElement("Todas");
		cmbCategoria = new JComboBox<String>(dcbmCategoria);
		cmbCategoria.setBounds(550, 42, 110, 26);
		cmbCategoria.setFont(Fuentes.REGULAR.deriveFont(16f));
		cmbCategoria.setForeground(VPrincipal.colorLetras);
		add(cmbCategoria);

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_PRODUCTO);
		btnBuscar.setBounds(680, 42, 155, 26);
		btnBuscar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnBuscar.setForeground(VPrincipal.colorLetras);
		btnBuscar.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnBuscar);

		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(15, 89, 827, 688);
		scrpProductos.getViewport().setBackground(VPrincipal.colorVibrante);
		add(scrpProductos);

		tblProductos = new JTable();
		tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblProductos.getTableHeader().setFont(Fuentes.MEDIUM.deriveFont(16f));
		tblProductos.getTableHeader().setForeground(VPrincipal.colorLetras);
		tblProductos.getTableHeader().setBackground(VPrincipal.colorNaranjaPatito);
		tblProductos.getTableHeader().setReorderingAllowed(false);
        tblProductos.getTableHeader().setResizingAllowed(false);
		tblProductos.setFont(Fuentes.REGULAR.deriveFont(14f));
		tblProductos.setForeground(VPrincipal.colorLetras);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		btnVerMas = new JButton(ConstantesBotones.VER_MAS);
		btnVerMas.setBounds(15, 797, 100, 28);
		btnVerMas.setBackground(VPrincipal.colorNaranjaPatito);
		btnVerMas.setForeground(VPrincipal.colorLetras);
		btnVerMas.setFont(Fuentes.MEDIUM.deriveFont(16f));
		add(btnVerMas);

		btnCarrito = new JButton(ConstantesBotones.CARRITO);
		btnCarrito.setBounds(750, 797, 92, 28);
		btnCarrito.setBackground(VPrincipal.colorNaranjaPatito);
		btnCarrito.setForeground(VPrincipal.colorLetras);
		btnCarrito.setFont(Fuentes.MEDIUM.deriveFont(16f));
		add(btnCarrito);

		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblDescripcion.setForeground(VPrincipal.colorLetras);
		lblDescripcion.setBounds(864, 87, 100, 20);
		lblDescripcion.setVisible(false);
		add(lblDescripcion);

		scrpDescripcion = new JScrollPane();
		scrpDescripcion.setBounds(865, 128, 524, 385);
		scrpDescripcion.setVisible(false);
		add(scrpDescripcion);

		txaDescripcion = new JTextArea();
		scrpDescripcion.setViewportView(txaDescripcion);
		txaDescripcion.setLineWrap(true);
		txaDescripcion.setWrapStyleWord(true);
		txaDescripcion.setEditable(false);
		txaDescripcion.setBackground(VPrincipal.colorVibrante);
		txaDescripcion.setFont(Fuentes.REGULAR.deriveFont(14f));
		txaDescripcion.setForeground(VPrincipal.colorLetras);

	}

	/**
	 * Configura el {@link DefaultTableModel} para inhabilitar la edición directa
	 * sobre la rejilla, veta la reordenación de columnas mediante arrastre de ratón
	 * y dimensiona los anchos preferidos.
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
		dtmProductos.addColumn("Precio (€)");
		dtmProductos.addColumn("Cantidad");
		dtmProductos.addColumn("+");
		dtmProductos.addColumn("-");

		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(270);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(70);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblProductos.getColumnModel().getColumn(4).setPreferredWidth(50);
	}

	/**
	 * Vuelca los ítems del listado provisto en las celdas lógicas del modelo de la
	 * tabla, restableciendo el bloque lateral informativo y las selecciones
	 * previas. Despliega un aviso si está vacío. * @param productos El
	 * {@link ArrayList} que contiene las instancias de {@link Producto} filtradas.
	 */
	public void cargarTabla(ArrayList<Producto> productos) {
		tblProductos.clearSelection();
		dtmProductos.getDataVector().clear();
		productosCargados = productos;
		btnVerMas.setEnabled(false);
		hideDescripcion();

		if (productos.size() != 0) {
			clearTable();
			Object[] row = new Object[5];
			for (Producto prod : productos) {
				row[0] = prod.getNombre();
				row[1] = prod.getPrecio();
				row[2] = 0;
				row[3] = "+";
				row[4] = "-";
				dtmProductos.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se han encontrado items con los filtros seleccionados", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Remueve de manera secuencial cada fila existente en el modelo de datos de la
	 * tabla.
	 */
	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for (int i = 0; i < r; i++) {
			// System.out.println(i);
			dtmProductos.removeRow(0);
		}
	}

	/**
	 * Incrementa en una unidad el indicador de cantidad preseleccionada para la
	 * fila provista, controlando que no se sobrepase el límite de existencias
	 * (stock) físicas del artículo. * @param fila Posición entera de la fila
	 * referenciada en la cuadrícula.
	 * 
	 * @return {@code true} si la operación fue válida y se sumó una unidad;
	 *         {@code false} si colisiona con el stock.
	 */
	public boolean sumarCantidad(int fila) {
		int cant = (int) dtmProductos.getValueAt(fila, 2);
		int stock = productosCargados.get(fila).getStock();
		if (cant < stock) {
			dtmProductos.setValueAt(cant + 1, fila, 2);
			return true;
		}
		return false;
	}

	/**
	 * Sustrae una unidad en el contador interno de unidades deseadas para una fila
	 * indicada, deteniendo el decremento al alcanzar el valor basal de cero.
	 * * @param fila Posición entera de la fila que se va a modificar.
	 */
	public void restarCantidad(int fila) {
		int cant = (int) dtmProductos.getValueAt(fila, 2);
		if (cant > 0) {
			dtmProductos.setValueAt(cant - 1, fila, 2);
		}
	}

	/**
	 * Devuelve el recuento provisional de unidades de compra marcadas en la celda
	 * correspondiente. * @param fila Índice numérico de la fila de consulta.
	 * 
	 * @return El número entero con las unidades estipuladas.
	 */
	public int getCantidadEnFila(int fila) {
		return (int) dtmProductos.getValueAt(fila, 2);
	}

	/**
	 * Obtiene la instancia completa del artículo en la posición provista. * @param
	 * fila Índice numérico de la consulta en el vector secuencial de productos.
	 * 
	 * @return El objeto de tipo {@link Producto} correspondiente.
	 */
	public Producto getProductoEnFila(int fila) {
		return productosCargados.get(fila);
	}

	/**
	 * Devuelve la referencia directa del componente de tabla de artículos.
	 * * @return El componente objeto {@link JTable} integrado en el panel.
	 */
	public JTable getTblProductos() {
		return tblProductos;
	}

	/**
	 * Revela de forma interactiva el bloque lateral de detalles visuales,
	 * insertando el texto descriptivo y conmutando la etiqueta del botón de
	 * control. * @param descripcion Cadena alfabética que contiene la ficha
	 * informativa del artículo.
	 */
	public void verDescripcion(String descripcion) {
		lblDescripcion.setVisible(true);
		scrpDescripcion.setVisible(true);
		txaDescripcion.setText(descripcion);
		btnVerMas.setText(ConstantesBotones.VER_MENOS);
	}

	/**
	 * Oculta el bloque de detalles de la interfaz gráfica y purga el texto
	 * informativo previo.
	 */
	public void hideDescripcion() {
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		txaDescripcion.setText("");
		btnVerMas.setText(ConstantesBotones.VER_MAS);
	}

	/**
	 * Consulta el estado de visibilidad activo del panel de ficha técnica del
	 * producto. * @return {@code true} si el scroll de descripción está visible;
	 * {@code false} en caso contrario.
	 */
	public boolean isDescripcionVisible() {
		return scrpDescripcion.isVisible();
	}

	/**
	 * Altera el estado de activación o interactividad del botón Ver Más. * @param b
	 * {@code true} para habilitar el control; {@code false} para deshabilitarlo.
	 */
	public void setVerMasEnabled(boolean b) {
		btnVerMas.setEnabled(b);
	}

	/**
	 * Captura los criterios de filtrado seleccionados en la cabecera del módulo de
	 * búsqueda. Sanea de espacios colindantes el texto y discrimina si se mantiene
	 * la opción genérica de búsqueda. * @return Un array indexado de cadenas
	 * {@link String} formateado de la siguiente manera: `[Nombre, Precio,
	 * Categoría]`. Retorna {@code null} si todos mantienen la opción neutra por
	 * defecto.
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
	 * Sincroniza y pobla de manera dinámica las opciones de filtrado del combo de
	 * categorías, anteponiendo la opción neutra general del sistema ("Todas").
	 * * @param categorias Un {@link ArrayList} que contiene los nombres de las
	 * categorías.
	 */
	public void cargarCategorias(ArrayList<String> categorias) {
		dcbmCategoria.removeAllElements();
		dcbmCategoria.addElement("Todas");
		for (String cat : categorias) {
			dcbmCategoria.addElement(cat);
		}
	}

	/**
	 * Purga los vectores de datos de la rejilla, vacía el registro de productos en
	 * memoria, limpia las cajas de texto y restaura los selectores desplegables al
	 * índice cero.
	 */
	public void limpiarDatos() {
		dtmProductos.getDataVector().clear();
		dtmProductos.fireTableDataChanged();
		productosCargados.clear();
		txtBuscarNombre.setText("");
		cmbPrecio.setSelectedIndex(0);
		cmbCategoria.setSelectedIndex(0);
		hideDescripcion();
		btnVerMas.setEnabled(false);
	}

	/**
	 * Vincula los periféricos interactivos, botones de acción y manejadores de
	 * clics de celdas a la lógica unificada del controlador de eventos. * @param c
	 * Instancia de la clase controladora principal ({@link Ctrl}).
	 */
	@Override
	public void setControlador(Ctrl c) {
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand(ConstantesBotones.BUSCAR_PRODUCTO);

		btnVerMas.addActionListener(c);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);

		btnCarrito.addActionListener(c);
		btnCarrito.setActionCommand(ConstantesBotones.CARRITO);

		tblProductos.addMouseListener(c);
		tblProductos.getSelectionModel().addListSelectionListener(c);
	}

}