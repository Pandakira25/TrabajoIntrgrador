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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Producto;
import com.dam.model.pojos.Producto;

public class VGestionStock extends JPanel implements IPanels {
	public static final String NAME = "VGestionStock";

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
	
	private ArrayList<Producto> productosCargados = new ArrayList<>();

	public VGestionStock() {
		configurarVentana();
		crearComponentes();
	}

	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setBackground(VPrincipal.colorPalido);
		setName(NAME);
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

		cmbPrecio = new JComboBox<>(new String[] { "Todos", "< 10 €", "10 - 50 €", "> 50 €" });
		cmbPrecio.setBounds(300, 79, 115, 26);
		add(cmbPrecio);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(430, 82, 68, 20);
		add(lblCategoria);

		dcbmCategoria = new DefaultComboBoxModel<>();
		dcbmCategoria.addElement("Todas");
		cmbCategoria = new JComboBox<>(dcbmCategoria);
		cmbCategoria.setBounds(501, 79, 110, 26);
		add(cmbCategoria);

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_PRODUCTO);
		btnBuscar.setBounds(15, 118, 115, 26);
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
		btnVerMas = new JButton(ConstantesBotones.VER_MAS);
		btnVerMas.setBounds(15, 542, 92, 28);
		add(btnVerMas);

		// --- Panel derecho: cantidad ---
		JLabel lblCantInfo = new JLabel("Ingrese la cantidad a añadir/eliminar");
		lblCantInfo.setBounds(545, 204, 224, 26);
		add(lblCantInfo);

		txtCantidad = new JTextField("1");
		txtCantidad.setHorizontalAlignment(JTextField.CENTER);
		txtCantidad.setBounds(550, 240, 62, 38);
		add(txtCantidad);

		btnMas = new JButton(new ImageIcon(ConstantesBotones.MASICONO));
		btnMas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMas.setBounds(622, 247, 38, 28);
		add(btnMas);

		btnMenos = new JButton(new ImageIcon(ConstantesBotones.MENOSICONO));
		btnMenos.setBounds(666, 247, 38, 28);
		add(btnMenos);

		// --- Descripción (oculta por defecto) ---
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

	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for(int i = 0; i < r; i++) {
			//System.out.println(i);
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
		btnVerMas.setActionCommand(ConstantesBotones.VER_MENOS);
	}
	
	public void hideDescripcion() {
		lblDescripcion.setVisible(false);
		scrpDescripcion.setVisible(false);
		txaDescripcion.setText("");
		
		btnVerMas.setText(ConstantesBotones.VER_MAS);
		btnVerMas.setActionCommand(ConstantesBotones.VER_MAS);
	}
	
    public void setVerMasEnabled(boolean b) {
        btnVerMas.setEnabled(b);
    }
    
    public void setBtnMasEnabled(boolean b) {
    	btnMas.setEnabled(b);
    }
	
    public void setBtnMenosEnabled(boolean b) {
        btnMenos.setEnabled(b);
    }

	public int getCantidad() {
		try {
			return Integer.parseInt(txtCantidad.getText().trim());
		} catch (NumberFormatException e) {
			return 1;
		}
	}

	/**
	 * Valida la cantidad introducida para ajustes de stock.
	 * @return cantidad válida o null si hay error
	 */
	public Integer obtenerCantidadValidada() {
		try {
			return Producto.parseCantidadMovimiento(txtCantidad.getText());
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error de datos", JOptionPane.ERROR_MESSAGE);
			return null;
		}
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
		btnVerMas.setText(ConstantesBotones.VER_MAS);
	}

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

	public String [] getConsulta() {
		String nombre = txtBuscarNombre.getText().trim();
        String precio = (String) cmbPrecio.getSelectedItem();
        String categoria = (String) cmbCategoria.getSelectedItem();

        if (nombre.isEmpty() && precio.equals("Todos") && categoria.equals("Todas")) {
            return null;
        }

        return new String[] {
            nombre.isEmpty() ? null : nombre,
            precio.equals("Todos") ? null : precio,
            categoria.equals("Todas") ? null : categoria
        };	}

	public boolean isDescripcionVisible() {
		return scrpDescripcion.isVisible();
	}

	public JTable getTblProductos() {
		return tblProductos;
	}

	public Producto getProductoEnFila(int fila) {
		return productosCargados.get(fila);
	}

}
