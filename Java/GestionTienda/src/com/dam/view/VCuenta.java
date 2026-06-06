package com.dam.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dam.ctrl.Ctrl;
import com.dam.model.pojos.Comprador;

public class VCuenta extends JPanel implements IPanels {
    public static final String NAME = "VCuenta";

    private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
    private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

    private JTextField txtNombre;
    private JPasswordField txtContrasenia;
    private JTextField txtTel;
    private JTextField txtDireccion;
    private JTextField txtNTarjeta;
    private JButton btnModificar;
    private JButton btnDarseDeBaja;

    public VCuenta() {
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

        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(15, 16, 300, 25);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(35, 60, 80, 20);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(125, 57, 200, 26);
        add(txtNombre);

        JLabel lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setBounds(35, 98, 85, 20);
        add(lblContrasenia);

        txtContrasenia = new JPasswordField();
        txtContrasenia.setBounds(125, 95, 200, 26);
        add(txtContrasenia);

        JLabel lblTel = new JLabel("Teléfono:");
        lblTel.setBounds(35, 136, 80, 20);
        add(lblTel);

        txtTel = new JTextField();
        txtTel.setBounds(125, 133, 200, 26);
        add(txtTel);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(35, 174, 80, 20);
        add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(125, 171, 200, 26);
        add(txtDireccion);

        JLabel lblNTarjeta = new JLabel("N. Tarjeta:");
        lblNTarjeta.setBounds(35, 212, 80, 20);
        add(lblNTarjeta);

        txtNTarjeta = new JTextField();
        txtNTarjeta.setBounds(125, 209, 200, 26);
        add(txtNTarjeta);

        btnModificar = new JButton(ConstantesBotones.MODIFICAR_COMPRADOR);
        btnModificar.setBounds(125, 255, 175, 30);
        add(btnModificar);

        btnDarseDeBaja = new JButton(ConstantesBotones.DARSE_DE_BAJA);
        btnDarseDeBaja.setBounds(125, 300, 175, 30);
        add(btnDarseDeBaja);
    }

    public void cargarDatos(Comprador c) {
        txtNombre.setText(c.getNombre());
        txtContrasenia.setText(c.getContrasenia());
        txtTel.setText(String.valueOf(c.getTel()));
        txtDireccion.setText(c.getDireccion());
        txtNTarjeta.setText(c.getnTarjeta());
    }

    public Comprador obtenerDatosFormulario(int id) {
        String nombre = txtNombre.getText().trim();
        String contra = new String(txtContrasenia.getPassword()).trim();
        String telStr = txtTel.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String nTarjeta = txtNTarjeta.getText().trim();

        boolean valid = true;

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de datos",
                    JOptionPane.ERROR_MESSAGE);
            valid = false;
        } else if (contra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La contraseña es obligatoria.", "Error de datos",
                    JOptionPane.ERROR_MESSAGE);
            valid = false;
        } else if (telStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El teléfono es obligatorio.", "Error de datos",
                    JOptionPane.ERROR_MESSAGE);
            valid = false;
        } else {
            try {
                Integer.parseInt(telStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El teléfono debe ser un número.", "Error de datos",
                        JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
        }

        if (valid) {
            return new Comprador(id, 3, nombre, contra, Integer.parseInt(telStr), true, direccion, nTarjeta);
        }
        return null;
    }

    @Override
    public void setControlador(Ctrl c) {
        btnModificar.addActionListener(c);
        btnModificar.setActionCommand(ConstantesBotones.MODIFICAR_COMPRADOR);

        btnDarseDeBaja.addActionListener(c);
        btnDarseDeBaja.setActionCommand(ConstantesBotones.DARSE_DE_BAJA);
    }
}