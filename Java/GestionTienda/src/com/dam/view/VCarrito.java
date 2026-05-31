package com.dam.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Producto;

public class VCarrito extends JPanel implements IPanels {

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	private JTable tblCarrito;
	private DefaultTableModel dtmCarrito;
	private JScrollPane scrpCarrito;
	private JButton btnPagar;

	public VCarrito() {
		configurarVentana();
		crearComponentes();
	}

	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setPreferredSize(new Dimension(ANCHO, 590));
	}
	
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Tu carrito", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitulo.setBounds(0, 20, ANCHO, 35);
		add(lblTitulo);

		scrpCarrito = new JScrollPane();
		scrpCarrito.setBounds(15, 75, 760, 445);
		add(scrpCarrito);

		tblCarrito = new JTable();
		tblCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpCarrito.setViewportView(tblCarrito);
		configurarTabla();

		btnPagar = new JButton("Pagar");
		btnPagar.setBounds((ANCHO - 120) / 2, 535, 120, 30);
		add(btnPagar);
	}

	//TODO: corregir
	//No se debería ver el id
	private void configurarTabla() {
		dtmCarrito = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblCarrito.setModel(dtmCarrito);

		dtmCarrito.addColumn("Nombre");
		dtmCarrito.addColumn("Precio (€)");
		dtmCarrito.addColumn("+");
		dtmCarrito.addColumn("-");
		dtmCarrito.addColumn("Eliminar");
		dtmCarrito.addColumn("ID");
		dtmCarrito.addColumn("Cantidad");
		tblCarrito.removeColumn(tblCarrito.getColumnModel().getColumn(6));
		tblCarrito.removeColumn(tblCarrito.getColumnModel().getColumn(5));

		tblCarrito.getColumnModel().getColumn(0).setPreferredWidth(470);
		tblCarrito.getColumnModel().getColumn(1).setPreferredWidth(80);
		tblCarrito.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblCarrito.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblCarrito.getColumnModel().getColumn(4).setPreferredWidth(80);
	}
	
	
	//TODO: corregir el cargar tabla
	public void cargarTabla(ArrayList<Producto> producto) {
		dtmCarrito.getDataVector().clear();
		for (Producto fila : producto) {
			dtmCarrito.addRow(fila);
		}
		dtmCarrito.fireTableDataChanged();
	}

	//TODO: revisar bien esto
	public int getIdFilaSeleccionada() {
		int fila = tblCarrito.getSelectedRow();
		if (fila == -1)
			return -1;
		return (int) dtmCarrito.getValueAt(fila, 5);
	}
	
	//TODO: no se si vamos a usar esto
	public int getCantidadFilaSeleccionada() {
		int fila = tblCarrito.getSelectedRow();
		if (fila == -1)
			return 0;
		return (int) dtmCarrito.getValueAt(fila, 6);
	}

	public void limpiarDatos() {
		dtmCarrito.getDataVector().clear();
		dtmCarrito.fireTableDataChanged();
	}

	@Override
	public void setControlador(Ctrl c) {
		btnPagar.addActionListener(c);
	}

}
