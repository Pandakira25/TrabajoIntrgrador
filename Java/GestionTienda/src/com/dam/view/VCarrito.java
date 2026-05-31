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

		btnPagar = new JButton(ConstantesBotones.PAGAR);
		btnPagar.setBounds((ANCHO - 120) / 2, 535, 120, 30);
		add(btnPagar);
	}

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
	
	public void cargarTabla(ArrayList<Object[]> datos) {
		dtmCarrito.getDataVector().clear();
		for (Object[] fila : datos) {
			dtmCarrito.addRow(fila);
		}
		dtmCarrito.fireTableDataChanged();
	}

	public int getIdFilaSeleccionada() {
		int fila = tblCarrito.getSelectedRow();
		if (fila == -1)
			return -1;
		return (int) dtmCarrito.getValueAt(fila, 5);
	}

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

	public JTable getTblCarrito() {
		return tblCarrito;
	}

	public DefaultTableModel getDtmCarrito() {
		return dtmCarrito;
	}

	public JButton getBtnPagar() {
		return btnPagar;
	}

}
