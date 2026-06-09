package com.dam.ctrl;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dam.model.acessbd.TableCarritoDAO;
import com.dam.model.acessbd.TableCarritoProductoDAO;
import com.dam.model.acessbd.TableProductoDAO;
import com.dam.model.acessbd.TableTransaccionesDAO;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Comprador;
import com.dam.model.pojos.Empleado;
import com.dam.model.pojos.Producto;
import com.dam.model.pojos.Usuario;
import com.dam.view.VCarrito;
import com.dam.view.VCuenta;
import com.dam.view.VGestionEmp;
import com.dam.view.VGestionProd;
import com.dam.view.VGestionStock;
import com.dam.view.VGestionUsuarios;
import com.dam.view.VPrincipal;
import com.dam.view.VRegistrarse;
import com.dam.view.VShop;
import com.dam.view.VTrans;
import com.dam.view.VloginForm;

import com.dam.view.ConstantesBotones;

/**
 * Clase Controlador principal de la aplicación (`Ctrl`).
 * <p>
 * Implementa las interfaces {@link ActionListener}, {@link MouseListener} y {@link ListSelectionListener}
 * para gestionar el flujo de navegación entre pantallas, el comportamiento de los botones y menús,
 * así como la interacción con las distintas tablas de la interfaz gráfica y la base de datos (DAOs).
 * </p>
 * * @author zoe
 * @version 1.0
 */
public class Ctrl implements ActionListener, MouseListener, ListSelectionListener {

	/** Vista principal (marco contenedor de la aplicación). */
	private VPrincipal vp;

	/** Vista del formulario de inicio de sesión (Login). */
	private VloginForm vlf;
	/** Vista del carrito de compras. */
	private VCarrito vca;
	/** Vista de la gestión de empleados (exclusiva de Administrador). */
	private VGestionEmp vgemp;
	/** Vista de la gestión de productos (CRUD de productos). */
	private VGestionProd vgprod;
	/** Vista de la gestión y actualización del stock de productos. */
	private VGestionStock vgstk;
	/** Vista del formulario de registro para nuevos compradores. */
	private VRegistrarse vr;
	/** Vista de la tienda/catálogo de productos para el comprador. */
	private VShop vsh;
	/** Vista del histórico y consulta de transacciones. */
	private VTrans vtr;
	/** Vista del perfil o datos de cuenta del comprador logueado. */
	private VCuenta vcuenta;
	/** Vista de la gestión del estado de usuarios de la aplicación. */
	private VGestionUsuarios vgusr;

	/** Objeto DAO para las operaciones sobre la tabla de usuarios. */
	private TableUsuarioDAO usuarioDAO = new TableUsuarioDAO();
	/** Objeto DAO para las operaciones sobre la tabla de carritos. */
	private TableCarritoDAO carritoDao = new TableCarritoDAO();
	/** Objeto DAO para las operaciones sobre la tabla de productos. */
	private TableProductoDAO productoDAO = new TableProductoDAO();
	/** Objeto DAO para gestionar la relación de productos dentro de un carrito. */
	private TableCarritoProductoDAO carritoProductoDAO = new TableCarritoProductoDAO();
	/** Objeto DAO para registrar y consultar transacciones. */
	private TableTransaccionesDAO transaccionesDAO = new TableTransaccionesDAO();

	/** Almacena la entidad del usuario que ha iniciado sesión actualmente. */
	private Usuario usuarioLogueado;
	/** Identificador del carrito activo del comprador actual. -1 si no hay ninguno. */
	private int carritoActivoId = -1;

	/** Constante de nivel de autorización para Administradores (Valor: 1). */
	public static final int ADMIN = 1;
	/** Constante de nivel de autorización para Empleados (Valor: 2). */
	public static final int EMPLEADO = 2;
	/** Constante de nivel de autorización para Compradores (Valor: 3). */
	public static final int COMPRADOR = 3;

