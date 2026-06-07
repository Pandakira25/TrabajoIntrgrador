package com.dam.view;

import com.dam.ctrl.Ctrl;

/**
 * Interfaz contractual destinada a homogeneizar el comportamiento de los paneles intermedios (`IPanels`).
 * <p>
 * Define el ciclo de vida visual básico y obligatorio para los contenedores de tipo panel de la aplicación. 
 * Establece los métodos necesarios para la definición estética, la instanciación de widgets y el enlace 
 * con el subsistema de control de eventos.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public interface IPanels {

    /**
     * Define las propiedades de dimensionamiento, escala, color y metadatos del contenedor gráfico.
     */
    public void configurarVentana();

    /**
     * Inicializa, distribuye y acopla los componentes visuales internos del lienzo.
     */
    public void crearComponentes();

    /**
     * Enlaza el objeto controlador principal a los componentes interactivos del panel para responder a los eventos del usuario.
     * * @param c Instancia de la clase controladora unificada {@link Ctrl}.
     */
    public void setControlador(Ctrl c);
}