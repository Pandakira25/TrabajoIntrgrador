package com.dam.model.acessbd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dam.model.pojos.Comprador;
import com.dam.model.pojos.Usuario;
import com.dam.model.pojos.Empleado;

/**
 * Clase Data Access Object (DAO) para la gestión de la tabla de usuarios (`TableUsuarioDAO`).
 * <p>
 * Centraliza la persistencia y control de accesos para la entidad Usuario y sus respectivas 
 * especializaciones (Comprador y Empleado). Ofrece utilidades para autenticación (login), 
 * inserciones polimórficas con herencia en base de datos (Table-per-Subclass), consultas de perfiles, 
 * modificaciones de datos y borrado lógico mediante flags de activación.
 * </p>
 * * @author Zoe
 * @version 1.0
 */
public class TableUsuarioDAO {

	/** Instancia encargada de gestionar los parámetros de configuración y conexiones de la base de datos. */
	private AccessDBProp acc;

	/** Nombre de la columna que representa el identificador único de la tabla base de usuarios. */
	private static final String COL_USUARIO_ID = "usuario_id";
	/** Nombre de la columna de clave foránea que identifica al empleado. */
	private static final String COL_EMPLEADO_ID = "empleado_id";
	/** Nombre de la columna de clave foránea que identifica al comprador. */
	private static final String COL_COMPRADOR_ID = "comprador_id";

	/** Nombre de la columna que almacena el nivel o rol de autorización del usuario. */
	private static final String COL_AUTORIZACION = "autorizacion";
	/** Nombre de la columna que almacena el alias o nombre del usuario. */
	private static final String COL_NOMBRE = "nombre";
	/** Nombre de la columna destinada a la contraseña de acceso cifrada o en texto plano. */
	private static final String COL_CONTRASENIA = "contrasenia";
	/** Nombre de la columna de control para el borrado lógico o estado de vigencia del usuario. */
	private static final String COL_ACTIVO = "activo";
	/** Nombre de la columna asignada al número de teléfono de contacto. */
	private static final String COL_TEL = "tel";

	/** Nombre de la columna que almacena el número de la seguridad social (específico de empleado). */
	private static final String COL_N_SEG_S = "n_seg_social";
	/** Nombre de la columna que contiene el código internacional de cuenta bancaria IBAN (específico de empleado). */
	private static final String COL_IBAN = "iban";

	/** Nombre de la columna destinada a la localización o dirección postal (específico de comprador). */
	private static final String COL_DIRECCION = "direccion";
	/** Nombre de la columna que almacena el número de la tarjeta de crédito/débito asociada (específico de comprador). */
	private static final String COL_N_TARJETA = "n_tarjeta";

	/** Nombre de la tabla base general de usuarios en el modelo relacional. */
	private static final String NOM_TAB = "usuario";
	/** Nombre de la tabla de extensión para datos específicos de compradores. */
	private static final String NOM_TAB_COMPRADOR = "comprador";
	/** Nombre de la tabla de extensión para datos específicos de empleados. */
	private static final String NOM_TAB_EMPLEADO = "empleado";

	/**
	 * Constructor por defecto de la clase.
	 * Inicializa el puente de propiedades hacia el sistema gestor de bases de datos.
	 */
	public TableUsuarioDAO() {
		acc = new AccessDBProp();
	}

