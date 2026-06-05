package com.dam.model.acessbd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AccessDBProp {
	private String driver;
	private String url;
	
	public AccessDBProp() {
		Properties prop = new Properties();
		
		InputStream is = null;
		
		try {
			is = new FileInputStream("DB/ConfiguracionDB.properties");
			prop.load(is);
			
			driver = prop.getProperty("DRIVER");
			url = prop.getProperty("URL");
			
		}catch(FileNotFoundException e) {
			System.out.println("El fichero no ha sido encontrado");
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("No se ha podido leer el fichero");
		}
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		
		Connection con = DriverManager.getConnection(url);
		
		return con;
	}
	
	public void ejecutarScript(String rutaScript) {
	    try (Connection con = getConnection()) {
	        String sql = new String(Files.readAllBytes(Paths.get(rutaScript)));
	        String[] sentencias = sql.split(";");
	        for (String sentencia : sentencias) {
	            String s = sentencia.trim();
	            // ignorar líneas vacías y comentarios
	            if (!s.isEmpty() && !s.startsWith("--")) {
	                try (Statement stmt = con.createStatement()) {
	                    stmt.execute(s);
	                }
	            }
	        }
	        System.out.println("Script ejecutado correctamente");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error al ejecutar el script");
	    }
	}
}
