package com.dam.view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Empleado;
import com.dam.model.pojos.Producto;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class VGestionEmp extends JPanel implements IPanels {
	public static final String NAME = "VGestionEmp";

	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	private JTextField txtNombre;
	private JMenuBar mnBarraMenu;
	private JMenuItem mntmGestionStock;
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
	private JTextField txtBuscarNombre;
	private JLabel lblAutorizacion;
	private JSpinner spnAutorizacion;
	private JButton btnBuscarNombre;

	public VGestionEmp() {
		configurarVentana();
		crearComponentes();
	}
	
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setName(NAME);
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
		txtNSeguridad.setBounds(455, 96, 150, 26);
		add(txtNSeguridad);

		JLabel lblIban = new JLabel("IBAN:");
		lblIban.setBounds(35, 136, 80, 20);
		add(lblIban);

		txtIban = new JTextField();
		txtIban.setBounds(125, 133, 325, 26);
		add(txtIban);

		btnRegistrarEmp = new JButton(ConstantesBotones.REGISTRAR_EMPLEADO);
		btnRegistrarEmp.setBounds(150, 178, 175, 30);
		add(btnRegistrarEmp);

		btnLimpiar = new JButton(ConstantesBotones.LIMPIAR);
		btnLimpiar.setBounds(345, 178, 100, 30);
		add(btnLimpiar);

		
		lblListado = new JLabel("Listado de Empleados:");
		lblListado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblListado.setBounds(35, 291, 220, 20);
		add(lblListado);

		scrpEmpleados = new JScrollPane();
		scrpEmpleados.setBounds(35, 316, 710, 215);
		add(scrpEmpleados);

		tblEmpleados = new JTable();
		tblEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpEmpleados.setViewportView(tblEmpleados);
		configurarTabla();

		btnEliminarEmp = new JButton(ConstantesBotones.ELIMINAR_EMPLEADO);
		btnEliminarEmp.setBounds(535, 543, 175, 30);
		btnEliminarEmp.setEnabled(false);
		add(btnEliminarEmp);
		
		JLabel lblBuscarNombre = new JLabel("Nombre:");
		lblBuscarNombre.setBounds(35, 269, 44, 12);
		add(lblBuscarNombre);
		
		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(89, 263, 118, 18);
		add(txtBuscarNombre);
		txtBuscarNombre.setColumns(10);
		
		btnBuscarNombre = new JButton(ConstantesBotones.BUSCAR_EMPLEADO);
		btnBuscarNombre.setBounds(227, 261, 118, 20);
		add(btnBuscarNombre);
		
		lblAutorizacion = new JLabel("Autorización:");
		lblAutorizacion.setBounds(465, 140, 60, 12);
		add(lblAutorizacion);
		
		spnAutorizacion = new JSpinner();
		spnAutorizacion.setModel(new SpinnerNumberModel(2, 1, 2, 1));
		spnAutorizacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spnAutorizacion.setBounds(535, 132, 44, 35);
		add(spnAutorizacion);

	}

	//corregir: hecho
	//No se debería ver el id
	private void configurarTabla() {
		dtmEmpleados = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblEmpleados.setModel(dtmEmpleados);

		dtmEmpleados.addColumn("Nombre");
		dtmEmpleados.addColumn("Teléfono");
		dtmEmpleados.addColumn("N. Seguridad");
		dtmEmpleados.addColumn("IBAN");

		tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(160);
		tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(90);
		tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(130);
		tblEmpleados.getColumnModel().getColumn(3).setPreferredWidth(220);
	}

	// corregir cargar tabla: hecho
	public void cargarTabla(ArrayList<Empleado> empleados) {
		if(empleados.size() != 0) {
			clearTable();
			Object[] row = new Object[5];
			for(Empleado emp : empleados) {
				row[0] = emp.getNombre();
				row[1] = emp.getTel();
				row[2] = emp.getnSeguridad();
				row[3] = emp.getIban();
				
				dtmEmpleados.addRow(row);
			}
		}else {
			JOptionPane.showMessageDialog(this, "No se han encontrado items con los filtros seleccionados","Mensaje",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void clearTable() {
		int r = dtmEmpleados.getRowCount();
		for(int i = 0; i < r; i++) {
			//System.out.println(i);
			dtmEmpleados.removeRow(0);
		}
	}

	public void setEliminarEnabled(boolean b) {
		btnEliminarEmp.setEnabled(b);
	}
	
	//TODO: corregir lo que retorna tiene que retornar un Empleado y validar datos
	public Empleado obtenerDatosFormulario() {
		String nombre = txtNombre.getText().trim();
		String contra = new String(txtContrasenia.getPassword()).trim();
		String telStr = txtTel.getText().trim();
		String nSeguridad = txtNSeguridad.getText().trim();
		String iban = txtIban.getText().trim();
		int autorizacion = (int)spnAutorizacion.getValue();;
		
		boolean valid = true;
		
		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (contra.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		if (telStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El teléfono es obligatorio.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		try {
			Integer.parseInt(telStr);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El teléfono debe ser un número.", "Error de datos",
					JOptionPane.ERROR_MESSAGE);
			valid = false;
		}

		if(valid) {
			return new Empleado(autorizacion,nombre,contra,Integer.parseInt(telStr), true, nSeguridad, iban);
		}else {
			return null;
		}
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
		btnRegistrarEmp.setActionCommand(ConstantesBotones.REGISTRAR_EMPLEADO);
		
		btnLimpiar.addActionListener(c);
		btnLimpiar.setActionCommand(ConstantesBotones.LIMPIAR);
		
		btnEliminarEmp.addActionListener(c);
		btnEliminarEmp.setActionCommand(ConstantesBotones.ELIMINAR_PRODUCTO);
	}
}