	/**
	 * Realiza la validación de credenciales de un usuario contra la base de datos para iniciar sesión.
	 * <p>
	 * Emplea la infraestructura segura de Java Try-With-Resources para garantizar el cierre automático 
	 * de las conexiones y flujos abiertos de comunicación SQL.
	 * </p>
	 * * @param usuario     Cadena de texto con el nombre único de acceso del usuario.
	 * @param contrasenia Cadena de texto con la clave correspondiente para la validación.
	 * @return Una instancia del POJO {@link Usuario} debidamente poblada si las credenciales coinciden; 
	 * u {@code null} si el registro no existe o si se captura una excepción.
	 */
	public Usuario login(String usuario, String contrasenia) {
		String sql = "SELECT " + COL_USUARIO_ID + ", " + COL_AUTORIZACION + ", " + COL_NOMBRE + ", " + COL_CONTRASENIA
				+ ", " + COL_TEL + ", " + COL_ACTIVO + " FROM usuario WHERE " + COL_NOMBRE + " = ? AND "
				+ COL_CONTRASENIA + " = ?";

		try (Connection con = acc.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, usuario);
			ps.setString(2, contrasenia);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Usuario(rs.getInt(COL_USUARIO_ID), rs.getInt(COL_AUTORIZACION), rs.getString(COL_NOMBRE),
							rs.getString(COL_CONTRASENIA), rs.getInt(COL_TEL), rs.getInt(COL_ACTIVO) == 1);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Inserta un registro de usuario de manera polimórfica en el modelo de datos relacional.
	 * <p>
	 * Primero, guarda el registro general en la tabla base {@code usuario} extrayendo el ID autonumérico 
	 * autogenerado mediante {@link Statement#RETURN_GENERATED_KEYS}. Posteriormente, evalúa mediante 
	 * {@code instanceof} si la referencia corresponde a un {@link Empleado} o a un {@link Comprador}, 
	 * procediendo a insertar los atributos extendidos en su respectiva subtabla relacional.
	 * </p>
	 * * @param u Instancia u objeto base de tipo {@link Usuario} (o cualquiera de sus subclases).
	 * @return El entero {@code 1} si la inserción en cascada fue exitosa en todas las tablas implicadas; 
	 * u {@code -1} si la transacción falló o se produjo una anomalía.
	 */
	public int insertUsr(Usuario u) {
	    String senUsr = "INSERT INTO " + NOM_TAB + " (" + COL_AUTORIZACION + ", " + COL_NOMBRE + ", " + COL_CONTRASENIA + ", " + COL_ACTIVO + ", " + COL_TEL + ") VALUES(?,?,?,?,?)";

	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = acc.getConnection();
	        pstmt = con.prepareStatement(senUsr, Statement.RETURN_GENERATED_KEYS);

	        pstmt.setInt(1, u.getAutorizacion());
	        pstmt.setString(2, u.getNombre());
	        pstmt.setString(3, u.getContrasenia());
	        pstmt.setInt(4, u.isActivo() ? 1 : 0);
	        pstmt.setInt(5, u.getTel());

	        int f = pstmt.executeUpdate();

	        if (f > 0) {
	            ResultSet rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                int id = rs.getInt(1);
	                pstmt.close();

	                if (u instanceof Empleado) {
	                    Empleado emp = (Empleado) u;
	                    String senEmp = "INSERT INTO " + NOM_TAB_EMPLEADO + " (" + COL_EMPLEADO_ID + ", " + COL_N_SEG_S + ", " + COL_IBAN + ") VALUES(?,?,?)";
	                    pstmt = con.prepareStatement(senEmp);
	                    pstmt.setInt(1, id);
	                    pstmt.setString(2, emp.getnSeguridad());
	                    pstmt.setString(3, emp.getIban());
	                    pstmt.executeUpdate();

	                } else if (u instanceof Comprador) {
	                    Comprador comp = (Comprador) u;
	                    String senComp = "INSERT INTO " + NOM_TAB_COMPRADOR + " (" + COL_COMPRADOR_ID + ", " + COL_DIRECCION + ", " + COL_N_TARJETA + ") VALUES(?,?,?)";
	                    pstmt = con.prepareStatement(senComp);
	                    pstmt.setInt(1, id);
	                    pstmt.setString(2, comp.getDireccion());
	                    pstmt.setString(3, comp.getnTarjeta());
	                    pstmt.executeUpdate();
	                }

	                return 1;
	            }
	        }
	        return -1;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	/**
	 * Recupera el listado de empleados en estado activo, permitiendo un filtro parcial por nombre.
	 * <p>
	 * Realiza un acoplamiento interno (`JOIN`) entre la tabla general de usuarios y la tabla específica de 
	 * empleados basándose en el identificador único, aplicando el comodín de búsqueda parcial al parámetro.
	 * </p>
	 * * @param nombre Cadena de texto con el nombre o patrón inicial de búsqueda. Si es nulo o vacío, no aplica el filtro.
	 * @return Un {@link ArrayList} poblado con las instancias mapeadas de tipo {@link Empleado}.
	 */
	public ArrayList<Empleado> selectEmpleados(String nombre) {
	    ArrayList<Empleado> emp = new ArrayList<Empleado>();

	    String sen = "SELECT * FROM " + NOM_TAB + " u JOIN " + NOM_TAB_EMPLEADO + " e ON u." + COL_USUARIO_ID + " = e."
	            + COL_EMPLEADO_ID + " WHERE u." + COL_ACTIVO + " = 1";
	    
	    if (nombre != null && !nombre.isEmpty()) {
	        sen += " AND u." + COL_NOMBRE + " LIKE ?";
	    }

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rslt = null;

	    try {
	        con = acc.getConnection();

	        pstmt = con.prepareStatement(sen);
	        
	        if (nombre != null && !nombre.isEmpty()) {
	            pstmt.setString(1, nombre + "%");
	        }

	        rslt = pstmt.executeQuery();

	        while (rslt.next()) {
	            emp.add(new Empleado(rslt.getInt(COL_USUARIO_ID), rslt.getInt(COL_AUTORIZACION),
	                    rslt.getString(COL_NOMBRE), rslt.getString(COL_CONTRASENIA), rslt.getInt(COL_TEL),
	                    rslt.getBoolean(COL_ACTIVO), rslt.getString(COL_N_SEG_S), rslt.getString(COL_IBAN)));
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

	    return emp;
	}

	/**
	 * Realiza la baja lógica de un empleado modificando su atributo de activación a inactivo (0).
	 * * @param nombre Nombre único de identificación del empleado a dar de baja.
	 * @return Una cadena de texto con el mensaje de éxito o la descripción de la anomalía ocurrida.
	 */
	public String deleteEmp(String nombre) {
		String sen = "UPDATE usuario SET " + COL_ACTIVO + " = 0 WHERE " +  COL_NOMBRE + " = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = acc.getConnection();

			pstmt = con.prepareStatement(sen);
			pstmt.setString(1, nombre);

			int f = pstmt.executeUpdate();
			if (f > 0) {
				return "Se ha eliminado el empleado con exito";
			}else {
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
	 * Obtiene el perfil de datos consolidados de un comprador específico mediante su identificador único.
	 * <p>
	 * Agrupa mediante una operación estructural `JOIN` las columnas de la tabla base y la tabla secundaria 
	 * {@code comprador} para retornar la entidad especializada con la totalidad de su información.
	 * </p>
	 * * @param id Identificador numérico primario del usuario-comprador.
	 * @return Una instancia del POJO {@link Comprador} si se localiza en el dataset; u {@code null} en su defecto.
	 */
	public Comprador selectComprador(int id) {
	    String sen = "SELECT u.usuario_id, u.autorizacion, u.nombre, u.contrasenia, u.activo, u.tel, " +
	                 "c.direccion, c.n_tarjeta FROM " + NOM_TAB + " u JOIN " + NOM_TAB_COMPRADOR + " c ON u.usuario_id = c.comprador_id " +
	                 "WHERE u.usuario_id = ?";

	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rslt = null;

	    try {
	        con = acc.getConnection();
	        pstmt = con.prepareStatement(sen);
	        pstmt.setInt(1, id);
	        rslt = pstmt.executeQuery();

	        if (rslt.next()) {
	        	System.out.println(rslt.getInt(COL_USUARIO_ID));
	            return new Comprador(rslt.getInt(COL_USUARIO_ID), rslt.getInt("autorizacion"),
	                    rslt.getString("nombre"), rslt.getString("contrasenia"),
	                    rslt.getInt("tel"), rslt.getBoolean("activo"),
	                    rslt.getString("direccion"), rslt.getString("n_tarjeta"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
	    } finally {
	        try {
	            if (rslt != null) rslt.close();
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}

	/**
	 * Actualiza los atributos editables de un comprador tanto en la tabla común como en la específica.
	 * <p>
	 * Ejecuta dos sentencias de actualización secuenciales en un entorno transaccional, localizando las filas 
	 * mediante el identificador numérico interno provisto en el objeto.
	 * </p>
	 * * @param c Instancia de la entidad {@link Comprador} que contiene las modificaciones.
	 * @return Una cadena de texto descriptiva indicando el éxito de la actualización o el reporte de error.
	 */
	public String updateComprador(Comprador c) {
	    String senUsr = "UPDATE " + NOM_TAB + " SET " + COL_NOMBRE + " = ?, " + COL_CONTRASENIA + " = ?, " + COL_TEL + " = ? WHERE " + COL_USUARIO_ID + " = ?";
	    String senComp = "UPDATE " + NOM_TAB_COMPRADOR + " SET " + COL_DIRECCION + " = ?, " + COL_N_TARJETA + " = ? WHERE " + COL_COMPRADOR_ID + " = ?";

	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = acc.getConnection();

	        pstmt = con.prepareStatement(senUsr);
	        pstmt.setString(1, c.getNombre());
	        pstmt.setString(2, c.getContrasenia());
	        pstmt.setInt(3, c.getTel());
	        pstmt.setInt(4, c.getUserId());
	        pstmt.executeUpdate();
	        pstmt.close();

	        pstmt = con.prepareStatement(senComp);
	        pstmt.setString(1, c.getDireccion());
	        pstmt.setString(2, c.getnTarjeta());
	        pstmt.setInt(3, c.getUserId());
	        pstmt.executeUpdate();

	        return "Perfil actualizado con éxito";

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error: se ha producido un error al establecer la conexion con la base de datos";
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	 * Extrae el universo completo de usuarios registrados en la tabla general base sin discriminación de rol.
	 * * @return Un {@link ArrayList} que contiene a todos los objetos de tipo {@link Usuario} mapeados.
	 */
	public ArrayList<Usuario> selectAllUsuarios() {
	    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

	    String sen = "SELECT * FROM " + NOM_TAB;

	    Connection con = null;
	    Statement stmt = null;
	    ResultSet rslt = null;

	    try {
	        con = acc.getConnection();
	        stmt = con.createStatement();
	        rslt = stmt.executeQuery(sen);

	        while (rslt.next()) {
	            usuarios.add(new Usuario(rslt.getInt(COL_USUARIO_ID), rslt.getInt(COL_AUTORIZACION),
	                    rslt.getString(COL_NOMBRE), rslt.getString(COL_CONTRASENIA),
	                    rslt.getInt(COL_TEL), rslt.getBoolean(COL_ACTIVO)));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
	    } finally {
	        try {
	            if (rslt != null) rslt.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return usuarios;
	}

	/**
	 * Restaura el estado de habilitación de un usuario fijando su campo de actividad a activo (1).
	 * * @param id Identificador numérico primario del usuario.
	 * @return Una cadena informativa con el resultado de la actualización.
	 */
	public String habilitarUsuario(int id) {
	    String sen = "UPDATE " + NOM_TAB + " SET " + COL_ACTIVO + " = 1 WHERE " + COL_USUARIO_ID + " = ?";

	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = acc.getConnection();
	        pstmt = con.prepareStatement(sen);
	        pstmt.setInt(1, id);

	        int f = pstmt.executeUpdate();
	        if (f > 0) {
	            return "Usuario habilitado con éxito";
	        } else {
	            return "Algo malo ocurrió";
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error: se ha producido un error al establecer la conexion con la base de datos";
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	/**
	 * Ejecuta el borrado lógico de un usuario general fijando su campo de actividad a inactivo (0).
	 * * @param id Identificador numérico primario del usuario.
	 * @return Una cadena informativa con el resultado de la actualización.
	 */
	public String deshabilitarUsuario(int id) {
	    String sen = "UPDATE " + NOM_TAB + " SET " + COL_ACTIVO + " = 0 WHERE " + COL_USUARIO_ID + " = ?";

	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        con = acc.getConnection();
	        pstmt = con.prepareStatement(sen);
	        pstmt.setInt(1, id);

	        int f = pstmt.executeUpdate();
	        if (f > 0) {
	            return "Usuario deshabilitado con éxito";
	        } else {
	            return "Algo malo ocurrió";
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error: se ha producido un error al establecer la conexion con la base de datos";
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	 * Extrae los nombres de todos los usuarios clasificados como personal operativo o empleados del sistema.
	 * <p>
	 * Restringe el ámbito de la consulta filtrando por roles de autorización comprendidos en los códigos (1, 2).
	 * </p>
	 * * @return Un array unidimensional de cadenas de texto ({@code String[]}) con los nombres comerciales recolectados.
	 */
	public String[] selectNombresEmpleados() {
	    String sen = "SELECT " + COL_NOMBRE + " FROM " + NOM_TAB + " WHERE " + COL_AUTORIZACION + " IN (1, 2)";

	    Connection con = null;
	    Statement stmt = null;
	    ResultSet rslt = null;

	    try {
	        con = acc.getConnection();
	        stmt = con.createStatement();
	        rslt = stmt.executeQuery(sen);

	        ArrayList<String> nombres = new ArrayList<>();
	        while (rslt.next()) {
	            nombres.add(rslt.getString(COL_NOMBRE));
	        }

	        return nombres.toArray(new String[0]);

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error: se ha producido un error al establecer la conexion con la base de datos");
	    } finally {
	        try {
	            if (rslt != null) rslt.close();
	            if (stmt != null) stmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return new String[0];
	}
}