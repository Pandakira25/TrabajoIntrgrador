package com.dam.view;

import java.awt.Dimension;
import java.awt.Font;
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

public class VShop extends JPanel implements IPanels {

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

	private JLabel lblDescripcion;
	private JTextArea txaDescripcion;
	private JScrollPane scrpDescripcion;
	private JButton btnVerMas;
	private JButton btnCarrito;

	public VShop() {
		configurarVentana();
		crearComponentes();
	}
	
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setPreferredSize(new Dimension(ANCHO, 600));
	}

	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductos.setBounds(15, 15, 100, 20);
		add(lblProductos);

		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(15, 45, 60, 20);
		add(lblNombre);

		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(78, 42, 155, 26);
		add(txtBuscarNombre);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(248, 45, 48, 20);
		add(lblPrecio);
		
		//TODO: ver si queremos estos rangos
		cmbPrecio = new JComboBox<>(new String[] { "Todos", "< 10 €", "10 - 50 €", "> 50 €" });
		cmbPrecio.setBounds(300, 42, 115, 26);
		add(cmbPrecio);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(430, 45, 68, 20);
		add(lblCategoria);

		dcbmCategoria = new DefaultComboBoxModel<>();
		dcbmCategoria.addElement("Todas");
		cmbCategoria = new JComboBox<>(dcbmCategoria);
		cmbCategoria.setBounds(501, 42, 110, 26);
		add(cmbCategoria);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(15, 80, 92, 26);
		add(btnBuscar);


		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(15, 115, 520, 415);
		add(scrpProductos);

		tblProductos = new JTable();
		tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		
		btnVerMas = new JButton("Ver más");
		btnVerMas.setBounds(15, 542, 92, 28);
		add(btnVerMas);

		btnCarrito = new JButton("Carrito");
		btnCarrito.setBounds(115, 542, 92, 28);
		add(btnCarrito);

		//TODO: ver por que es visible
		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescripcion.setBounds(550, 115, 100, 20);
		lblDescripcion.setVisible(false);
		add(lblDescripcion);
		
		
		txaDescripcion = new JTextArea();
		txaDescripcion.setLineWrap(true);
		txaDescripcion.setWrapStyleWord(true);
		txaDescripcion.setEditable(false);

		scrpDescripcion = new JScrollPane(txaDescripcion);
		scrpDescripcion.setBounds(550, 140, 235, 385);
		scrpDescripcion.setVisible(false);
		add(scrpDescripcion);
		
	}
	
	
	//TODO: No tenemos que cargar el id
	private void configurarTabla() {
		dtmProductos = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblProductos.setModel(dtmProductos);
		dtmProductos.addColumn("Nombre");
		dtmProductos.addColumn("Precio (€)");
		dtmProductos.addColumn("Categoría");
		dtmProductos.addColumn("+");
		dtmProductos.addColumn("-");
		dtmProductos.addColumn("ID");
		dtmProductos.addColumn("Descripción");

		
		tblProductos.removeColumn(tblProductos.getColumnModel().getColumn(6));
		tblProductos.removeColumn(tblProductos.getColumnModel().getColumn(5));

		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(185);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(70);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(105);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblProductos.getColumnModel().getColumn(4).setPreferredWidth(50);
	}
	
	//Corregir
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
	
	//TODO: la descripcion la pasamos desde el controlador solo tenemos que hacer que se haga visible y que cargue el texto
	public void toggleDescripcion() {
		boolean mostrar = !scrpDescripcion.isVisible();
		if (mostrar) {
			int fila = tblProductos.getSelectedRow();
			Object desc = fila != -1 ? dtmProductos.getValueAt(fila, 6) : null;
			txaDescripcion.setText(desc != null ? desc.toString() : "Sin descripción.");
		}
		lblDescripcion.setVisible(mostrar);
		scrpDescripcion.setVisible(mostrar);
		btnVerMas.setText(mostrar ? "Ver menos" : "Ver más");
	}

	//TODO: no podemos obtener así el id
	public int getIdProductoSeleccionado() {
		int fila = tblProductos.getSelectedRow();
		if (fila == -1)
			return -1;
		return (int) dtmProductos.getValueAt(fila, 5);
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
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		btnVerMas.setText("Ver más");
	}

	@Override
	public void setControlador(Ctrl c) {
		btnBuscar.addActionListener(c);
		btnVerMas.addActionListener(c);
		btnCarrito.addActionListener(c);
	}


}
