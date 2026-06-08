package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dam.model.pojos.Producto;

/**
 * Clase Data Access Object (DAO) para la gestión de la tabla de productos (`TableProductoDAO`).
 * <p>
 * Centraliza y administra todas las consultas y mutaciones CRUD en la base de datos para la 
 * entidad Producto. Permite realizar búsquedas filtradas por criterios combinados, inserciones, 
 * actualizaciones, borrado lógico (habilitar/deshabilitar) y manipulación directa de existencias (stock).
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class TableProductoDAO {

	/** Instancia encargada de gestionar los parámetros de configuración y conexiones de la base de datos. */
	private AccessDBProp acc;

	/** Nombre de la tabla de productos en la base de datos. */
	private static final String NOM_TAB = "producto";

	/** Nombre de la columna que representa el identificador único del producto. */
	private static final String COL_PRODUCTO_ID = "producto_id";
	/** Nombre de la columna comercial del producto. */
	private static final String COL_NOMBRE = "nombre";
	/** Nombre de la columna destinada a la clasificación o categoría del artículo. */
	private static final String COL_CATEGORIA = "categoria";
	/** Nombre de la columna del costo o valor monetario unitario. */
	private static final String COL_PRECIO = "precio";
	/** Nombre de la columna reservada para la ficha de detalles o texto técnico. */
	private static final String COL_DESCRIPCION = "descripcion";
	/** Nombre de la columna que contabiliza las existencias disponibles en almacén. */
	private static final String COL_STOCK = "stock";
	/** Nombre de la columna de control para el borrado lógico o vigencia comercial (1 para activo, 0 para inactivo). */
	private static final String COL_ACTIVO = "activo";

	/**
	 * Constructor por defecto de la clase.
	 * Inicializa el puente de propiedades hacia el motor de persistencia.
	 */
	public TableProductoDAO() {
		acc = new AccessDBProp();
	}

	/**
	 * Ejecuta una consulta dinámica parametrizada para extraer productos basándose en múltiples filtros opcionales.
	 * <p>
	 * Construye la sentencia SQL mediante concatenación segura según el estado de los argumentos, traduciendo 
	 * rangos comerciales de precio a operadores lógicos SQL (`BETWEEN`, `&lt;`, `&gt;`) y aplicando búsquedas 
	 * parciales mediante el operador `LIKE` para el nombre.
	 * </p>
	 * * @param nombre      Cadena con el nombre o patrón inicial de búsqueda (se aplica comodín al final).
	 * @param precio      Rango estipulado en formato texto (e.g., "&lt; 10 €", "10 - 50 €", "&gt; 50 €").
	 * @param categoria   Filtro estricto de categoría por coincidencia exacta.
	 * @param soloActivos Indicador booleano; si es {@code true}, restringe los resultados a artículos con vigencia activa.
	 * @return Un {@link ArrayList} poblado con los objetos de tipo {@link Producto} que cumplen las condiciones.
	 */
	public ArrayList<Producto> selectProductos(String nombre, String precio, String categoria, boolean soloActivos) {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sen = "SELECT * FROM " + NOM_TAB + " WHERE 1=1";

		if (soloActivos) {
			sen += " AND " + COL_ACTIVO + " = 1";
		}
		if (nombre != null && !nombre.isEmpty()) {
			sen += " AND " + COL_NOMBRE + " LIKE ?";
		}
		if (precio != null) {
			switch (precio) {
			case "< 10 €":
				sen += " AND " + COL_PRECIO + " < 10";
				break;
			case "10 - 50 €":
				sen += " AND " + COL_PRECIO + " BETWEEN 10 AND 50";
				break;
			case "> 50 €":
				sen += " AND " + COL_PRECIO + " > 50";
				break;
			}
		}
		if (categoria != null && !categoria.isEmpty()) {
			sen += " AND " + COL_CATEGORIA + " = ?";
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);

			int param = 1;
			if (nombre != null && !nombre.isEmpty()) {
				pstmt.setString(param++, nombre + "%");
			}
			if (categoria != null && !categoria.isEmpty()) {
				pstmt.setString(param++, categoria);
			}

			rslt = pstmt.executeQuery();

			while (rslt.next()) {
				productos.add(new Producto(rslt.getInt(COL_PRODUCTO_ID), rslt.getString(COL_NOMBRE),
						rslt.getString(COL_CATEGORIA), rslt.getDouble(COL_PRECIO), rslt.getString(COL_DESCRIPCION),
						rslt.getInt(COL_STOCK), rslt.getBoolean(COL_ACTIVO)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return productos;
	}

	/**
	 * Recupera el catálogo completo de categorías sin duplicados existentes en el inventario.
	 * * @return Un {@link ArrayList} de cadenas {@link String} conteniendo los nombres de las categorías.
	 */
	public ArrayList<String> selectCategorias() {
		ArrayList<String> categorias = new ArrayList<String>();

		String sen = "SELECT DISTINCT " + COL_CATEGORIA + " FROM " + NOM_TAB;

		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;

		try {
			con = acc.getConnection();

			stmt = con.createStatement();

			rslt = stmt.executeQuery(sen);

			while (rslt.next()) {
				categorias.add(rslt.getString(COL_CATEGORIA));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
		} finally {
			try {
				if (rslt != null)
					rslt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return categorias;
	}

	/**
	 * Deshabilita de forma lógica un artículo modificando su estado de vigencia a inactivo (0) según su nombre.
	 * * @param nombre El nombre exacto del artículo a dar de baja.
	 * @return Un mensaje informativo detallando el resultado de la transacción o el error provocado.
	 */
	public String disableProd(String nombre) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_ACTIVO + " = 0 WHERE " + COL_NOMBRE + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, nombre);

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Se ha deshabilitado el producto con éxito";
			} else {
				return "Algo malo ocurrió";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Error: se ha producido un error al establecer la conexion con la base de datos";
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Restaura de forma lógica un artículo modificando su estado de vigencia a activo (1) según su nombre.
	 * * @param nombre El nombre exacto del artículo a dar de alta.
	 * @return Un mensaje informativo detallando el resultado de la transacción o el error provocado.
	 */
	public String enableProd(String nombre) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_ACTIVO + " = 1 WHERE " + COL_NOMBRE + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, nombre);

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Se ha habilitado el producto con éxito";
			} else {
				return "Algo malo ocurrió";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Error: se ha producido un error al establecer la conexion con la base de datos";
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Da de alta un nuevo registro físico de producto en la base de datos.
	 * * @param p Instancia de la entidad {@link Producto} con los datos de creación.
	 * @return Un mensaje informativo detallando el éxito del registro o la descripción del error.
	 */
	public String insertProducto(Producto p) {
		String sen = "INSERT INTO " + NOM_TAB + " (" + COL_NOMBRE + ", " + COL_CATEGORIA + ", " + COL_PRECIO + ", "
				+ COL_DESCRIPCION + ", " + COL_STOCK + ") VALUES(?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getCategoria());
			pstmt.setDouble(3, p.getPrecio());
			pstmt.setString(4, p.getDescripcion());
			pstmt.setInt(5, p.getStock());

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Producto agregado con éxito";
			} else {
				return "Algo malo ocurrió";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Error: se ha producido un error al establecer la conexion con la base de datos";
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Modifica los atributos de un producto existente localizándolo mediante su identificador primario.
	 * * @param p Instancia de la entidad {@link Producto} con las modificaciones requeridas.
	 * @return Un mensaje descriptivo del estado de la ejecución.
	 */
	public String updateProducto(Producto p) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_NOMBRE + " = ?, " + COL_CATEGORIA + " = ?, " + COL_PRECIO
				+ " = ?, " + COL_DESCRIPCION + " = ?, " + COL_STOCK + " = ? WHERE " + COL_PRODUCTO_ID + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getCategoria());
			pstmt.setDouble(3, p.getPrecio());
			pstmt.setString(4, p.getDescripcion());
			pstmt.setInt(5, p.getStock());
			pstmt.setInt(6, p.getId());

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Producto modificado con éxito";
			} else {
				return "Algo malo ocurrió";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Error: se ha producido un error al establecer la conexion con la base de datos";
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Disminuye las unidades en stock de un producto específico, restando el valor provisto del total actual.
	 * * @param productoId Identificador numérico único del producto.
	 * @param cantidad   Unidades físicas que se sustraerán de las existencias.
	 */
	public void decrementarStock(int productoId, int cantidad) {
		String sen = "UPDATE " + NOM_TAB + " SET " + COL_STOCK + " = " + COL_STOCK + " - ? WHERE " + COL_PRODUCTO_ID
				+ " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();
			pstmt = con.prepareStatement(sen);
			pstmt.setInt(1, cantidad);
			pstmt.setInt(2, productoId);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Incrementa las unidades en stock de un producto específico, sumando el valor provisto al total actual.
	 * * @param productoId Identificador numérico único del producto.
	 * @param cantidad   Unidades físicas que se añadirán a las existencias.
	 */
	public void incrementarStock(int productoId, int cantidad) {
	    String sen = "UPDATE " + NOM_TAB + " SET " + COL_STOCK + " = " + COL_STOCK + " + ? WHERE " + COL_PRODUCTO_ID + " = ?";

	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = acc.getConnection();
	        pstmt = con.prepareStatement(sen);
	        pstmt.setInt(1, cantidad);
	        pstmt.setInt(2, productoId);
	        pstmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}