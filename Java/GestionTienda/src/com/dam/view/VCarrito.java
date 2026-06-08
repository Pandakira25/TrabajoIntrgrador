package com.dam.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Empleado;
import com.dam.model.pojos.Producto;

/**
 * Panel de la vista del Carrito de Compras (`VCarrito`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Esta vista permite a los compradores
 * visualizar los productos que han seleccionado, modificar sus cantidades de forma interactiva, 
 * asociar un empleado que haya asistido la venta y formalizar la transacción mediante el pago.
 * </p>
 * * @author zoe
 * @version 1.0
 */
public class VCarrito extends JPanel implements IPanels {
    /** Identificador único para el gestor de diseño o la navegación de paneles. */
    public static final String NAME = "VCarrito";
    /** Ancho útil calculado en píxeles para el panel según los márgenes de la ventana principal. */
    private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
    /** Alto útil calculado en píxeles para el panel descontando la barra de menús. */
    private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

    /** Listado secuencial de los artículos añadidos actualmente al carrito. */
    private ArrayList<Producto> productosCargados = new ArrayList<>();
    /** Mapa asociativo que vincula cada artículo con el número de unidades solicitadas. */
    private HashMap<Producto, Integer> cantidades = new HashMap<>();
    /** Almacén de objetos Empleado disponibles para la venta. */
    private ArrayList<Empleado> empleadosCargados = new ArrayList<>();

    /** Tabla contenedora para la visualización gráfica de los ítems del carrito. */
    private JTable tblCarrito;
    /** Modelo lógico de datos estructural para el manejo de filas y columnas de la tabla. */
    private DefaultTableModel dtmCarrito;
    /** Panel de soporte con barras de desplazamiento para albergar la tabla de datos. */
    private JScrollPane scrpCarrito;
    /** Botón para proceder a la facturación y procesamiento de la orden de compra. */
    private JButton btnPagar;
    /** Etiqueta que renderiza dinámicamente el precio acumulado de los artículos. */
    private JLabel lblTotal;
    /** Desplegable selector con la nómina de empleados de apoyo. */
    private JComboBox<String> cmbEmpleado;
    /** Componente modelo para la manipulación dinámica de las opciones del desplegable. */
    private DefaultComboBoxModel<String> dcbmEmpleado;

    /**
     * Constructor por defecto del panel.
     * Invoca la parametrización de dimensiones e inicializa la disposición de sus componentes.
     */
    public VCarrito() {
        configurarVentana();
        crearComponentes();
    }

    /**
     * Define las propiedades físicas básicas del contenedor como sus dimensiones, 
     * el fondo paleta corporativo y el nombre de identidad.
     */
    @Override
    public void configurarVentana() {
        setSize(ANCHO, ALTO);
        setBackground(VPrincipal.colorPalido);
        setName(NAME);
    }

    /**
     * Instancia, parametriza estéticamente y posiciona de forma absoluta 
     * todos los componentes gráficos Swing del carrito de compras.
     */
    @Override
    public void crearComponentes() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Tu carrito", SwingConstants.LEFT);
        lblTitulo.setFont(Fuentes.BOLD.deriveFont(20f));
        lblTitulo.setForeground(VPrincipal.colorLetras);
        lblTitulo.setBounds(15, 30, 140, 35);
        add(lblTitulo);

        scrpCarrito = new JScrollPane();
        scrpCarrito.setBounds(15, 75, 827, 688);
        scrpCarrito.getViewport().setBackground(VPrincipal.colorVibrante);
        add(scrpCarrito);

        tblCarrito = new JTable();
        tblCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCarrito.getTableHeader().setBackground(VPrincipal.colorNaranjaPatito);
        tblCarrito.getTableHeader().setForeground(VPrincipal.colorLetras);
        tblCarrito.getTableHeader().setFont(Fuentes.MEDIUM.deriveFont(16f));
        tblCarrito.setFont(Fuentes.REGULAR.deriveFont(14f));
        tblCarrito.setForeground(VPrincipal.colorLetras);
        tblCarrito.setRowHeight(28);
        scrpCarrito.setViewportView(tblCarrito);
        configurarTabla();

