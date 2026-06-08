package com.dam.view;

import com.dam.ctrl.Ctrl;
import javax.swing.JPanel;

/**
 * Interfaz contractual destinada a homogeneizar la estructura y comportamiento de las ventanas (`IFrames`).
 * <p>
 * Define los métodos necesarios para la inicialización geométrica, la construcción secuencial 
 * de la interfaz de usuario, la vinculación del flujo de control reactivo y la gestión dinámica 
 * del intercambio de paneles en un contenedor central.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public interface IFrames {
    
    /**
     * Define los parámetros geométricos, estéticos y de dimensionamiento del marco de la ventana.
     */
    public void configurarVentana();

    // Crea y añade los componentes Swing al contenedor (JScrollPane central).
    /**
     * Inicializa, posiciona e inyecta todos los elementos y widgets gráficos del contenedor visual.
     */
    public void crearComponentes();

    // Inyecta el controlador para que los componentes puedan registrarlo como listener.
    // Se llama DESPUÉS de crear el controlador en Inicio.java.
    /**
     * Vincula el objeto controlador unificado a los disparadores de eventos de la ventana.
     * * @param c Instancia de la clase controladora principal {@link Ctrl}.
     */
    public void setControlador(Ctrl c);

    // Hace visible la ventana. Siempre al final, cuando todo está listo.
    /**
     * Altera el estado de visibilidad del componente marco para renderizarlo en pantalla.
     */
    public void hacerVisible();

    // Intercambia el panel mostrado en el JScrollPane central.
    // Así una sola ventana muestra distintas "pantallas" sin abrir ventanas nuevas.
    /**
     * Reemplaza de forma dinámica el lienzo visible actual por un nuevo contenedor de elementos.
     * * @param panel Instancia de tipo {@link JPanel} que se desea acoplar en el visor central.
     */
    public void cargarPanel(JPanel panel);
}