	/**
	 * Constructor del controlador. Inicializa las referencias de todas las vistas
	 * necesarias y carga el panel de inicio de sesión (`VloginForm`) por defecto.
	 * * @param vp Vista principal contenedora.
	 * @param vloginForm Vista del formulario de inicio de sesión.
	 * @param vca Vista del carrito de compras.
	 * @param vgemp Vista de gestión de empleados.
	 * @param vgprod Vista de gestión de productos.
	 * @param vgstk Vista de gestión de existencias (stock).
	 * @param vr Vista de registro de usuario.
	 * @param vsh Vista de la tienda.
	 * @param vtr Vista de transacciones.
	 * @param vcuenta Vista de cuenta del comprador.
	 * @param vgusr Vista de gestión de usuarios.
	 */
	public Ctrl(VPrincipal vp, VloginForm vloginForm, VCarrito vca, VGestionEmp vgemp, VGestionProd vgprod,
			VGestionStock vgstk, VRegistrarse vr, VShop vsh, VTrans vtr, VCuenta vcuenta, VGestionUsuarios vgusr) {
		this.vp = vp;
		this.vlf = vloginForm;
		this.vca = vca;
		this.vgemp = vgemp;
		this.vgprod = vgprod;
		this.vgstk = vgstk;
		this.vr = vr;
		this.vsh = vsh;
		this.vtr = vtr;
		this.vcuenta = vcuenta;
		this.vgusr = vgusr;

		vp.cargarPanel(vlf);

		vlf.getRootPane().setDefaultButton(vlf.getBtnEntrar());
	}

