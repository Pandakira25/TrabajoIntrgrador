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
	public static final String NAME = "VShop";

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
	
	private ArrayList<Producto> productosCargados = new ArrayList<>();


	public VShop() {
		configurarVentana();
		crearComponentes();
	}

	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setPreferredSize(new Dimension(ANCHO, 600));
		setName(NAME);
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

	private void clearTable() {
		int r = dtmProductos.getRowCount();
		for (int i = 0; i < r; i++) {
			// System.out.println(i);
			dtmProductos.removeRow(0);
		}
	}

	public boolean sumarCantidad(int fila) {
	    int cant = (int) dtmProductos.getValueAt(fila, 2);
	    int stock = productosCargados.get(fila).getStock();
	    if (cant < stock) {
	        dtmProductos.setValueAt(cant + 1, fila, 2);
	        return true;
	    }
	    return false;
	}

    public void restarCantidad(int fila) {
        int cant = (int) dtmProductos.getValueAt(fila, 2);
        if (cant > 0) {
            dtmProductos.setValueAt(cant - 1, fila, 2);
        }
    }

    public int getCantidadEnFila(int fila) {
        return (int) dtmProductos.getValueAt(fila, 2);
    }

    public Producto getProductoEnFila(int fila) {
        return productosCargados.get(fila);
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void verDescripcion(String descripcion) {
        lblDescripcion.setVisible(true);
        scrpDescripcion.setVisible(true);
        txaDescripcion.setText(descripcion);
        btnVerMas.setText(ConstantesBotones.VER_MENOS);
    }

    public void hideDescripcion() {
        lblDescripcion.setVisible(false);
        scrpDescripcion.setVisible(false);
        txaDescripcion.setText("");
        btnVerMas.setText(ConstantesBotones.VER_MAS);
    }

    public boolean isDescripcionVisible() {
        return scrpDescripcion.isVisible();
    }

    public void setVerMasEnabled(boolean b) {
        btnVerMas.setEnabled(b);
    }

    public String[] getConsulta() {
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
        };
    }

    public void cargarCategorias(ArrayList<String> categorias) {
        dcbmCategoria.removeAllElements();
        dcbmCategoria.addElement("Todas");
        for (String cat : categorias) {
            dcbmCategoria.addElement(cat);
        }
    }

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
