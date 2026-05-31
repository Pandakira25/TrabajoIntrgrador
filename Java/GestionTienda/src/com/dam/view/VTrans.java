package com.dam.view;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;

public class VTrans extends JPanel implements IPanels {

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	private JTable tblTransacciones;
	private DefaultTableModel dtmTransacciones;
	private JScrollPane scrpTransacciones;

	public VTrans() {
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

		JLabel lblTitulo = new JLabel("Transacciones");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(36, 20, 300, 25);
		add(lblTitulo);

		JLabel lblInfo = new JLabel("Histórico de todas las transacciones realizadas:");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInfo.setBounds(35, 55, 380, 20);
		add(lblInfo);

		scrpTransacciones = new JScrollPane();
		scrpTransacciones.setBounds(35, 82, 710, 430);
		add(scrpTransacciones);

		tblTransacciones = new JTable();
		tblTransacciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpTransacciones.setViewportView(tblTransacciones);
		configurarTabla();
	}

	//TODO: lo del id que no se tiene que ver
	private void configurarTabla() {
		dtmTransacciones = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblTransacciones.setModel(dtmTransacciones);

		dtmTransacciones.addColumn("ID Transacción");
		dtmTransacciones.addColumn("Comprador");
		dtmTransacciones.addColumn("Empleado");
		dtmTransacciones.addColumn("Importe (€)");

		tblTransacciones.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblTransacciones.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblTransacciones.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblTransacciones.getColumnModel().getColumn(3).setPreferredWidth(110);
	}
	
	//TODO: Corregirlo
	public void cargarTabla(ArrayList<Object[]> datos) {
		dtmTransacciones.getDataVector().clear();
		for (Object[] fila : datos) {
			dtmTransacciones.addRow(fila);
		}
		dtmTransacciones.fireTableDataChanged();
	}

	public void limpiarDatos() {
		dtmTransacciones.getDataVector().clear();
		dtmTransacciones.fireTableDataChanged();
	}

	@Override
	public void setControlador(Ctrl c) {
		//TODO
	}
}
