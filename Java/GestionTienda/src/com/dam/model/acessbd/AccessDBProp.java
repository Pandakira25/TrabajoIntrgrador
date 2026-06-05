package com.dam.model.acessbd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AccessDBProp {

	private static final String CONFIG_RELATIVE = "DB/ConfiguracionDB.properties";

	private String driver;
	private String url;

	public AccessDBProp() {
		File configFile = findConfigFile();

		try (InputStream is = new FileInputStream(configFile)) {
			Properties prop = new Properties();
			prop.load(is);

			driver = prop.getProperty("DRIVER");
			url = resolveUrl(prop.getProperty("URL"), configFile);
		} catch (IOException e) {
			System.err.println("No se ha podido leer la configuración de BD: " + configFile.getAbsolutePath());
			e.printStackTrace();
		}
	}

	private static File findConfigFile() {
		File dir = new File(System.getProperty("user.dir"));

		while (dir != null) {
			File direct = new File(dir, CONFIG_RELATIVE);
			if (direct.isFile()) {
				return direct;
			}

			File fromRepoRoot = new File(dir, "Java/GestionTienda/" + CONFIG_RELATIVE);
			if (fromRepoRoot.isFile()) {
				return fromRepoRoot;
			}

			dir = dir.getParentFile();
		}

		return new File(CONFIG_RELATIVE);
	}

	private static String resolveUrl(String rawUrl, File configFile) {
		if (rawUrl == null || !rawUrl.startsWith("jdbc:sqlite:")) {
			return rawUrl;
		}

		String dbPath = rawUrl.substring("jdbc:sqlite:".length());
		File dbFile = new File(dbPath);

		if (!dbFile.isAbsolute()) {
			File projectRoot = configFile.getParentFile().getParentFile();
			dbFile = new File(projectRoot, dbPath);
		}

		return "jdbc:sqlite:" + dbFile.getAbsolutePath();
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);

		return DriverManager.getConnection(url);
	}
}
