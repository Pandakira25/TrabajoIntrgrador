package com.dam.view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class Fuentes {
    public static Font REGULAR;
    public static Font BOLD;
    public static Font MEDIUM;

    static {
        try {
            REGULAR = Font.createFont(Font.TRUETYPE_FONT, 
                new File("resources/fonts/Nunito-Regular.ttf")).deriveFont(14f);
            BOLD = Font.createFont(Font.TRUETYPE_FONT, 
                new File("resources/fonts/Nunito-Bold.ttf")).deriveFont(14f);
            MEDIUM = Font.createFont(Font.TRUETYPE_FONT, 
            		new File("resources/fonts/Nunito-Medium.ttf")).deriveFont(14f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(REGULAR);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(BOLD);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(MEDIUM);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("aaaaaaaaa");
            REGULAR = new Font("Tahoma", Font.PLAIN, 14);
            BOLD = new Font("Tahoma", Font.BOLD, 14);
            MEDIUM = new Font("Tahoma", Font.PLAIN, 14);
        }
    }
}