        lblTotal = new JLabel("Total: 0,00 €");
        lblTotal.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblTitulo.setForeground(VPrincipal.colorLetras);
        lblTotal.setBounds(564, 779, 148, 30);
        add(lblTotal);

        JLabel lblEmpleado = new JLabel("¿Te ayudó un empleado?");
        lblEmpleado.setFont(Fuentes.MEDIUM.deriveFont(16f));
        lblEmpleado.setForeground(VPrincipal.colorLetras);
        lblEmpleado.setBounds(15, 773, 190, 30);
        add(lblEmpleado);

        dcbmEmpleado = new DefaultComboBoxModel<>();
        dcbmEmpleado.addElement("No");
        cmbEmpleado = new JComboBox<>(dcbmEmpleado);
        cmbEmpleado.setBounds(215, 777, 160, 26);
        cmbEmpleado.setBackground(VPrincipal.colorVibrante);
        cmbEmpleado.setForeground(VPrincipal.colorLetras);
        cmbEmpleado.setFont(Fuentes.MEDIUM.deriveFont(16f));
        add(cmbEmpleado);

        btnPagar = new JButton(ConstantesBotones.PAGAR);
        btnPagar.setBackground(VPrincipal.colorNaranjaPatito);
        btnPagar.setFont(Fuentes.MEDIUM.deriveFont(16f));
        btnPagar.setForeground(VPrincipal.colorLetras);
        btnPagar.setBounds(722, 779, 120, 30);
        add(btnPagar);
    }

    /**
     * Inicializa las columnas lógicas del {@link DefaultTableModel}, deshabilita el
     * reordenamiento de cabeceras por arrastre y define el dimensionamiento de las celdas.
     */
    private void configurarTabla() {
        dtmCarrito = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCarrito.getTableHeader().setReorderingAllowed(false);
        
        tblCarrito.setModel(dtmCarrito);

        dtmCarrito.addColumn("Nombre");
        dtmCarrito.addColumn("Precio (€)");
        dtmCarrito.addColumn("Cantidad");
        dtmCarrito.addColumn("+");
        dtmCarrito.addColumn("-");
        dtmCarrito.addColumn("Eliminar");

        tblCarrito.getColumnModel().getColumn(0).setPreferredWidth(370);
        tblCarrito.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblCarrito.getColumnModel().getColumn(2).setPreferredWidth(70);
        tblCarrito.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblCarrito.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblCarrito.getColumnModel().getColumn(5).setPreferredWidth(80);
    }

    /**
     * Introduce un producto al carrito de compras. Si el producto ya figuraba, 
     * incrementa en una unidad su contador parcial. Redibuja la cuadrícula al finalizar.
     * * @param p El objeto {@link Producto} que se desea anexar al carrito.
     */
    public void agregarProducto(Producto p) {
        if (cantidades.containsKey(p)) {
            cantidades.put(p, cantidades.get(p) + 1);
        } else {
            productosCargados.add(p);
            cantidades.put(p, 1);
        }
        refrescarTabla();
    }

    /**
     * Incrementa en una unidad la cantidad del producto ubicado en la fila indicada,
     * siempre y cuando no exceda los límites de existencias (stock) del artículo.
     * * @param fila Índice numérico de la fila seleccionada dentro de la JTable.
     * @return {@code true} si se logró sumar con éxito; {@code false} si se alcanzó el límite de stock.
     */
    public boolean sumarCantidad(int fila) {
        Producto p = productosCargados.get(fila);
        int cant = cantidades.get(p);
        if (cant < p.getStock()) {
            cantidades.put(p, cant + 1);
            refrescarTabla();
            return true;
        }
        return false;
    }

    /**
     * Sustrae una unidad de la cantidad demandada del producto de la fila indicada.
     * Si el contador disminuye por debajo de 1, el elemento es purgado automáticamente del carrito.
     * * @param fila Índice numérico de la fila de la tabla a modificar.
     */
    public void restarCantidad(int fila) {
        Producto p = productosCargados.get(fila);
        int cant = cantidades.get(p);
        if (cant <= 1) {
            eliminarProducto(fila);
        } else {
            cantidades.put(p, cant - 1);
            refrescarTabla();
        }
    }

    /**
     * Suprime de forma directa el registro de un producto del mapa asociativo
     * y del listado secuencial de artículos cargados dada su ubicación en el índice.
     * * @param fila Posición entera del registro en la estructura visual de filas.
     */
    public void eliminarProducto(int fila) {
        Producto p = productosCargados.get(fila);
        cantidades.remove(p);
        productosCargados.remove(fila);
        refrescarTabla();
    }

    /**
     * Limpia en su totalidad las filas del componente visual, itera la colección de
     * productos activos para reinsertar las filas actualizadas y totaliza el coste de la compra.
     */
    private void refrescarTabla() {
        int r = dtmCarrito.getRowCount();
        for (int i = 0; i < r; i++) {
            dtmCarrito.removeRow(0);
        }
        double total = 0;
        Object[] row = new Object[6];
        for (Producto prod : productosCargados) {
            row[0] = prod.getNombre();
            row[1] = prod.getPrecio();
            row[2] = cantidades.get(prod);
            row[3] = "+";
            row[4] = "-";
            row[5] = "Eliminar";
            dtmCarrito.addRow(row);
            total += prod.getPrecio() * cantidades.get(prod);
        }
        lblTotal.setText(String.format("Total: %.2f €", total));
    }

    /**
     * Vacía por completo las colecciones del carrito, restituye el selector de empleados
     * a la opción por defecto ("No") y vuelve a generar el estado visual del panel.
     */
    public void limpiarCarrito() {
        productosCargados.clear();
        cantidades.clear();
        cmbEmpleado.setSelectedIndex(0);
        refrescarTabla();
    }

    /**
     * Sincroniza y rellena el cuadro desplegable de empleados con los nombres de pila 
     * de los trabajadores provistos desde la base de datos.
     * * @param empleados Estructura {@link ArrayList} que contiene las instancias del personal operativo.
     */
    public void cargarEmpleados(ArrayList<Empleado> empleados) {
        empleadosCargados = empleados;
        dcbmEmpleado.removeAllElements();
        dcbmEmpleado.addElement("No");
        for (Empleado emp : empleados) {
            dcbmEmpleado.addElement(emp.getNombre());
        }
    }

    /**
     * Recupera la clave única (Id) del empleado asociado seleccionado en la vista.
     * * @return Un valor de tipo {@link Integer} con el ID del empleado seleccionado; 
     * {@code null} si se ha seleccionado la opción "No".
     */
    public Integer getEmpleadoSeleccionado() {
        int idx = cmbEmpleado.getSelectedIndex();
        if (idx == 0) return null;
        return empleadosCargados.get(idx - 1).getUserId();
    }

    /**
     * Devuelve el objeto del artículo localizado en el índice de fila referenciado.
     * * @param fila Índice numérico de la consulta en la tabla.
     * @return El objeto {@link Producto} apuntado por esa posición.
     */
    public Producto getProductoEnFila(int fila) {
        return productosCargados.get(fila);
    }

    /**
     * Extrae el recuento de unidades pedidas correspondientes a una fila específica.
     * * @param fila Posición ordinal del ítem en la tabla.
     * @return Valor entero representativo del volumen ordenado de dicho artículo.
     */
    public int getCantidadEnFila(int fila) {
        return cantidades.get(productosCargados.get(fila));
    }

    /**
     * Proporciona la estructura completa con el mapeo de productos y sus cantidades acumuladas.
     * * @return Un {@link HashMap} cuyas claves son los {@link Producto} y sus valores las unidades correspondientes.
     */
    public HashMap<Producto, Integer> getCantidades() {
        return cantidades;
    }

    /**
     * Proporciona la referencia del componente de la tabla del carrito.
     * * @return El componente objeto {@link JTable} interno de la vista.
     */
    public JTable getTblCarrito() {
        return tblCarrito;
    }

    /**
     * Enlaza el controlador del patrón MVC a la interfaz del carrito, 
     * suscribiendo sus botones a la escucha de eventos de acción y clics de ratón.
     * * @param c Instancia de la clase controladora principal {@link Ctrl}.
     */
    @Override
    public void setControlador(Ctrl c) {
        btnPagar.addActionListener(c);
        btnPagar.setActionCommand(ConstantesBotones.PAGAR);
        tblCarrito.addMouseListener(c);
    }
}