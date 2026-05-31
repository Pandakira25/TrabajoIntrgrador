package com.dam.view;

import java.awt.Dimension;
import java.awt.Font;
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
		setName("VShop");
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

		// TODO: ver si queremos estos rangos
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

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_PRODUCTO);
		btnBuscar.setBounds(15, 80, 110, 26);
		add(btnBuscar);

		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(15, 115, 520, 415);
		add(scrpProductos);

		tblProductos = new JTable();
		tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		
		btnVerMas = new JButton(ConstantesBotones.VER_MAS);
		btnVerMas.setBounds(15, 542, 92, 28);
		add(btnVerMas);

		btnCarrito = new JButton(ConstantesBotones.CARRITO);
		btnCarrito.setBounds(115, 542, 92, 28);
		add(btnCarrito);

		// TODO: ver por que es visible
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
		dtmProductos.addColumn("");
		dtmProductos.addColumn("");
		dtmProductos.addColumn("");
		// TODO: quisiera que tuviera la cantidad que vas agregando en tiempo real
		// (preguntarle a pilar)

		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(470);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblProductos.getColumnModel().getColumn(4).setPreferredWidth(80);
	}

	// corregir el cargar tabla: hecho
	public void cargarTabla(ArrayList<Producto> productos) {
		if (productos.size() != 0) {
			clearTable();
			Object[] row = new Object[5];
			for (Producto prod : productos) {
				row[0] = prod.getNombre();
				row[1] = prod.getPrecio();
				// TODO: preguntarle a pilar cómo hacerlo
				row[2] = "+";
				row[3] = "-";
				row[4] = "Eliminar";

				dtmProductos.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se han encontrado items con los filtros seleccionados", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for (int i = 0; i < r; i++) {
			// System.out.println(i);
			dtmProductos.removeRow(0);
		}
	}

	public void cargarCategorias(ArrayList<String> categorias) {
		dcbmCategoria.removeAllElements();
		dcbmCategoria.addElement("Todas");
		for (String cat : categorias) {
			dcbmCategoria.addElement(cat);
		}
	}

	public void verDescripcion(String descripción) {
		lblDescripcion.setVisible(true);
		scrpDescripcion.setVisible(true);
		txaDescripcion.setText(descripción);
		
		btnVerMas.setText(ConstantesBotones.VER_MENOS);
	}
	
	public void hideDescripción() {
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		txaDescripcion.setText("");
		
		btnVerMas.setText(ConstantesBotones.VER_MAS);
	}

	public String [] getConsulta() {
		String consulta [] = {
			txtBuscarNombre.getText(),
			(String)cmbCategoria.getSelectedItem(),
			(String)cmbPrecio.getSelectedItem()
		};
		return consulta;
	}

	public void limpiarDatos() {
		dtmProductos.getDataVector().clear();
		dtmProductos.fireTableDataChanged();
		txtBuscarNombre.setText("");
		cmbPrecio.setSelectedIndex(0);
		cmbCategoria.setSelectedIndex(0);
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		btnVerMas.setText(ConstantesBotones.VER_MAS);
	}

	@Override
	public void setControlador(Ctrl c) {
		btnBuscar.addActionListener(c);
		btnVerMas.addActionListener(c);
		btnCarrito.addActionListener(c);
	}

}
