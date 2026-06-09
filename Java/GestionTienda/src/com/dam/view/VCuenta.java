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
import com.dam.model.pojos.Usuario;

/**
 * Panel de la vista del Perfil de Usuario (`VCuenta`).
 * <p>
 * Hereda de {@link JPanel} e implementa la interfaz {@link IPanels}. 
 * Proporciona una interfaz gráfica estructurada para que el comprador autenticado 
 * pueda visualizar sus datos personales, realizar modificaciones en su perfil con 
 * validación integrada, o solicitar la baja del sistema.
 * </p>
 * * @author zoe
 * @version 1.0
 */
public class VCuenta extends JPanel implements IPanels {
    /** Identificador único utilizado para el diseño, depuración o la gestión de navegación. */
    public static final String NAME = "VCuenta";

    /** Ancho útil en píxeles calculado para este panel, considerando los márgenes de la pantalla principal. */
    private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
    /** Alto útil en píxeles calculado para este panel, deduciendo márgenes y la barra de menús superior. */
    private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

    /** Campo de texto destinado a la visualización y edición del nombre de usuario. */
    private JTextField txtNombre;
    /** Campo enmascarado destinado a la edición segura de la contraseña. */
    private JPasswordField txtContrasenia;
    /** Campo de texto destinado a capturar el número telefónico de contacto. */
    private JTextField txtTel;
    /** Campo de texto destinado a la dirección de correspondencia o envío. */
    private JTextField txtDireccion;
    /** Campo de texto destinado a almacenar el número de tarjeta bancaria del cliente. */
    private JTextField txtNTarjeta;
    /** Botón encargado de desencadenar la actualización del registro de datos. */
    private JButton btnModificar;
    /** Botón encargado de procesar e iniciar la baja lógica de la cuenta del usuario. */
    private JButton btnDarseDeBaja;

    /**
     * Constructor por defecto del panel de cuenta.
     * Coordina el dimensionamiento geométrico de la ventana y el ensamblaje de sus elementos visuales.
     */
    public VCuenta() {
        configurarVentana();
        crearComponentes();
    }

    /**
     * Define las dimensiones espaciales absolutas del contenedor, 
     * asigna el color corporativo pálido al fondo y establece el nombre clave de la vista.
     */
    @Override
    public void configurarVentana() {
        setSize(ANCHO, ALTO);
        setBackground(VPrincipal.colorPalido);
        setName(NAME);
    }

    /**
     * Inicializa, personaliza con estilos y tipografías específicos, y sitúa de forma
     * absoluta mediante coordenadas todos los elementos gráficos Swing que constituyen el formulario.
     */
    @Override
    public void crearComponentes() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(Fuentes.BOLD.deriveFont(24f));
        lblTitulo.setForeground(VPrincipal.colorLetras);
        lblTitulo.setBounds(35, 10, 145, 25);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblNombre.setForeground(VPrincipal.colorLetras);
        lblNombre.setBounds(35, 60, 80, 20);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(165, 57, 200, 26);
        txtNombre.setForeground(VPrincipal.colorLetras);
        txtNombre.setFont(Fuentes.REGULAR.deriveFont(16f));
        add(txtNombre);

        JLabel lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setBounds(35, 101, 118, 20);
        lblContrasenia.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblContrasenia.setForeground(VPrincipal.colorLetras);
        add(lblContrasenia);

        txtContrasenia = new JPasswordField();
        txtContrasenia.setBounds(165, 100, 200, 26);
        txtContrasenia.setForeground(VPrincipal.colorLetras);
        txtContrasenia.setFont(Fuentes.REGULAR.deriveFont(16f));
        add(txtContrasenia);

        JLabel lblTel = new JLabel("Teléfono:");
        lblTel.setBounds(35, 143, 105, 20);
        lblTel.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblTel.setForeground(VPrincipal.colorLetras);
        add(lblTel);

