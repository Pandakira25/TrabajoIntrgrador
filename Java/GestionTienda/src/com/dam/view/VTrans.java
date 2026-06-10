package com.dam.view;



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

import javax.swing.JComboBox;

/**
 * Clase de la interfaz gráfica que representa la vista del histórico de transacciones (`VTrans`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Esta vista proporciona un panel estructurado 
 * para el seguimiento de operaciones comerciales. Cuenta con una tabla principal para listar el 
 * desglose de transacciones (comprador, empleado asistente e importe económico) y un conjunto de widgets 
 * de filtrado dinámico que permiten restringir los resultados por el nombre del comprador o el empleado.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
@SuppressWarnings("serial")
public class VTrans extends JPanel implements IPanels {
	/** Identificador único asignado al panel para la gestión y alternancia en el layout de la aplicación. */
	public static final String NAME = "VTrans";

	/** Anchura neta calculada del panel basada en las dimensiones de la ventana principal y sus insets. */
	private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
	/** Altura neta calculada del panel basada en las dimensiones de la ventana principal, insets y menú. */
	private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

	/** Componente gráfico de tabla para renderizar la colección estructurada de transacciones. */
	private JTable tblTransacciones;
	/** Modelo de datos subyacente para el control estructurado de filas y columnas de la tabla. */
	private DefaultTableModel dtmTransacciones;
	/** Contenedor con barras de desplazamiento para albergar y permitir la navegación sobre la tabla. */
	private JScrollPane scrpTransacciones;
	/** Campo de texto destinado a la introducción manual del nombre o patrón del comprador a buscar. */
	private JTextField txtBuscarNombre;
	/** Botón disparador encargado de iniciar la acción de filtrado o búsqueda de transacciones. */
	private JButton btnBuscar;
	/** Etiqueta informativa adjunta al combo box de selección de empleados. */
	private JLabel lblBuscarEmpleado;
	/** Componente de selección desplegable para filtrar los registros por un empleado específico. */
	private JComboBox<String> cmbBuscarEmpleado;
	/** Modelo de datos dinámico asignado para la gestión del contenido del combo box de empleados. */
	private DefaultComboBoxModel<String> dfcbBuscarEmpleado;

	/**
	 * Constructor por defecto de la clase.
	 * Coordina secuencialmente la definición geométrica del lienzo gráfico y la inicialización de widgets.
	 */
	public VTrans() {
		configurarVentana();
		crearComponentes();
	}
	
	/**
	 * Define las propiedades geométricas y estéticas del panel.
	 * Establece la dimensión neta calculada, el color de fondo persistente y el nombre del componente gráfico.
	 */
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);
		setBackground(VPrincipal.colorPalido);
		setName(NAME);
	}

	/**
	 * Inicializa, posiciona y añade al layout absoluto todos los elementos visuales del panel.
	 * <p>
	 * Se encarga de instanciar las etiquetas informativas, estructurar la zona de filtros, 
	 * acoplar la tabla dentro de su correspondiente scroll pane y precargar el estado base del desplegable.
	 * </p>
	 */
	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblTitulo = new JLabel("Transacciones");
		lblTitulo.setFont(Fuentes.BOLD.deriveFont(20f));
		lblTitulo.setForeground(VPrincipal.colorLetras);
		lblTitulo.setBounds(36, 20, 300, 25);
		add(lblTitulo);

		JLabel lblInfo = new JLabel("Histórico de todas las transacciones realizadas:");
		lblInfo.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblInfo.setForeground(VPrincipal.colorLetras);
		lblInfo.setBounds(36, 85, 380, 20);
		add(lblInfo);

		scrpTransacciones = new JScrollPane();
		scrpTransacciones.setBounds(35, 112, 827, 688);
		scrpTransacciones.getViewport().setBackground(VPrincipal.colorVibrante);
		add(scrpTransacciones);

		tblTransacciones = new JTable();
		tblTransacciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTransacciones.getTableHeader().setFont(Fuentes.MEDIUM.deriveFont(16f));
		tblTransacciones.getTableHeader().setForeground(VPrincipal.colorLetras);
		tblTransacciones.getTableHeader().setBackground(VPrincipal.colorNaranjaPatito);
		tblTransacciones.getTableHeader().setReorderingAllowed(false);
        tblTransacciones.getTableHeader().setResizingAllowed(false);
		tblTransacciones.setFont(Fuentes.REGULAR.deriveFont(14f));
		tblTransacciones.setForeground(VPrincipal.colorLetras);
		scrpTransacciones.setViewportView(tblTransacciones);
		configurarTabla();
		
		JLabel lblBuscarComprador = new JLabel("Comprador:");
		lblBuscarComprador.setBounds(35, 55, 97, 20);
		lblBuscarComprador.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblBuscarComprador.setForeground(VPrincipal.colorLetras);
		add(lblBuscarComprador);

		txtBuscarNombre = new JTextField();
		txtBuscarNombre.setBounds(133, 52, 155, 26);
		txtBuscarNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
		txtBuscarNombre.setForeground(VPrincipal.colorLetras);
		add(txtBuscarNombre);

		btnBuscar = new JButton(ConstantesBotones.BUSCAR_TRANSACCION);
		btnBuscar.setBounds(523, 52, 179, 26);
		btnBuscar.setFont(Fuentes.MEDIUM.deriveFont(16f));
		btnBuscar.setForeground(VPrincipal.colorLetras);
		btnBuscar.setBackground(VPrincipal.colorNaranjaPatito);
		add(btnBuscar);
		
		lblBuscarEmpleado = new JLabel("Empleado:");
		lblBuscarEmpleado.setBounds(298, 55, 89, 20);
		lblBuscarEmpleado.setFont(Fuentes.MEDIUM.deriveFont(16f));
		lblBuscarEmpleado.setForeground(VPrincipal.colorLetras);
		add(lblBuscarEmpleado);
		
		dfcbBuscarEmpleado = new DefaultComboBoxModel<String>();
		dfcbBuscarEmpleado.addElement("Todos");
		cmbBuscarEmpleado = new JComboBox<String>(dfcbBuscarEmpleado);
		cmbBuscarEmpleado.setBounds(383, 52, 112, 26);
		cmbBuscarEmpleado.setFont(Fuentes.REGULAR.deriveFont(16f));
		cmbBuscarEmpleado.setForeground(VPrincipal.colorLetras);
		add(cmbBuscarEmpleado);
	}
	
	/**
	 * Recopila los criterios de filtrado activos introducidos por el usuario en la interfaz gráfica.
	 * <p>
	 * Evalúa el texto del comprador y el elemento seleccionado del combo box, traduciendo los estados por 
	 * defecto o vacíos a referencias {@code null} para facilitar su posterior procesamiento en la base de datos.
	 * </p>
	 * * @return Un array unidimensional de cadenas de texto ({@code String[]}) de tamaño 2, donde `index[0]` 
	 * representa el nombre del comprador y `index[1]` el nombre del empleado. Retorna {@code null} si no 
	 * se ha establecido ningún filtro restrictivo.
	 */
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

	/**
	 * Realiza la configuración técnica y estructural de la tabla de transacciones.
	 * <p>
	 * Inicializa un modelo de celdas no editables, prohíbe el reordenamiento manual de las columnas por 
	 * parte del usuario, inyecta las cabeceras principales e indica el ancho preferido para cada campo.
	 * </p>
	 */
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
	/**
	 * Vuelca una matriz de información de transacciones directamente en el modelo visual de la tabla.
	 * <p>
	 * Limpia las selecciones previas, remueve las filas anteriores e itera sobre la matriz bidimensional 
	 * suministrada para agregar los nuevos registros. Si la matriz está vacía, presenta al usuario 
	 * un cuadro de diálogo informativo emergente indicando la ausencia de coincidencias.
	 * </p>
	 * * @param infoTransaccion Matriz bidimensional de cadenas de texto que contiene los datos a renderizar.
	 */
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

	/**
	 * Remueve la totalidad de las filas que contenga actualmente el modelo de la tabla de forma secuencial.
	 */
	private void clearTable() {
		int r = dtmTransacciones.getRowCount();
		for(int i = 0; i < r; i++) {
			//System.out.println(i);
			dtmTransacciones.removeRow(0);
		}
	}

	/**
	 * Resetea y vacía los vectores de datos del modelo de la tabla, notificando el cambio estructural 
	 * para refrescar la interfaz visual.
	 */
	public void limpiarDatos() {
		dtmTransacciones.getDataVector().clear();
		dtmTransacciones.fireTableDataChanged();
	}
	
	/**
	 * Popula dinámicamente el combo box de búsqueda con un listado con los nombres de los empleados disponibles.
	 * <p>
	 * Limpia los elementos existentes, reestablece la opción comodín "Todos" e inserta de forma secuencial 
	 * cada uno de los elementos presentes en el array de parámetros.
	 * </p>
	 * * @param emps Array unidimensional de cadenas de texto que contiene los nombres de los empleados a cargar.
	 */
	public void chargeEmp(String emps[]) {
		dfcbBuscarEmpleado.removeAllElements();
		dfcbBuscarEmpleado.addElement("Todos");
		for (String emp : emps) {
			dfcbBuscarEmpleado.addElement(emp);
		}
	}

	/**
	 * Vincula el objeto controlador unificado al disparador de eventos del botón de búsqueda.
	 * <p>
	 * Define el escuchador encargado de interceptar la interacción y asocia la marca de comando 
	 * correspondiente para su posterior enrutamiento lógico.
	 * </p>
	 * * @param c Instancia de la clase controladora unificada {@link Ctrl}.
	 */
	@Override
	public void setControlador(Ctrl c) {
		btnBuscar.addActionListener(c);
	    btnBuscar.setActionCommand(ConstantesBotones.BUSCAR_TRANSACCION);
	}
	
	/**
	 * Proporciona acceso directo al componente visual de la tabla de transacciones.
	 * * @return La instancia {@link JTable} empleada para renderizar el histórico.
	 */
	public JTable getTblTransacciones() {
		return tblTransacciones;
	}
}