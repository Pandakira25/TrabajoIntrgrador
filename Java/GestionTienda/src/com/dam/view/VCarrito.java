package com.dam.view;

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

public class VCarrito extends JPanel implements IPanels {
    public static final String NAME = "VCarrito";
    private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
    private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

    private ArrayList<Producto> productosCargados = new ArrayList<>();
    private HashMap<Producto, Integer> cantidades = new HashMap<>();
    private ArrayList<Empleado> empleadosCargados = new ArrayList<>();

    private JTable tblCarrito;
    private DefaultTableModel dtmCarrito;
    private JScrollPane scrpCarrito;
    private JButton btnPagar;
    private JLabel lblTotal;
    private JComboBox<String> cmbEmpleado;
    private DefaultComboBoxModel<String> dcbmEmpleado;

    public VCarrito() {
        configurarVentana();
        crearComponentes();
    }

    @Override
    public void configurarVentana() {
        setSize(ANCHO, ALTO);
        setPreferredSize(new Dimension(ANCHO, 590));
        setName(NAME);
    }

    @Override
    public void crearComponentes() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Tu carrito", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(0, 20, ANCHO, 35);
        add(lblTitulo);

        scrpCarrito = new JScrollPane();
        scrpCarrito.setBounds(15, 75, 760, 420);
        add(scrpCarrito);

        tblCarrito = new JTable();
        tblCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrpCarrito.setViewportView(tblCarrito);
        configurarTabla();

        lblTotal = new JLabel("Total: 0,00 €");
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotal.setBounds(15, 505, 200, 30);
        add(lblTotal);

        JLabel lblEmpleado = new JLabel("¿Te ayudó un empleado?");
        lblEmpleado.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEmpleado.setBounds(230, 505, 180, 30);
        add(lblEmpleado);

        dcbmEmpleado = new DefaultComboBoxModel<>();
        dcbmEmpleado.addElement("No");
        cmbEmpleado = new JComboBox<>(dcbmEmpleado);
        cmbEmpleado.setBounds(415, 508, 200, 26);
        add(cmbEmpleado);

        btnPagar = new JButton(ConstantesBotones.PAGAR);
        btnPagar.setBounds((ANCHO - 120) / 2, 545, 120, 30);
        add(btnPagar);
    }

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

    public void agregarProducto(Producto p) {
        if (cantidades.containsKey(p)) {
            cantidades.put(p, cantidades.get(p) + 1);
        } else {
            productosCargados.add(p);
            cantidades.put(p, 1);
        }
        refrescarTabla();
    }

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

    public void eliminarProducto(int fila) {
        Producto p = productosCargados.get(fila);
        cantidades.remove(p);
        productosCargados.remove(fila);
        refrescarTabla();
    }

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

    public void limpiarCarrito() {
        productosCargados.clear();
        cantidades.clear();
        cmbEmpleado.setSelectedIndex(0);
        refrescarTabla();
    }

    public void cargarEmpleados(ArrayList<Empleado> empleados) {
        empleadosCargados = empleados;
        dcbmEmpleado.removeAllElements();
        dcbmEmpleado.addElement("No");
        for (Empleado emp : empleados) {
            dcbmEmpleado.addElement(emp.getNombre());
        }
    }

    public Integer getEmpleadoSeleccionado() {
        int idx = cmbEmpleado.getSelectedIndex();
        if (idx == 0) return null;
        return empleadosCargados.get(idx - 1).getUserId();
    }

    public Producto getProductoEnFila(int fila) {
        return productosCargados.get(fila);
    }

    public int getCantidadEnFila(int fila) {
        return cantidades.get(productosCargados.get(fila));
    }

    public HashMap<Producto, Integer> getCantidades() {
        return cantidades;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    @Override
    public void setControlador(Ctrl c) {
        btnPagar.addActionListener(c);
        btnPagar.setActionCommand(ConstantesBotones.PAGAR);
        tblCarrito.addMouseListener(c);
    }
}