	/**
	 * Captura y procesa los eventos de acción producidos por componentes que emiten
	 * {@link ActionEvent} (por ejemplo, botones y opciones de menú).
	 * Distingue la lógica dependiendo de si el evento proviene de un item de menú
	 * (`JMenuItem`) o de un botón estándar (`JButton`).
	 * * @param e Objeto con los detalles del evento de acción provocado.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Component src = (Component) e.getSource();
		String ac = e.getActionCommand();
		//System.out.println(ac);
		// System.out.println(src);
		// System.out.println(vloginForm.getBtnregister());

		if (src instanceof JMenuItem) {
			switch (ac) {
			case ConstantesBotones.GESTION_EMPLEADOS:
				vp.cargarPanel(vgemp);
				break;
			case ConstantesBotones.GESTION_PRODUCTOS:
				vp.cargarPanel(vgprod);
				vgprod.cargarCategorias(productoDAO.selectCategorias());
				if (productoDAO.selectProductos(null, null, null, false) != null) {
					vgprod.cargarTabla(productoDAO.selectProductos(null, null, null, false));
				}
				vgprod.hideDescripcion();
				break;
			case ConstantesBotones.GESTION_STOCK:
				vp.cargarPanel(vgstk);
				if (productoDAO.selectProductos(null, null, null, true) != null) {
					vgstk.cargarTabla(productoDAO.selectProductos(null, null, null, false));
				}
				vgstk.cargarCategorias(productoDAO.selectCategorias());
				break;
			case ConstantesBotones.VER_TRANSACCIONES:
				vp.cargarPanel(vtr);
				if (transaccionesDAO.selectTransacciones() != null) {
					vtr.chargeEmp(usuarioDAO.selectNombresEmpleados());
					vtr.cargarTabla(transaccionesDAO.selectTransacciones());
				}
				break;
			case ConstantesBotones.COMPRAR:
				vp.cargarPanel(vsh);
				break;
			case ConstantesBotones.CARRITO:
				vp.cargarPanel(vca);
				break;
			case ConstantesBotones.MI_CUENTA:
				vp.cargarPanel(vcuenta);
				System.out.println(usuarioLogueado.getUserId()); // = 5
				Comprador comp = usuarioDAO.selectComprador(usuarioLogueado.getUserId());

				System.out.println(comp);
				if (comp != null) {
					vcuenta.cargarDatos(comp);
				}
				break;
			case ConstantesBotones.GESTION_USUARIOS:
				vp.cargarPanel(vgusr);
				vgusr.cargarTabla(usuarioDAO.selectAllUsuarios());
				break;
			}
		} else if (src instanceof JButton) {
			Container srcCont = src.getParent();

			switch (srcCont.getName()) {
			case VloginForm.NAME:
				acVLF(ac);
				break;
			case VRegistrarse.NAME:
				acVR(ac);
				break;
			case VGestionEmp.NAME:
				acVGE(ac);
				break;
			case VGestionProd.NAME:
				acVGP(ac);
				break;
			case VGestionStock.NAME:
				acVGSt(ac);
				break;
			case VTrans.NAME:
				acVT(ac);
				break;
			case VCarrito.NAME:
				acVCa(ac);
				break;
			case VShop.NAME:
				acVSh(ac);
				break;
			case VCuenta.NAME:
				acVCuenta(ac);
				break;
			case VGestionUsuarios.NAME:
				acVGU(ac);
				break;
			}
		}
	}

	/**
	 * Procesa los comandos de acción originados dentro del panel de Gestión de Usuarios.
	 * Permite habilitar o deshabilitar las cuentas de usuario seleccionadas.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVGU(String ac) {
		switch (ac) {
		case ConstantesBotones.HABILITAR_USUARIO:
			int fila = vgusr.getTblUsuarios().getSelectedRow();
			if (fila != -1) {
				int x = JOptionPane.showConfirmDialog(vgusr, "¿Seguro que desea habilitar este usuario?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (x == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(vgusr,
							usuarioDAO.habilitarUsuario(vgusr.getUsuarioEnFila(fila).getUserId()), "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
					vgusr.cargarTabla(usuarioDAO.selectAllUsuarios());
				}
			}
			break;
		case ConstantesBotones.DESHABILITAR_USUARIO:
			int fila2 = vgusr.getTblUsuarios().getSelectedRow();
			if (fila2 != -1) {
				int y = JOptionPane.showConfirmDialog(vgusr, "¿Seguro que desea deshabilitar este usuario?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (y == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(vgusr,
							usuarioDAO.deshabilitarUsuario(vgusr.getUsuarioEnFila(fila2).getUserId()), "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
					vgusr.cargarTabla(usuarioDAO.selectAllUsuarios());
				}
			}
			break;
		}
	}

	/**
	 * Procesa los comandos de acción originados dentro del panel Mi Cuenta del comprador.
	 * Permite modificar los datos personales o solicitar la baja del perfil.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVCuenta(String ac) {
		switch (ac) {
		case ConstantesBotones.MODIFICAR_COMPRADOR:
			Comprador comp = vcuenta.obtenerDatosFormulario(usuarioLogueado.getUserId());

			if (comp != null) {
				JOptionPane.showMessageDialog(vcuenta, usuarioDAO.updateComprador(comp), "Resultado",
						JOptionPane.INFORMATION_MESSAGE);
				usuarioLogueado = usuarioDAO.login(comp.getNombre(), comp.getContrasenia());
			}
			break;
		case ConstantesBotones.DARSE_DE_BAJA:
			int res = JOptionPane.showConfirmDialog(vp, "¿Seguro que desea darse de baja?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (res == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(vcuenta, usuarioDAO.deshabilitarUsuario(usuarioLogueado.getUserId()),
						"Resultado", JOptionPane.INFORMATION_MESSAGE);

				carritoActivoId = -1;
				vca.limpiarCarrito();

				usuarioLogueado = null;
				vp.quitarMenu();
				vlf.getTxtUsuario().setText("");
				vlf.getTxtContrasenia().setText("");
				vp.cargarPanel(vlf);
				vlf.getRootPane().setDefaultButton(vlf.getBtnEntrar());

			}
			break;
		}
	}

	/**
	 * Procesa los comandos de acción originados dentro de la vista de Catálogo/Tienda (`VShop`).
	 * Gestiona la búsqueda de artículos por filtros o el despliegue de su descripción extendida.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVSh(String ac) {
		switch (ac) {
		case ConstantesBotones.BUSCAR_PRODUCTO:
			String[] consulta = vsh.getConsulta();
			if (consulta == null) {
				vsh.cargarTabla(productoDAO.selectProductos(null, null, null, true));
			} else {
				vsh.cargarTabla(productoDAO.selectProductos(consulta[0], consulta[1], consulta[2], true));
			}
			break;
		case ConstantesBotones.VER_MAS:
			if (vsh.isDescripcionVisible()) {
				vsh.hideDescripcion();
			} else {
				int fila = vsh.getTblProductos().getSelectedRow();
				if (fila != -1) {
					vsh.verDescripcion(vsh.getProductoEnFila(fila).getDescripcion());
				}
			}
			break;
		case ConstantesBotones.CARRITO:
			vp.cargarPanel(vca);
			break;
		}
	}

	/**
	 * Procesa los comandos de acción originados dentro del panel del Carrito de la compra (`VCarrito`).
	 * Efectúa la lógica de pago de la compra, decrementando el stock de los productos.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVCa(String ac) {
		switch (ac) {
		case ConstantesBotones.PAGAR:
			if (carritoActivoId == -1) {
				JOptionPane.showMessageDialog(vca, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
				break;
			}
			int confirm = JOptionPane.showConfirmDialog(vca, "¿Confirmar compra?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				Integer empleadoId = vca.getEmpleadoSeleccionado();

				for (Map.Entry<Producto, Integer> entry : vca.getCantidades().entrySet()) {
					productoDAO.decrementarStock(entry.getKey().getId(), entry.getValue());
				}
				String resultado = transaccionesDAO.insertTransaccion(usuarioLogueado.getUserId(), carritoActivoId,
						empleadoId);
				JOptionPane.showMessageDialog(vca, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);
				vca.limpiarCarrito();
				vsh.cargarTabla(productoDAO.selectProductos(null, null, null, true));
				carritoActivoId = -1;
			}
			break;
		}
	}

	/**
	 * Procesa los comandos de acción originados dentro de la vista de Transacciones (`VTrans`).
	 * Filtra el listado histórico de transacciones del comercio según los criterios estipulados.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVT(String ac) {
		switch (ac) {
		case ConstantesBotones.BUSCAR_TRANSACCION:
			vtr.cargarTabla(transaccionesDAO.selectTransaccionesCons(vtr.getConsulta()));
			break;
		}
	}

	/**
	 * Procesa los comandos de acción originados en la vista de control de Existencias/Stock (`VGestionStock`).
	 * Incrementa o decrementa de forma directa las unidades de un artículo determinado en almacén.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVGSt(String ac) {
		switch (ac) {
		case ConstantesBotones.BUSCAR_PRODUCTO:
			// System.out.println("xd");
			String[] consulta = vgstk.getConsulta();
			System.out.println(consulta);
			if (consulta == null) {
				vgstk.cargarTabla(productoDAO.selectProductos(null, null, null, false));
			} else {
				vgstk.cargarTabla(productoDAO.selectProductos(consulta[0], consulta[1], consulta[2], true));
			}
			break;
		case ConstantesBotones.MAS:
			int filaMas = vgstk.getTblProductos().getSelectedRow();
			if (filaMas != -1) {
				int cantidad = vgstk.getCantidad();
				Producto p = vgstk.getProductoEnFila(filaMas);
				productoDAO.incrementarStock(p.getId(), cantidad);
				vgstk.cargarTabla(productoDAO.selectProductos(null, null, null, false));
			}
			break;
		case ConstantesBotones.MENOS:
			int filaMenos = vgstk.getTblProductos().getSelectedRow();
			if (filaMenos != -1) {
				int cantidad = vgstk.getCantidad();
				Producto p = vgstk.getProductoEnFila(filaMenos);
				if (cantidad > p.getStock()) {
					JOptionPane.showMessageDialog(vgstk,
							"No puedes restar más stock del disponible. Stock actual: " + p.getStock(),
							"Stock insuficiente", JOptionPane.WARNING_MESSAGE);
				} else {
					productoDAO.decrementarStock(p.getId(), cantidad);
					vgstk.cargarTabla(productoDAO.selectProductos(null, null, null, false));
				}
			}
			break;
		case ConstantesBotones.VER_MAS:
			// System.out.println("noc");
			int fila = vgstk.getTblProductos().getSelectedRow();

			if (fila != -1) {
				vgstk.verDescripcion(vgstk.getProductoEnFila(fila).getDescripcion());
			}
			break;
		case ConstantesBotones.VER_MENOS:
			vgstk.hideDescripcion();
		}
	}

	/**
	 * Procesa los comandos de acción de la vista CRUD para artículos y mercaderías (`VGestionProd`).
	 * Realiza altas, modificaciones, búsquedas y lógicas de inhabilitación/habilitación de productos.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVGP(String ac) {
		switch (ac) {
		case ConstantesBotones.ADD_PRODUCTO:
			Producto nuevo = vgprod.obtenerDatosFormulario();
			if (nuevo != null) {
				JOptionPane.showMessageDialog(vgprod, productoDAO.insertProducto(nuevo), "Resultado de la operación",
						JOptionPane.INFORMATION_MESSAGE);
				vgprod.limpiarDatos();
				vgprod.cargarTabla(productoDAO.selectProductos(null, null, null, false));
				vgprod.cargarCategorias(productoDAO.selectCategorias());
			}
			break;
		case ConstantesBotones.MODIFICAR_PRODUCTO:
			if (vgprod.getIdSeleccionado() == -1) {
				JOptionPane.showMessageDialog(vgprod, "Selecciona un producto de la tabla para modificar.",
						"Error de datos", JOptionPane.WARNING_MESSAGE);
				break;
			}
			Producto modificado = vgprod.obtenerDatosFormulario();
			if (modificado != null) {
				JOptionPane.showMessageDialog(vgprod, productoDAO.updateProducto(modificado),
						"Resultado de la operación", JOptionPane.INFORMATION_MESSAGE);
				vgprod.limpiarDatos();
				vgprod.cargarTabla(productoDAO.selectProductos(null, null, null, false));
				vgprod.cargarCategorias(productoDAO.selectCategorias());
			}
			break;
		case ConstantesBotones.LIMPIAR:
			vgprod.limpiarDatos();
			break;
		case ConstantesBotones.BUSCAR_PRODUCTO:
			// System.out.println("xd");
			String[] consulta = vgprod.getConsulta();
			if (consulta == null) {
				vgprod.cargarTabla(productoDAO.selectProductos(null, null, null, false));
			} else {
				vgprod.cargarTabla(productoDAO.selectProductos(consulta[0], consulta[1], consulta[2], false));
			}
			break;
		case ConstantesBotones.DESHABILITAR_PRODUCTO:
			int x = JOptionPane.showConfirmDialog(vgprod, "¿Seguro que desea deshabilitar el producto?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (x == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(vgprod, productoDAO.disableProd(vgprod.getNombreSeleccionado()),
						"Resultado de la operación", JOptionPane.INFORMATION_MESSAGE);
				vgprod.cargarTabla(productoDAO.selectProductos(null, null, null, false));
			}
			break;
		case ConstantesBotones.HABILITAR_PRODUCTO:
			int y = JOptionPane.showConfirmDialog(vgprod, "¿Seguro que desea habilitar el producto?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (y == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(vgprod, productoDAO.enableProd(vgprod.getNombreSeleccionado()),
						"Resultado de la operación", JOptionPane.INFORMATION_MESSAGE);
				vgprod.cargarTabla(productoDAO.selectProductos(null, null, null, false));
			}
			break;
		case ConstantesBotones.VER_MAS:
			int fila = vgemp.getTblProductos().getSelectedRow();

			if (fila != -1) {
				vgprod.verDescripcion(vgstk.getProductoEnFila(fila).getDescripcion());
			}
			break;
		case ConstantesBotones.VER_MENOS:
			vgprod.hideDescripcion();
		}
	}

	/**
	 * Procesa los comandos de acción asociados con la pantalla de Gestión de Empleados (`VGestionEmp`).
	 * Encargado de registrar altas, realizar búsquedas o la remoción (eliminación) de personal.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVGE(String ac) {
		switch (ac) {
		case ConstantesBotones.BUSCAR_EMPLEADO:
			vgemp.cargarTabla(usuarioDAO.selectEmpleados(vgemp.getConsulta()));
			break;
		case ConstantesBotones.REGISTRAR_EMPLEADO:
			Empleado emp = vgemp.obtenerDatosFormulario();
			if (emp != null) {
				if (usuarioDAO.insertUsr(emp) > 0) {
					JOptionPane.showMessageDialog(vgemp, "Empleado registrado con éxito.", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
					vgemp.limpiarDatos();
					vgemp.cargarTabla(usuarioDAO.selectEmpleados(null));
				} else {
					JOptionPane.showMessageDialog(vgemp, "Error al registrar el empleado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case ConstantesBotones.LIMPIAR:
			vgemp.limpiarDatos();
			break;
		case ConstantesBotones.ELIMINAR_EMPLEADO:
			System.out.println("xd");
			int x = JOptionPane.showConfirmDialog(vgemp, "¿Seguro que desea eliminarlo?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (x == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(vgemp, usuarioDAO.deleteEmp(vgemp.getNombreSeleccionado()),
						"Resultado de la operación", JOptionPane.INFORMATION_MESSAGE);
				vgemp.cargarTabla(usuarioDAO.selectEmpleados(null));
			}

			break;
		}
	}

	/**
	 * Procesa los comandos de acción de la pantalla de Registro de Compradores (`VRegistrarse`).
	 * Valida e introduce un nuevo comprador en el sistema.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVR(String ac) {
		switch (ac) {
		case ConstantesBotones.REGISTRARSE:
			Comprador compr = vr.obtenerDatos();
			if (compr != null) {
				if (usuarioDAO.insertUsr(compr) > 0) {
					JOptionPane.showMessageDialog(vr, "Usuario insertado con éxito.", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);
					vp.cargarPanel(vlf);
					vlf.setTxtUsuario(compr.getNombre());
					vlf.getRootPane().setDefaultButton(vlf.getBtnEntrar());
				} else {
					JOptionPane.showMessageDialog(vr, "Error al insertar el usuario.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case ConstantesBotones.CANCELAR:
			int respuesta = JOptionPane.showConfirmDialog(vp, "¿Seguro que desea cancelar?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (respuesta == JOptionPane.YES_OPTION) {
				vp.cargarPanel(vlf);
			}
			break;
		}
	}

	/**
	 * Procesa los comandos de acción iniciales del panel de Login (`VloginForm`).
	 * Redirige al proceso de entrada o a la pantalla de auto-registro.
	 * * @param ac El comando de acción asociado al botón pulsado.
	 */
	private void acVLF(String ac) {
		switch (ac) {
		case ConstantesBotones.ENTRAR:
			entrar();
			break;
		case ConstantesBotones.REGISTRARSE:
			vp.cargarPanel(vr);
			break;
		}
	}

