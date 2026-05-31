package com.dam.view;

import java.awt.Font;
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

public class VGestionProd extends JPanel implements IPanels {

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

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
	private JButton btnEliminarProd;

	public VGestionProd() {
		configurarVentana();
		crearComponentes();
	}
	
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
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
		lblListado.setBounds(35, 228, 220, 20);
		add(lblListado);

		scrpProductos = new JScrollPane();
		scrpProductos.setBounds(35, 253, 710, 200);
		add(scrpProductos);

		tblProductos = new JTable();
		tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpProductos.setViewportView(tblProductos);
		configurarTabla();

		btnEliminarProd = new JButton(ConstantesBotones.ELIMINAR_PRODUCTO);
		btnEliminarProd.setBounds(543, 465, 163, 30);
		btnEliminarProd.setEnabled(false);
		add(btnEliminarProd);
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
		dtmProductos.addColumn("Categoría");
		dtmProductos.addColumn("Precio");
		dtmProductos.addColumn("Stock");
		dtmProductos.addColumn("Descripción"); //TODO: preguntarles a los profes si deberíamos hacerlo con un ver más

		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(130);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(70);
		tblProductos.getColumnModel().getColumn(4).setPreferredWidth(50);
		tblProductos.getColumnModel().getColumn(5).setPreferredWidth(260);
	}
	
	
	public void cargarTabla(ArrayList<Producto> productos) {
		if (productos.size() != 0) {
			clearTable();
			Object[] row = new Object[5];
			for (Producto prod : productos) {
				row[0] = prod.getNombre();
				row[1] = prod.getCategoria();
				row[2] = prod.getPrecio();
				row[2] = prod.getStock();
				row[2] = prod.getDescripcion();

				dtmProductos.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se han encontrado items con los filtros seleccionados", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for(int i = 0; i < r; i++) {
			//System.out.println(i);
			dtmProductos.removeRow(0);
		}
	}
	
	//Para cuando tengamos que modificar un producto
	public void cargarProductoEnForm() {
		int fila = tblProductos.getSelectedRow();
		
		txtNombre.setText((String) tblProductos.getValueAt(fila, 1));
		txtCategoria.setText((String) tblProductos.getValueAt(fila, 2));
		txtPrecio.setText(String.valueOf(tblProductos.getValueAt(fila, 3)));
		txtStock.setText(String.valueOf(tblProductos.getValueAt(fila, 4)));
		txtDescripcion.setText((String) tblProductos.getValueAt(fila, 5));
	}

	public void setModificarEnabled(boolean b) {
		btnModificarProd.setEnabled(b);
	}

	public void setEliminarEnabled(boolean b) {
		btnEliminarProd.setEnabled(b);
	}
	
	//TODO: agregaría otro setUnabled para agregar producto cuando le demos a modificar

	//TODO: corregir lo que retorna tiene que retornar un producto y validación de datos
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

		if(valid) {
			return new Producto(nombre, categoria, precio, stock, descripcion);
		}else {
			return null;
		}
	}

	public void limpiarDatos() {
		txtNombre.setText("");
		txtCategoria.setText("");
		txtPrecio.setText("");
		txtStock.setText("");
		txtDescripcion.setText("");
		tblProductos.clearSelection();
		btnModificarProd.setEnabled(false);
		btnEliminarProd.setEnabled(false);
	}

	@Override
	public void setControlador(Ctrl c) {
		btnAgregarProd.addActionListener(c);
		btnModificarProd.addActionListener(c);
		btnLimpiar.addActionListener(c);
		btnEliminarProd.addActionListener(c);
	}
}
