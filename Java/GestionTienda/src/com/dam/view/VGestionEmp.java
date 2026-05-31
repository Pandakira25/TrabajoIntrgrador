package com.dam.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;

public class VGestionEmp extends JPanel implements IPanels {

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	private JTextField txtNombre;
	private JPasswordField txtContrasenia;
	private JTextField txtTel;
	private JTextField txtNSeguridad;
	private JTextField txtIban;
	private JButton btnRegistrarEmp;
	private JButton btnLimpiar;
	private JTable tblEmpleados;
	private DefaultTableModel dtmEmpleados;
	private JScrollPane scrpEmpleados;
	private JButton btnEliminarEmp;
	private JLabel lblListado;

	public VGestionEmp() {
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

		JLabel lblTitulo = new JLabel("Gestión de Empleados");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(15, 16, 300, 25);
		add(lblTitulo);

	
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(35, 60, 80, 20);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(125, 57, 200, 26);
		add(txtNombre);

		JLabel lblTel = new JLabel("Teléfono:");
		lblTel.setBounds(370, 60, 80, 20);
		add(lblTel);

		txtTel = new JTextField();
		txtTel.setBounds(455, 57, 150, 26);
		add(txtTel);

		JLabel lblContrasenia = new JLabel("Contraseña:");
		lblContrasenia.setBounds(35, 98, 85, 20);
		add(lblContrasenia);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(125, 95, 200, 26);
		add(txtContrasenia);

		JLabel lblNSeguridad = new JLabel("N. Seguridad:");
		lblNSeguridad.setBounds(370, 98, 95, 20);
		add(lblNSeguridad);

		txtNSeguridad = new JTextField();
		txtNSeguridad.setBounds(468, 95, 150, 26);
		add(txtNSeguridad);

		JLabel lblIban = new JLabel("IBAN:");
		lblIban.setBounds(35, 136, 80, 20);
		add(lblIban);

		txtIban = new JTextField();
		txtIban.setBounds(125, 133, 380, 26);
		add(txtIban);

		btnRegistrarEmp = new JButton("Registrar Empleado");
		btnRegistrarEmp.setBounds(150, 178, 175, 30);
		add(btnRegistrarEmp);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(345, 178, 100, 30);
		add(btnLimpiar);

		
		lblListado = new JLabel("Listado de Empleados:");
		lblListado.setBounds(35, 228, 220, 20);
		add(lblListado);

		scrpEmpleados = new JScrollPane();
		scrpEmpleados.setBounds(35, 253, 710, 215);
		add(scrpEmpleados);

		tblEmpleados = new JTable();
		tblEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpEmpleados.setViewportView(tblEmpleados);
		configurarTabla();
		
		
		//Por defecto deshabilitado
		btnEliminarEmp = new JButton("Eliminar Empleado");
		btnEliminarEmp.setBounds(535, 480, 175, 30);
		btnEliminarEmp.setEnabled(false);
		add(btnEliminarEmp);
		
	}

	//TODO: corregir
	//No se debería ver el id
	private void configurarTabla() {
		dtmEmpleados = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblEmpleados.setModel(dtmEmpleados);

		dtmEmpleados.addColumn("ID");
		dtmEmpleados.addColumn("Nombre");
		dtmEmpleados.addColumn("Teléfono");
		dtmEmpleados.addColumn("N. Seguridad");
		dtmEmpleados.addColumn("IBAN");

		tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(40);
		tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(160);
		tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(90);
		tblEmpleados.getColumnModel().getColumn(3).setPreferredWidth(130);
		tblEmpleados.getColumnModel().getColumn(4).setPreferredWidth(220);
	}

	//TODO: corregir cargar tabla
	public void cargarTabla(ArrayList<Object[]> datos) {
		dtmEmpleados.getDataVector().clear();
		for (Object[] fila : datos) {
			dtmEmpleados.addRow(fila);
		}
		dtmEmpleados.fireTableDataChanged();
	}

	public void setEliminarEnabled(boolean b) {
		btnEliminarEmp.setEnabled(b);
	}

	//TODO: Revisar bien esto
	public int getIdEmpleadoSeleccionado() {
		int fila = tblEmpleados.getSelectedRow();
		if (fila == -1)
			return -1;
		return (int) tblEmpleados.getValueAt(fila, 0);
	}
	
	//TODO: corregir lo que retorna tiene que retornar un Empleado
	public String[] obtenerDatosFormulario() {
		String nombre = txtNombre.getText().trim();
		String contra = new String(txtContrasenia.getPassword()).trim();
		String telStr = txtTel.getText().trim();
		String nSeguridad = txtNSeguridad.getText().trim();
		String iban = txtIban.getText().trim();

		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (contra.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (telStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El teléfono es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		try {
			Integer.parseInt(telStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El teléfono debe ser un número.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return new String[] { nombre, contra, telStr, nSeguridad, iban };
	}

	public void limpiarDatos() {
		txtNombre.setText("");
		txtContrasenia.setText("");
		txtTel.setText("");
		txtNSeguridad.setText("");
		txtIban.setText("");
	}

	@Override
	public void setControlador(Ctrl c) {
		btnRegistrarEmp.addActionListener(c);
		btnLimpiar.addActionListener(c);
		btnEliminarEmp.addActionListener(c);
	}

	public JTable getTblEmpleados() {
		return tblEmpleados;
	}

	public JButton getBtnRegistrarEmp() {
		return btnRegistrarEmp;
	}

	public JButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public JButton getBtnEliminarEmp() {
		return btnEliminarEmp;
	}

}
