package com.dam.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;

public class VGestionStock extends JPanel implements IPanels {

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	private JTextField txtBuscarNombre;
	private JComboBox<String> cmbPrecio;
	private JComboBox<String> cmbCategoria;
	private DefaultComboBoxModel<String> dcbmCategoria;
	private JButton btnBuscar;

	private JTable tblProductos;
	private DefaultTableModel dtmProductos;
	private JScrollPane scrpProductos;

	private JTextField txtCantidad;
	private JButton btnMas;
	private JButton btnMenos;

	private JLabel lblDescripcion;
	private JTextArea txaDescripcion;
	private JScrollPane scrpDescripcion;
	private JButton btnVerMas;

	public VGestionStock() {
		configurarVentana();
		crearComponentes();
	}

	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setPreferredSize(new Dimension(ANCHO, 620));
	}

	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Gestión de Stock");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(15, 12, 280, 25);
		add(lblTitulo);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductos.setBounds(15, 50, 100, 20);
		add(lblProductos);

		// --- Filtros ---
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(15, 82, 60, 20);
		add(lblNombre);

		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(78, 79, 155, 26);
		add(txtBuscarNombre);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(248, 82, 48, 20);
		add(lblPrecio);

		// TODO: Ver si queremos otro rango?
		cmbPrecio = new JComboBox<>(new String[] { "Todos", "< 10 €", "10 - 50 €", "> 50 €" });
		cmbPrecio.setBounds(300, 79, 115, 26);
		add(cmbPrecio);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(430, 82, 68, 20);
		add(lblCategoria);

		// TODO
		dcbmCategoria = new DefaultComboBoxModel<>();
		dcbmCategoria.addElement("Todas");
		cmbCategoria = new JComboBox<>(dcbmCategoria);
		cmbCategoria.setBounds(501, 79, 110, 26);
		add(cmbCategoria);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(15, 118, 92, 26);
		add(btnBuscar);

		// --- Tabla ---
		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(15, 155, 520, 375);
		add(scrpProductos);

		tblProductos = new JTable();
		tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		// --- Botón Ver más ---
		btnVerMas = new JButton("Ver más");
		btnVerMas.setBounds(15, 542, 92, 28);
		add(btnVerMas);

		// --- Panel derecho: cantidad ---
		// TODO: html?
		JLabel lblCantInfo = new JLabel("<html>Ingrese la cantidad a<br>añadir/eliminar</html>");
		lblCantInfo.setBounds(550, 185, 240, 42);
		add(lblCantInfo);

		txtCantidad = new JTextField("1");
		txtCantidad.setHorizontalAlignment(JTextField.CENTER);
		txtCantidad.setBounds(550, 240, 62, 38);
		add(txtCantidad);
		
		
		//TODO: por que estan deshabiñitados??
		btnMas = new JButton("+");
		btnMas.setBounds(622, 247, 38, 28);
		btnMas.setEnabled(false);
		add(btnMas);

		btnMenos = new JButton("-");
		btnMenos.setBounds(666, 247, 38, 28);
		btnMenos.setEnabled(false);
		add(btnMenos);

		// --- Descripción (oculta por defecto) ---
		// TODO: ver por que no se oculta
		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescripcion.setBounds(550, 315, 100, 20);
		lblDescripcion.setVisible(false);
		add(lblDescripcion);

		txaDescripcion = new JTextArea();
		txaDescripcion.setLineWrap(true);
		txaDescripcion.setWrapStyleWord(true);
		txaDescripcion.setEditable(false);

		scrpDescripcion = new JScrollPane(txaDescripcion);
		scrpDescripcion.setBounds(550, 340, 230, 190);
		scrpDescripcion.setVisible(false);
		add(scrpDescripcion);

	}

	// TODO: corregir
	// No se debería cargar el id
	private void configurarTabla() {
		dtmProductos = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblProductos.setModel(dtmProductos);

		// Columnas modelo: 0=Nombre, 1=Precio, 2=Stock, 3=Categoría, 4=ID (oculto),
		// 5=Descripción (oculta)
		dtmProductos.addColumn("Nombre");
		dtmProductos.addColumn("Precio (€)");
		dtmProductos.addColumn("Stock");
		dtmProductos.addColumn("Categoría");
		dtmProductos.addColumn("ID");
		dtmProductos.addColumn("Descripción");

		// Ocultar columnas ID y Descripción de la vista (datos siguen en el modelo)
		tblProductos.removeColumn(tblProductos.getColumnModel().getColumn(5));
		tblProductos.removeColumn(tblProductos.getColumnModel().getColumn(4));

		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(200);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(75);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(55);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(120);
	}
	
	
	//TODO: corregir
	/**
	 * Cada Object[] contiene: [0] nombre, [1] precio, [2] stock, [3] categoria, [4]
	 * id, [5] descripcion
	 */
	public void cargarTabla(ArrayList<Object[]> lista) {
		dtmProductos.getDataVector().clear();
		for (Object[] fila : lista) {
			dtmProductos.addRow(fila);
		}
		dtmProductos.fireTableDataChanged();
	}

	public void cargarCategorias(ArrayList<String> categorias) {
		dcbmCategoria.removeAllElements();
		dcbmCategoria.addElement("Todas");
		for (String cat : categorias) {
			dcbmCategoria.addElement(cat);
		}
	}
	
	public void toggleDescripcion() {
		boolean mostrar = !scrpDescripcion.isVisible();
		if (mostrar) {
			int fila = tblProductos.getSelectedRow();
			Object desc = fila != -1 ? dtmProductos.getValueAt(fila, 5) : null;
			txaDescripcion.setText(desc != null ? desc.toString() : "Sin descripción.");
		}
		lblDescripcion.setVisible(mostrar);
		scrpDescripcion.setVisible(mostrar);
		btnVerMas.setText(mostrar ? "Ver menos" : "Ver más");
		revalidate();
		repaint();
	}
	
	//TODO: deberiamos hacer un método para habilitar y deshabilitar el de menos dependiendo de la cantidad de stock
	public void setStockButtonsEnabled(boolean b) {
		btnMas.setEnabled(b);
		btnMenos.setEnabled(b);
	}
	
	
	//TODO: no deberíamos obtener asi el id
	public int getIdProductoSeleccionado() {
		int fila = tblProductos.getSelectedRow();
		if (fila == -1)
			return -1;
		return (int) dtmProductos.getValueAt(fila, 4);
	}

	public int getCantidad() {
		try {
			return Integer.parseInt(txtCantidad.getText().trim());
		} catch (NumberFormatException e) {
			return 1;
		}
	}

	public String getNombreFiltro() {
		return txtBuscarNombre.getText().trim();
	}

	public String getPrecioFiltro() {
		return (String) cmbPrecio.getSelectedItem();
	}

	public String getCategoriaFiltro() {
		return (String) cmbCategoria.getSelectedItem();
	}

	public void limpiarDatos() {
		dtmProductos.getDataVector().clear();
		dtmProductos.fireTableDataChanged();
		txtBuscarNombre.setText("");
		cmbPrecio.setSelectedIndex(0);
		cmbCategoria.setSelectedIndex(0);
		txtCantidad.setText("1");
		btnMas.setEnabled(false);
		btnMenos.setEnabled(false);
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		btnVerMas.setText("Ver más");
	}

	@Override
	public void setControlador(Ctrl c) {
		btnBuscar.addActionListener(c);
		btnMas.addActionListener(c);
		btnMenos.addActionListener(c);
		btnVerMas.addActionListener(c);
	}

}