	/**
	 * Ejecuta la lógica de autenticación (Login) de usuarios en la plataforma.
	 * Comprueba las credenciales contra la base de datos, verifica que la cuenta esté activa
	 * y despliega la barra de menús y la vista asignada de acuerdo a su rol (ADMIN, EMPLEADO o COMPRADOR).
	 */
	private void entrar() {
		String nombre = vlf.getUsr()[0].trim();
		String pwd = new String(vlf.getUsr()[1]);

		if (nombre.isEmpty() || pwd.isEmpty()) {
			JOptionPane.showMessageDialog(vlf, "Introduce usuario y contraseña.", "Login", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Usuario u = usuarioDAO.login(nombre, pwd);

		if (u == null) {
			JOptionPane.showMessageDialog(vp, "Usuario o contraseña incorrectos.", "Login", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!u.isActivo()) {
			JOptionPane.showMessageDialog(vp, "Esta cuenta está desactivada.", "Login", JOptionPane.WARNING_MESSAGE);
			return;
		}

		usuarioLogueado = u;

		int autorizacion = u.getAutorizacion();

		// System.out.println(autorizacion);

		switch (autorizacion) {
		case ADMIN:
			vp.menuAdmin();
			vp.cargarPanel(vgemp);
			ArrayList<Empleado> emp = usuarioDAO.selectEmpleados(null);
			if (emp != null)
				vgemp.cargarTabla(emp);
			break;
		case EMPLEADO:
			vp.crearMenuBase();
			vp.cargarPanel(vgstk);
			if (productoDAO.selectProductos(null, null, null, true) != null)
				vgstk.cargarTabla(productoDAO.selectProductos(null, null, null, false));
			vgstk.cargarCategorias(productoDAO.selectCategorias());
			break;
		case COMPRADOR:
			vp.menuComprador();
			vp.cargarPanel(vsh);
			vsh.cargarCategorias(productoDAO.selectCategorias());
			vsh.cargarTabla(productoDAO.selectProductos(null, null, null, true));
			vca.cargarEmpleados(usuarioDAO.selectEmpleados(null));
			carritoActivoId = carritoDao.getCarritoActivo(usuarioLogueado.getUserId());
			Comprador comp = usuarioDAO.selectComprador(usuarioLogueado.getUserId());
			if (comp != null)
				vcuenta.cargarDatos(comp);
			break;
		default:
			vp.quitarMenu();
			break;
		}

		vp.revalidate();
		vp.repaint();
	}

	/**
	 * Cierra la sesión del usuario actual previa confirmación en ventana emergente.
	 * Si el usuario es un comprador, se le notifica que los ítems del carrito pendientes se descartarán.
	 * Limpia el estado del formulario y restablece la vista al panel de Login (`VloginForm`).
	 */
	private void cerrarSesion() {
		if (usuarioLogueado.getAutorizacion() == 3) {
			int res = JOptionPane.showConfirmDialog(vp, "¿Seguro que desea cerrar sesion? su carrito será descartado",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (res == JOptionPane.YES_OPTION) {
				carritoActivoId = -1;
				vca.limpiarCarrito();

				usuarioLogueado = null;
				vp.quitarMenu();
				vlf.getTxtUsuario().setText("");
				vlf.getTxtContrasenia().setText("");
				vp.cargarPanel(vlf);
				vlf.getRootPane().setDefaultButton(vlf.getBtnEntrar());
			}
		} else {
			int res = JOptionPane.showConfirmDialog(vp, "¿Seguro que desea cerrar sesion?", "Confirmación",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (res == JOptionPane.YES_OPTION) {
				usuarioLogueado = null;
				vp.quitarMenu();
				vlf.getTxtUsuario().setText("");
				vlf.getTxtContrasenia().setText("");
				vp.cargarPanel(vlf);
				vlf.getRootPane().setDefaultButton(vlf.getBtnEntrar());
			}
		}

	}

	/**
	 * Obtiene el objeto del usuario autenticado en la sesión actual.
	 * * @return El {@link Usuario} logueado.
	 */
	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	/**
	 * Controla los eventos de clics del ratón en las tablas y menús de la interfaz.
	 * <p>
	 * Permite limpiar selecciones al clicar en zonas vacías, o interactuar directamente
	 * pulsando en columnas específicas (añadir unidades, sustraer o remover productos) 
	 * dentro de las tablas de `VCarrito` y `VShop`.
	 * </p>
	 * * @param e Detalles físicos de las coordenadas y componente del click de ratón.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Object src = e.getSource();

		if (src == vp.getScrlCont()) {
			// limpiar la tabla de la vista activa
			vgemp.getTblEmpleados().clearSelection();
			vgprod.getTblProductos().clearSelection();
			vgstk.getTblProductos().clearSelection();
			vgusr.getTblUsuarios().clearSelection();
			vtr.getTblTransacciones().clearSelection();
		}

		if (src == vca.getTblCarrito()) {
			int fila = vca.getTblCarrito().rowAtPoint(e.getPoint());
			int columna = vca.getTblCarrito().columnAtPoint(e.getPoint());
			if (fila != -1) {
				switch (columna) {
				case 3: // +
					if (!vca.sumarCantidad(fila)) {
						JOptionPane.showMessageDialog(vca, "No hay más stock disponible.", "Stock agotado",
								JOptionPane.WARNING_MESSAGE);
					} else {
						Producto p = vca.getProductoEnFila(fila);
						carritoProductoDAO.upsertProducto(carritoActivoId, p.getId(), vca.getCantidadEnFila(fila));
					}
					break;
				case 4: // -
					Producto p = vca.getProductoEnFila(fila);
					vca.restarCantidad(fila);
					int nuevaCant = vca.getCantidadEnFila(fila);
					if (nuevaCant == 0) {
						carritoProductoDAO.eliminarProducto(carritoActivoId, p.getId());
					} else {
						carritoProductoDAO.upsertProducto(carritoActivoId, p.getId(), nuevaCant);
					}
					break;
				case 5: // Eliminar
					Producto pe = vca.getProductoEnFila(fila);
					carritoProductoDAO.eliminarProducto(carritoActivoId, pe.getId());
					vca.eliminarProducto(fila);
					break;
				}
			}
		} else if (src == vsh.getTblProductos()) {
			int fila = vsh.getTblProductos().rowAtPoint(e.getPoint());
			int columna = vsh.getTblProductos().columnAtPoint(e.getPoint());
			if (fila != -1) {
				switch (columna) {
				case 3: // +
					if (!vsh.sumarCantidad(fila)) {
						JOptionPane.showMessageDialog(vsh, "No hay más stock disponible.", "Stock agotado",
								JOptionPane.WARNING_MESSAGE);
					} else {
						Producto p = vsh.getProductoEnFila(fila);
						// Si no hay carrito activo, crearlo
						if (carritoActivoId == -1) {
							carritoActivoId = carritoDao.crearCarrito(usuarioLogueado.getUserId());
						}
						vca.agregarProducto(p);
						carritoProductoDAO.upsertProducto(carritoActivoId, p.getId(), vsh.getCantidadEnFila(fila));
					}
					break;
				case 4: // -
					if (vsh.getCantidadEnFila(fila) > 0) {
						Producto p = vsh.getProductoEnFila(fila);
						vsh.restarCantidad(fila);
						int nuevaCant = vsh.getCantidadEnFila(fila);
						if (nuevaCant == 0) {
							carritoProductoDAO.eliminarProducto(carritoActivoId, p.getId());
						} else {
							carritoProductoDAO.upsertProducto(carritoActivoId, p.getId(), nuevaCant);
						}
					}
					break;
				}
			}
		}else if(src == vp.getMnCerrarSesion()) {
			cerrarSesion();
		}
	}

	/**
	 * Método de la interfaz MouseListener (Sin implementación en esta versión).
	 * * @param e Datos de evento de ratón.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Método de la interfaz MouseListener (Sin implementación en esta versión).
	 * * @param e Datos de evento de ratón.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Método de la interfaz MouseListener (Sin implementación en esta versión).
	 * * @param e Datos de evento de ratón.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("over");
	}

	/**
	 * Método de la interfaz MouseListener (Sin implementación en esta versión).
	 * * @param e Datos de evento de ratón.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Controla los cambios de estado en las selecciones de filas en las JTables.
	 * <p>
	 * Habilita o deshabilita botones dinámicamente de acuerdo al elemento marcado
	 * en los paneles de Empleados, Gestión de Productos, Catálogo, Control de Stock y Usuarios.
	 * </p>
	 * * @param e El evento que caracteriza la modificación de la fila seleccionada.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			Object src = e.getSource();

			if (src == vgemp.getSelectionModel()) {
				int fila = vgemp.getTblEmpleados().getSelectedRow();
				if (fila != -1) {
					boolean esMismoUsuario = vgemp.getNombreSeleccionado().equals(usuarioLogueado.getNombre());
					vgemp.setEliminarEnabled(!esMismoUsuario);
				} else {
					vgemp.setEliminarEnabled(false);
				}
			} else if (src == vgprod.getSelectedModel()) {
				int fila = vgprod.getTblProductos().getSelectedRow();
				if (fila != -1) {
					boolean activo = vgprod.getProductoEnFila(fila).isActivo();
					vgprod.setEliminarEnabled(activo);
					vgprod.setHabilitarEnabled(!activo);
					vgprod.setModificarEnabled(true);
					vgprod.cargarProductoEnForm(); // carga datos en formulario
					vgprod.verDescripcion(vgprod.getProductoEnFila(fila).getDescripcion());
					vgprod.setVerMasEnabled(true);
				} else {
					vgprod.setEliminarEnabled(false);
					vgprod.setHabilitarEnabled(false);
					vgprod.setModificarEnabled(false);
				}
			} else if (src == vsh.getTblProductos().getSelectionModel()) {
				int fila = vsh.getTblProductos().getSelectedRow();
				vsh.setVerMasEnabled(fila != -1);
				if (fila != -1 && vsh.isDescripcionVisible()) {
					vsh.verDescripcion(vsh.getProductoEnFila(fila).getDescripcion());
				}
			} else if (src == vgstk.getTblProductos().getSelectionModel()) {
				int fila = vgstk.getTblProductos().getSelectedRow();
				if (fila != -1) {
					vgstk.setVerMasEnabled(true);
					vgstk.setBtnMasEnabled(true);
					vgstk.setBtnMenosEnabled(true);
					if (vgstk.isDescripcionVisible()) {
						vgstk.verDescripcion(vgstk.getProductoEnFila(fila).getDescripcion());
					}
				} else {
					vgstk.setVerMasEnabled(false);
					vgstk.setBtnMasEnabled(false);
					vgstk.setBtnMenosEnabled(false);
					vgstk.hideDescripcion();
				}
			} else if (src == vgusr.getSelectionModel()) {
				// System.out.println("holi");
				int fila = vgusr.getTblUsuarios().getSelectedRow();
				if (fila != -1) {
					boolean activo = vgusr.getUsuarioEnFila(fila).isActivo();
					vgusr.setHabilitarEnabled(!activo);
					vgusr.setDeshabilitarEnabled(activo);
				} else {
					vgusr.setHabilitarEnabled(false);
					vgusr.setDeshabilitarEnabled(false);
				}
			}

		}
	}

}