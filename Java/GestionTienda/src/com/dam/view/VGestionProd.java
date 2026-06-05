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

public class VGestionProd extends JPanel implements IPanels {
	public static final String NAME = "VGestionProd";

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	private ArrayList<Producto> productosCargados = new ArrayList<>();
	private int idSeleccionado = -1;

	private JTextField txtNombre;
	private JTextField txtCategoria;
	private JTextField txtPrecio;
	private JTextField txtStock;
	private JTextField txtDescripcion;
	private JButton btnAgregarProd;
	private JButton btnModificarProd;
	private JButton btnLimpiar;
	private JTable tblProductos;
	private DefaultTableModel dtmProductos;
	private JScrollPane scrpProductos;
	private JButton btnDeshabilitarProd;
	private JTextField txtNombreBuscar;
	private JComboBox<String> cmbPrecioBuscar;
	private JComboBox<String> cmbCategoriaBuscar;
	private DefaultComboBoxModel<String> dcbmCategoria;
	private JButton btnBuscar;
	private JButton btnHabilitarProd;
	private JLabel lblVerDescripcion;
	private JTextArea txaDescripcion;
	private JScrollPane scrpDescripcion;
	private JButton btnVerMas;

	public VGestionProd() {
		configurarVentana();
		crearComponentes();
	}

	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setName(NAME);
	}

	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Productos");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(15, 16, 300, 25);
		add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
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

	public void setVerMasEnabled(boolean b) {
		btnVerMas.setEnabled(b);
	}

	public void verDescripcion(String descripción) {
		lblVerDescripcion.setVisible(true);
		scrpDescripcion.setVisible(true);
		txaDescripcion.setText(descripción);

		btnVerMas.setText(ConstantesBotones.VER_MENOS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MENOS);
	}

	public void hideDescripcion() {
		lblVerDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		txaDescripcion.setText("");

		btnVerMas.setText(ConstantesBotones.VER_MAS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);
	}

	public void cargarCategorias(ArrayList<String> categorias) {
		dcbmCategoria.removeAllElements();
		dcbmCategoria.addElement("Todas");
		for (String cat : categorias) {
			dcbmCategoria.addElement(cat);
		}
	}

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

	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for (int i = 0; i < r; i++) {
			// System.out.println(i);
			dtmProductos.removeRow(0);
		}
	}

	// Para cuando tengamos que modificar un producto
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

	public int getIdSeleccionado() {
		return idSeleccionado;
	}

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

	public void setModificarEnabled(boolean b) {
		btnModificarProd.setEnabled(b);
	}

	public void setEliminarEnabled(boolean b) {
		btnDeshabilitarProd.setEnabled(b);
	}

	public void setHabilitarEnabled(boolean b) {
		btnHabilitarProd.setEnabled(b);
	}

	public void setAgregarEnabled(boolean b) {
		btnAgregarProd.setEnabled(b);
	}

	// TODO: validación de datos
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

	public Object getSelectedModel() {
		return tblProductos.getSelectionModel();
	}

	public JTable getTblProductos() {
		return tblProductos;
	}

	public String getNombreSeleccionado() {
		return (String) tblProductos.getValueAt(tblProductos.getSelectedRow(), 0);
	}

	public Producto getProductoEnFila(int fila) {
		return productosCargados.get(fila);
	}
}
