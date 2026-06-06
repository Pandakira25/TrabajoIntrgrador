package com.dam.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Usuario;

public class VGestionUsuarios extends JPanel implements IPanels {
    public static final String NAME = "VGestionUsuarios";

    private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
    private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

    private JTable tblUsuarios;
    private DefaultTableModel dtmUsuarios;
    private JScrollPane scrpUsuarios;
    private JButton btnHabilitar;
    private JButton btnDeshabilitar;

    private ArrayList<Usuario> usuariosCargados = new ArrayList<>();

    public VGestionUsuarios() {
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

        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(15, 16, 300, 25);
        add(lblTitulo);

        scrpUsuarios = new JScrollPane();
        scrpUsuarios.setBounds(35, 55, 710, 430);
        add(scrpUsuarios);

        tblUsuarios = new JTable();
        tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrpUsuarios.setViewportView(tblUsuarios);
        configurarTabla();

        btnHabilitar = new JButton(ConstantesBotones.HABILITAR_USUARIO);
        btnHabilitar.setBounds(35, 500, 175, 30);
        btnHabilitar.setEnabled(false);
        add(btnHabilitar);

        btnDeshabilitar = new JButton(ConstantesBotones.DESHABILITAR_USUARIO);
        btnDeshabilitar.setBounds(570, 500, 175, 30);
        btnDeshabilitar.setEnabled(false);
        add(btnDeshabilitar);
    }

    private void configurarTabla() {
        dtmUsuarios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios.setModel(dtmUsuarios);

        dtmUsuarios.addColumn("Nombre");
        dtmUsuarios.addColumn("Rol");
        dtmUsuarios.addColumn("Estado");

        tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(300);
        tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(150);

        tblUsuarios.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("Activo".equals(value)) {
                    c.setBackground(new Color(144, 238, 144));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(new Color(255, 99, 99));
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
    }

    public void cargarTabla(ArrayList<Usuario> usuarios) {
        tblUsuarios.clearSelection();
        dtmUsuarios.getDataVector().clear();
        usuariosCargados = usuarios;
        btnHabilitar.setEnabled(false);
        btnDeshabilitar.setEnabled(false);

        if (usuarios.size() != 0) {
            clearTable();
            Object[] row = new Object[3];
            for (Usuario u : usuarios) {
                row[0] = u.getNombre();
                row[1] = getRol(u.getAutorizacion());
                row[2] = u.isActivo() ? "Activo" : "Desactivado";
                dtmUsuarios.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados.", "Mensaje",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getRol(int autorizacion) {
        switch (autorizacion) {
            case 1: return "Administrador";
            case 2: return "Empleado";
            case 3: return "Comprador";
            default: return "Desconocido";
        }
    }

    private void clearTable() {
        int r = dtmUsuarios.getRowCount();
        for (int i = 0; i < r; i++) {
            dtmUsuarios.removeRow(0);
        }
    }

    public void setHabilitarEnabled(boolean b) {
        btnHabilitar.setEnabled(b);
    }

    public void setDeshabilitarEnabled(boolean b) {
        btnDeshabilitar.setEnabled(b);
    }

    public Usuario getUsuarioEnFila(int fila) {
        return usuariosCargados.get(fila);
    }

    public ListSelectionModel getSelectionModel() {
        return tblUsuarios.getSelectionModel();
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    @Override
    public void setControlador(Ctrl c) {
        btnHabilitar.addActionListener(c);
        btnHabilitar.setActionCommand(ConstantesBotones.HABILITAR_USUARIO);

        btnDeshabilitar.addActionListener(c);
        btnDeshabilitar.setActionCommand(ConstantesBotones.DESHABILITAR_USUARIO);

        tblUsuarios.getSelectionModel().addListSelectionListener(c);
    }
}