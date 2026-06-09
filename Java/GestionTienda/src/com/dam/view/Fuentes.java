package com.dam.view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * Clase de utilidad para la gestión y carga de tipografías personalizadas en la aplicación (`Fuentes`).
 * <p>
 * Centraliza la inicialización de los estilos de la fuente corporativa "Nunito" (Regular, Bold y Medium) 
 * desde archivos locales TrueType (TTF). Las fuentes se registran dinámicamente en el entorno gráfico 
 * local mediante {@link GraphicsEnvironment}. En caso de fallo o ausencia de los archivos de recursos, 
 * la clase implementa un mecanismo de contingencia ("fallback") aplicando por defecto la familia tipográfica "Tahoma".
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class Fuentes {
    /** Representa la variante de estilo normal o regular de la fuente, con un tamaño por defecto de 14 puntos. */
    public static Font REGULAR;
    /** Representa la variante de estilo negrita o bold de la fuente, con un tamaño por defecto de 14 puntos. */
    public static Font BOLD;
    /** Representa la variante de estilo medio o medium de la fuente, con un tamaño por defecto de 14 puntos. */
    public static Font MEDIUM;

    static {
        try {
            REGULAR = Font.createFont(Font.TRUETYPE_FONT, 
                Fuentes.class.getResourceAsStream("/resources/fonts/Nunito-Regular.ttf")).deriveFont(14f);
            BOLD = Font.createFont(Font.TRUETYPE_FONT, 
                Fuentes.class.getResourceAsStream("/resources/fonts/Nunito-Bold.ttf")).deriveFont(14f);
            MEDIUM = Font.createFont(Font.TRUETYPE_FONT, 
                Fuentes.class.getResourceAsStream("/resources/fonts/Nunito-Medium.ttf")).deriveFont(14f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(REGULAR);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(BOLD);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(MEDIUM);
        } catch (Exception e) {
            e.printStackTrace();
            REGULAR = new Font("Tahoma", Font.PLAIN, 14);
            BOLD = new Font("Tahoma", Font.BOLD, 14);
            MEDIUM = new Font("Tahoma", Font.PLAIN, 14);
        }
    }
}