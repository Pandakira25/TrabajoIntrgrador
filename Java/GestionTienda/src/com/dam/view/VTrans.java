package com.dam.view;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
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
import com.dam.model.pojos.Transacciones;
import javax.swing.JComboBox;

public class VTrans extends JPanel implements IPanels {
	public static final String NAME = "VTrans";

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	private JTable tblTransacciones;
	private DefaultTableModel dtmTransacciones;
	private JScrollPane scrpTransacciones;
	private JTextField txtBuscarNombre;
	private JButton btnBuscar;
	private JLabel lblBuscarEmpleado;
	private JComboBox<String> cmbBuscarEmpleado;
	private DefaultComboBoxModel<String> dfcbBuscarEmpleado;

	public VTrans() {
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

		JLabel lblTitulo = new JLabel("Transacciones");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(36, 20, 300, 25);
		add(lblTitulo);

		JLabel lblInfo = new JLabel("Histórico de todas las transacciones realizadas:");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInfo.setBounds(36, 85, 380, 20);
		add(lblInfo);

		scrpTransacciones = new JScrollPane();
		scrpTransacciones.setBounds(35, 112, 710, 400);
		add(scrpTransacciones);

		tblTransacciones = new JTable();
		tblTransacciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpTransacciones.setViewportView(tblTransacciones);
		configurarTabla();
		
		JLabel lblBuscarComprador = new JLabel("Comprador:");
		lblBuscarComprador.setBounds(35, 55, 75, 20);
		add(lblBuscarComprador);

		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(115, 52, 155, 26);
		add(txtBuscarNombre);

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_TRANSACCION);
		btnBuscar.setBounds(474, 52, 155, 26);
		add(btnBuscar);
		
		lblBuscarEmpleado = new JLabel("Empleado:");
		lblBuscarEmpleado.setBounds(281, 63, 58, 12);
		add(lblBuscarEmpleado);
		
		dfcbBuscarEmpleado = new DefaultComboBoxModel<String>();
		dfcbBuscarEmpleado.addElement("Todos");
		cmbBuscarEmpleado = new JComboBox<String>(dfcbBuscarEmpleado);
		cmbBuscarEmpleado.setBounds(345, 55, 112, 20);
		add(cmbBuscarEmpleado);
	}
	
	public String[] getConsulta() {
		String nombre = txtBuscarNombre.getText().trim();
		String empleado = (String)cmbBuscarEmpleado.getSelectedItem();
		
		if(nombre.isEmpty() && empleado.equals("todos")) {
			return null;
		}
		
	    return new String [] {
	    		nombre.isEmpty()? null : txtBuscarNombre.getText(),
	    		empleado.equals("Todos")? null : (String)cmbBuscarEmpleado.getSelectedItem()
	    };
	}

	private void configurarTabla() {
		dtmTransacciones = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblTransacciones.getTableHeader().setReorderingAllowed(false);
		tblTransacciones.setModel(dtmTransacciones);

		dtmTransacciones.addColumn("Comprador");
		dtmTransacciones.addColumn("Empleado");
		dtmTransacciones.addColumn("Importe (€)");

		tblTransacciones.getColumnModel().getColumn(0).setPreferredWidth(200);
		tblTransacciones.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblTransacciones.getColumnModel().getColumn(2).setPreferredWidth(110);
	}
	
	//Le tenemos que pasar un string [][] con la siguiente estructura
	/*
	 * {
	 * {nombreComprador, nombreEmpleado, importeTotal}
	 * }
	 * */
	public void cargarTabla(String infoTransaccion [][]) {
		tblTransacciones.clearSelection();
		dtmTransacciones.getDataVector().clear();
		
		if(infoTransaccion.length != 0) {
			clearTable();
			Object[] row = new Object[3];
			for(int i = 0; i < infoTransaccion.length; i++) {
				row[0] = infoTransaccion[i][0];
				row[1] = infoTransaccion[i][1];
				row[2] = infoTransaccion[i][2];
				
				dtmTransacciones.addRow(row);
			}
		}else {
			JOptionPane.showMessageDialog(this, "No se han encontrado items con los filtros seleccionados","Mensaje",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void clearTable() {
		int r = dtmTransacciones.getRowCount();
		for(int i = 0; i < r; i++) {
			//System.out.println(i);
			dtmTransacciones.removeRow(0);
		}
	}

	public void limpiarDatos() {
		dtmTransacciones.getDataVector().clear();
		dtmTransacciones.fireTableDataChanged();
	}
	
	public void chargeEmp(String emps[]) {
		dfcbBuscarEmpleado.removeAllElements();
		dfcbBuscarEmpleado.addElement("Todos");
		for (String emp : emps) {
			dfcbBuscarEmpleado.addElement(emp);
		}
	}

	@Override
	public void setControlador(Ctrl c) {
		btnBuscar.addActionListener(c);
	    btnBuscar.setActionCommand(ConstantesBotones.BUSCAR_TRANSACCION);
	}
	
	public JTable getTblTransacciones() {
		return tblTransacciones;
	}
}
