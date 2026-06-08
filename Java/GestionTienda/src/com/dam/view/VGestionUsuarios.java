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

/**
 * Panel de interfaz gráfica destinado a la gestión visual de usuarios (`VGestionUsuarios`).
 * <p>
 * Hereda de {@link JPanel} e implementa {@link IPanels}. Proporciona un entorno visual estructurado 
 * en una tabla para visualizar el listado de usuarios del sistema, mostrando su nombre, rol y estado. 
 * Dispone de botones interactivos para habilitar o deshabilitar las cuentas seleccionadas, aplicando 
 * renderizadores de color personalizados para distinguir los estados a simple vista.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class VGestionUsuarios extends JPanel implements IPanels {
    
    /** Identificador único asignado al panel para la gestión y alternancia en el layout de la aplicación. */
    public static final String NAME = "VGestionUsuarios";

    /** Anchura neta calculada del componente basada en las dimensiones de la ventana principal y sus insets. */
    private static final int ANCHO = VPrincipal.ANCHO - VPrincipal.insetsL - VPrincipal.insetsR;
    /** Altura neta calculada del componente basada en las dimensiones de la ventana principal, insets y menú. */
    private static final int ALTO = VPrincipal.ALTO - VPrincipal.insetsT - VPrincipal.insetsB - VPrincipal.menuH;

    /** Componente gráfico de tabla para renderizar la colección estructurada de usuarios. */
    private JTable tblUsuarios;
    /** Modelo de datos subyacente para el control estructurado de filas y columnas de la tabla. */
    private DefaultTableModel dtmUsuarios;
    /** Contenedor con barras de desplazamiento para albergar y permitir la navegación sobre la tabla. */
    private JScrollPane scrpUsuarios;
    /** Botón disparador encargado de enviar la señal para habilitar un usuario seleccionado. */
    private JButton btnHabilitar;
    /** Botón disparador encargado de enviar la señal para deshabilitar un usuario seleccionado. */
    private JButton btnDeshabilitar;

    /** Colección indexada local de objetos {@link Usuario} que se corresponden biunívocamente con las filas vigentes de la tabla. */
    private ArrayList<Usuario> usuariosCargados = new ArrayList<>();

    /**
     * Constructor por defecto de la clase.
     * Coordina secuencialmente la definición geométrica del lienzo y la inicialización de widgets.
     */
    public VGestionUsuarios() {
        configurarVentana();
        crearComponentes();
    }

    /**
     * Define los parámetros geométricos del panel contenedor.
     * Establece el tamaño del lienzo de dibujo, el color base de fondo y el nombre del componente gráfico.
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
     * Se encarga de instanciar las etiquetas, configurar el scroll de la tabla con un modelo
     * de selección de fila único y establecer los botones funcionales con sus estados por defecto.
     * </p>
     */
    @Override
    public void crearComponentes() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setFont(Fuentes.BOLD.deriveFont(20f));
        lblTitulo.setForeground(VPrincipal.colorLetras);
        lblTitulo.setBounds(15, 16, 300, 25);
        add(lblTitulo);

        scrpUsuarios = new JScrollPane();
        scrpUsuarios.setBounds(15, 56, 827, 688);
        scrpUsuarios.getViewport().setBackground(VPrincipal.colorVibrante);
        add(scrpUsuarios);

        tblUsuarios = new JTable();
        tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblUsuarios.setFont(Fuentes.REGULAR.deriveFont(14f));
        tblUsuarios.setForeground(VPrincipal.colorLetras);
        tblUsuarios.getTableHeader().setBackground(VPrincipal.colorNaranjaPatito);
        tblUsuarios.getTableHeader().setFont(Fuentes.MEDIUM.deriveFont(16f));
        tblUsuarios.getTableHeader().setForeground(VPrincipal.colorLetras);
        scrpUsuarios.setViewportView(tblUsuarios);
        configurarTabla();

        btnHabilitar = new JButton(ConstantesBotones.HABILITAR_USUARIO);
        btnHabilitar.setBounds(15, 770, 175, 30);
        btnHabilitar.setBackground(VPrincipal.colorNaranjaPatito);
        btnHabilitar.setForeground(VPrincipal.colorLetras);
        btnHabilitar.setFont(Fuentes.MEDIUM.deriveFont(16f));
        btnHabilitar.setEnabled(false);
        add(btnHabilitar);

        btnDeshabilitar = new JButton(ConstantesBotones.DESHABILITAR_USUARIO);
        btnDeshabilitar.setBounds(212, 770, 193, 30);
        btnDeshabilitar.setFont(Fuentes.MEDIUM.deriveFont(16f));
        btnDeshabilitar.setForeground(VPrincipal.colorLetras);
        btnDeshabilitar.setBackground(VPrincipal.colorNaranjaPatito);
        btnDeshabilitar.setEnabled(false);
        add(btnDeshabilitar);
    }

    /**
     * Realiza los ajustes de inicialización estructural de la tabla de usuarios.
     * <p>
     * Define un modelo de datos no editable, añade las tres columnas principales ("Nombre", "Rol", "Estado"), 
     * asigna anchos relativos preferidos e inyecta un renderizador condicional para pintar la columna de 
     * estado de verde (Activo) o rojo (Desactivado).
     * </p>
     */
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

    /**
     * Vuelca la colección dinámica de datos en el modelo visual de la tabla.
     * <p>
     * Resetea el foco de selección, limpia por completo las filas previas, sincroniza la referencia 
     * local de la lista y dibuja los nuevos registros traduciendo los metadatos numéricos a texto entendible. 
     * Si el listado se encuentra vacío, despliega un cuadro de diálogo informativo emergente.
     * </p>
     * * @param usuarios Colección dinámicamente estructurada de objetos {@link Usuario} para mostrar.
     */
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

    /**
     * Mapea el código numérico de autorización al literal del rol de usuario correspondiente.
     * * @param autorizacion Código numérico discreto indicativo del privilegio o nivel de acceso.
     * @return Una cadena textual representativa del rol ("Administrador", "Empleado", "Comprador" o "Desconocido").
     */
    private String getRol(int autorizacion) {
        switch (autorizacion) {
            case 1: return "Administrador";
            case 2: return "Empleado";
            case 3: return "Comprador";
            default: return "Desconocido";
        }
    }

    /**
     * Remueve la totalidad de las filas que contenga actualmente el modelo de la tabla de manera secuencial.
     */
    private void clearTable() {
        int r = dtmUsuarios.getRowCount();
        for (int i = 0; i < r; i++) {
            dtmUsuarios.removeRow(0);
        }
    }

    /**
     * Configura el estado de activación (habilitado/deshabilitado) del componente del botón Habilitar.
     * * @param b {@code true} para habilitar la interacción del widget; {@code false} para congelarlo.
     */
    public void setHabilitarEnabled(boolean b) {
        btnHabilitar.setEnabled(b);
    }

    /**
     * Configura el estado de activación (habilitado/deshabilitado) del componente del botón Deshabilitar.
     * * @param b {@code true} para habilitar la interacción del widget; {@code false} para congelarlo.
     */
    public void setDeshabilitarEnabled(boolean b) {
        btnDeshabilitar.setEnabled(b);
    }

    /**
     * Devuelve el objeto del dominio situado exactamente en la posición de la fila indicada.
     * * @param fila Índice numérico posicional de la fila en la vista de la tabla.
     * @return La instancia {@link Usuario} correspondiente en esa misma posición indexada.
     */
    public Usuario getUsuarioEnFila(int fila) {
        return usuariosCargados.get(fila);
    }

    /**
     * Proporciona acceso directo al gestor o modelo que coordina las selecciones en la tabla de la interfaz.
     * * @return La referencia al {@link ListSelectionModel} enlazado a la JTable de usuarios.
     */
    public ListSelectionModel getSelectionModel() {
        return tblUsuarios.getSelectionModel();
    }

    /**
     * Proporciona acceso al componente visual estructurado de la tabla.
     * * @return La instancia {@link JTable} empleada para el renderizado del listado.
     */
    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    /**
     * Enlaza el componente controlador principal a los disparadores de eventos de la vista.
     * <p>
     * Registra las acciones del ratón (clicks) en los botones mediante marcas de comandos unificadas 
     * y añade el escuchador a los cambios de fila seleccionada de la tabla para controlar su reactividad.
     * </p>
     * * @param c Instancia de la clase controladora unificada {@link Ctrl}.
     */
    @Override
    public void setControlador(Ctrl c) {
        btnHabilitar.addActionListener(c);
        btnHabilitar.setActionCommand(ConstantesBotones.HABILITAR_USUARIO);

        btnDeshabilitar.addActionListener(c);
        btnDeshabilitar.setActionCommand(ConstantesBotones.DESHABILITAR_USUARIO);

        tblUsuarios.getSelectionModel().addListSelectionListener(c);
    }
}