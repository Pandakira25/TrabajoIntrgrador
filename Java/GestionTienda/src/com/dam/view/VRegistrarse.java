package com.dam.view;

import com.dam.ctrl.Ctrl;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VRegistrarse extends JPanel implements IPanels {

    private JLabel lblTitulo;
    private Ctrl ctrl;

    public VRegistrarse() {
        absoluteLayout();
        crearComponentes();
        configurarVentana();
    }

    private void absoluteLayout() {
        setLayout(null);
    }

    @Override
    public void crearComponentes() {
        lblTitulo = new JLabel("Registrarse");
        lblTitulo.setBounds(110, 15, 180, 28);
        add(lblTitulo);
    }

    @Override
    public void configurarVentana() {
        setPreferredSize(new Dimension(400, 250));
    }

    @Override
    public void setControlador(Ctrl consultaResController) {
        ctrl = consultaResController;
    }
}
