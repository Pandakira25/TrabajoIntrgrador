package com.dam.view;

import com.dam.ctrl.Ctrl;
import javax.swing.JPanel;

public interface IFrames {
    public void configurarVentana();

    // Crea y añade los componentes Swing al contenedor (JScrollPane central).
    public void crearComponentes();

    // Inyecta el controlador para que los componentes puedan registrarlo como listener.
    // Se llama DESPUÉS de crear el controlador en Inicio.java.
    public void setControlador(Ctrl c);

    // Hace visible la ventana. Siempre al final, cuando todo está listo.
    public void hacerVisible();

    // Intercambia el panel mostrado en el JScrollPane central.
    // Así una sola ventana muestra distintas "pantallas" sin abrir ventanas nuevas.
    public void cargarPanel(JPanel panel);
}