        txtTel = new JTextField();
        txtTel.setBounds(165, 141, 200, 26);
        txtTel.setForeground(VPrincipal.colorLetras);
        txtTel.setFont(Fuentes.REGULAR.deriveFont(16f));
        add(txtTel);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(35, 185, 105, 20);
        lblDireccion.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblDireccion.setForeground(VPrincipal.colorLetras);
        add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(165, 183, 200, 26);
        txtDireccion.setFont(Fuentes.REGULAR.deriveFont(16f));
        add(txtDireccion);

        JLabel lblNTarjeta = new JLabel("N. Tarjeta:");
        lblNTarjeta.setBounds(35, 227, 105, 20);
        lblNTarjeta.setFont(Fuentes.MEDIUM.deriveFont(20f));
        lblNTarjeta.setForeground(VPrincipal.colorLetras);
        add(lblNTarjeta);

        txtNTarjeta = new JTextField();
        txtNTarjeta.setBounds(165, 225, 200, 26);
        txtNTarjeta.setFont(Fuentes.REGULAR.deriveFont(16f));
        txtNTarjeta.setForeground(VPrincipal.colorLetras);
        add(txtNTarjeta);

        btnModificar = new JButton(ConstantesBotones.MODIFICAR_COMPRADOR);
        btnModificar.setBounds(228, 279, 175, 30);
        btnModificar.setFont(Fuentes.MEDIUM.deriveFont(16f));
        btnModificar.setForeground(VPrincipal.colorLetras);
        btnModificar.setBackground(VPrincipal.colorNaranjaPatito);
        add(btnModificar);

        btnDarseDeBaja = new JButton(ConstantesBotones.DARSE_DE_BAJA);
        btnDarseDeBaja.setBounds(35, 279, 175, 30);
        btnDarseDeBaja.setFont(Fuentes.MEDIUM.deriveFont(16f));
        btnDarseDeBaja.setForeground(VPrincipal.colorLetras);
        btnDarseDeBaja.setBackground(VPrincipal.colorNaranjaPatito);
        add(btnDarseDeBaja);
    }

    /**
     * Vuelca la información de una entidad comprador en los campos de texto correspondientes
     * de la interfaz gráfica para su visualización.
     * * @param c El objeto {@link Comprador} origen de los datos a representar en el perfil.
     */
    public void cargarDatos(Comprador c) {
        txtNombre.setText(c.getNombre());
        txtContrasenia.setText(c.getContrasenia());
        txtTel.setText(String.valueOf(c.getTel()));
        txtDireccion.setText(c.getDireccion());
        txtNTarjeta.setText(c.getnTarjeta());
    }

    /**
     * Extrae, sanea de espacios colindantes y valida sintácticamente la información digitada en el formulario.
     * <p>
     * Evalúa la presencia obligatoria de campos y comprueba que la cadena telefónica sea numéricamente
     * válida. En caso de inconsistencias, despliega advertencias dinámicas por pantalla.
     * </p>
     * * @param id Identificador único y correlativo (User ID) del comprador en sesión.
     * @return Una nueva instancia estructurada de {@link Comprador} provista con las variables validadas; 
     * o {@code null} si se incumple alguna regla de validación de datos.
     */
    public Comprador obtenerDatosFormulario(int id) {
        try {
            return new Comprador(
                    id,
                    txtNombre.getText(),
                    new String(txtContrasenia.getPassword()),
                    txtTel.getText(),
                    txtDireccion.getText(),
                    txtNTarjeta.getText());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de datos", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Acopla los botones interactivos de este panel a la lógica centralizada del patrón MVC,
     * asignándoles sus respectivos comandos de acción estándar.
     * * @param c El objeto controlador centralizado de la aplicación ({@link Ctrl}).
     */
    @Override
    public void setControlador(Ctrl c) {
        btnModificar.addActionListener(c);
        btnModificar.setActionCommand(ConstantesBotones.MODIFICAR_COMPRADOR);

        btnDarseDeBaja.addActionListener(c);
        btnDarseDeBaja.setActionCommand(ConstantesBotones.DARSE_DE_BAJA);
    }
}