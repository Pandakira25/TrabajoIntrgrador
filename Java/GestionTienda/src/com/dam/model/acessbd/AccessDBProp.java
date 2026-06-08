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

/**
 * Clase encargada de la configuración y gestión de conexiones a la base de datos (`AccessDBProp`).
 * <p>
 * Recupera las credenciales y parámetros de conexión (driver y URL) dinámicamente desde un 
 * archivo externo de configuración estructurado en formato de propiedades (`.properties`). 
 * Proporciona además la infraestructura básica para inicializar o alterar la base de datos 
 * mediante la lectura y ejecución secuencial de scripts SQL estructurados.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class AccessDBProp {
	/** Nombre del controlador o driver JDBC calificado que se cargará en memoria. */
	private String driver;
	/** Cadena de conexión o URL de localización específica para el motor de base de datos. */
	private String url;
	
	/**
	 * Constructor por defecto de la clase.
	 * <p>
	 * Inicializa y lee el archivo de recursos ubicado en la ruta persistente 
	 * {@code "DB/ConfiguracionDB.properties"} para mapear las propiedades clave 
	 * {@code DRIVER} y {@code URL} en los atributos locales de la instancia.
	 * En caso de fallo en la E/S, captura las excepciones correspondientes informando por consola.
	 * </p>
	 */
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
	
	/**
	 * Intenta levantar y devolver un canal de comunicación activo con la fuente de datos.
	 * <p>
	 * Carga dinámicamente la clase del Driver en el entorno de ejecución utilizando reflexión 
	 * y delega en el {@link DriverManager} la resolución de la conexión mediante la URL mapeada.
	 * </p>
	 * * @return Un objeto de tipo {@link Connection} listo para ejecutar sentencias.
	 * @throws ClassNotFoundException Si el driver especificado en las propiedades no se encuentra en el classpath.
	 * @throws SQLException           Si ocurre un error de acceso a la base de datos o la URL es incorrecta.
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		
		Connection con = DriverManager.getConnection(url);
		
		return con;
	}
	
	/**
	 * Lee de forma íntegra un archivo físico que contenga código SQL y ejecuta sus sentencias.
	 * <p>
	 * El método carga el archivo en memoria transformándolo en bytes, divide el bloque completo 
	 * utilizando el carácter delimitador de punto y coma ({@code ;}) e itera sobre las cadenas 
	 * resultantes enviando de manera individual cada instrucción no vacía al motor de base de datos.
	 * </p>
	 * * @param rutaScript Cadena de texto indicativa de la ubicación del archivo SQL a procesar.
	 */
	public void ejecutarScript(String rutaScript) {
	    try (Connection con = getConnection()) {
	        String sql = new String(Files.readAllBytes(Paths.get(rutaScript)));
	        String[] sentencias = sql.split(";");
	        for (String sentencia : sentencias) {
	            String s = sentencia.trim();
	            if (!s.isEmpty()) {
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