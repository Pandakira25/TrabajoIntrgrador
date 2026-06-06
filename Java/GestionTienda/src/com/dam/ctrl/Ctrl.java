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
import com.dam.model.acessbd.TableCompradorDAO;
import com.dam.model.acessbd.TableEmpleadoDAO;
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
import com.dam.view.VPrincipal;
import com.dam.view.VRegistrarse;
import com.dam.view.VShop;
import com.dam.view.VTrans;
import com.dam.view.VloginForm;

import com.dam.view.ConstantesBotones;

public class Ctrl implements ActionListener, MouseListener, ListSelectionListener {

	private VPrincipal vp;

	private VloginForm vlf;
	private VCarrito vca;
	private VGestionEmp vgemp;
	private VGestionProd vgprod;
	private VGestionStock vgstk;
	private VRegistrarse vr;
	private VShop vsh;
	private VTrans vtr;
	private VCuenta vcuenta;

	private TableUsuarioDAO usuarioDAO = new TableUsuarioDAO();
	private TableCarritoDAO carritoDao = new TableCarritoDAO();
	private TableEmpleadoDAO empleadoDAO = new TableEmpleadoDAO();
	private TableCompradorDAO compradorDAO = new TableCompradorDAO();
	private TableProductoDAO productoDAO = new TableProductoDAO();
	private TableCarritoProductoDAO carritoProductoDAO = new TableCarritoProductoDAO();
	private TableTransaccionesDAO transaccionesDAO = new TableTransaccionesDAO();

	private Usuario usuarioLogueado;
	private int carritoActivoId = -1;

	public static final int ADMIN = 1;
	public static final int EMPLEADO = 2;
	public static final int COMPRADOR = 3;

	public Ctrl(VPrincipal vp, VloginForm vloginForm, VCarrito vca, VGestionEmp vgemp, VGestionProd vgprod,
			VGestionStock vgstk, VRegistrarse vr, VShop vsh, VTrans vtr, VCuenta vcuenta) {
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

		vp.cargarPanel(vlf);

		vlf.getRootPane().setDefaultButton(vlf.getBtnEntrar());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component src = (Component) e.getSource();
		String ac = e.getActionCommand();

		// System.out.println(src);
		// System.out.println(vloginForm.getBtnregister());

		if (src instanceof JMenuItem) {
			switch (ac) {
			case ConstantesBotones.CERRAR_SESION:
				cerrarSesion();
				break;
			case ConstantesBotones.GESTION_EMPLEADOS:
				vp.cargarPanel(vgemp);
				break;
			case ConstantesBotones.GESTION_PRODUCTOS:
				vp.cargarPanel(vgprod);
				vgprod.cargarCategorias(productoDAO.selectCategorias());
				if(productoDAO.selectProductos(null, null, null,false) != null) vgprod.cargarTabla(productoDAO.selectProductos(null, null, null,false));
				vgprod.hideDescripcion();
				break;
			case ConstantesBotones.GESTION_STOCK:
				vp.cargarPanel(vgstk);
				if(productoDAO.selectProductos(null, null, null, true) != null) vgstk.cargarTabla(productoDAO.selectProductos(null, null, null, false));
				vgstk.cargarCategorias(productoDAO.selectCategorias());
				break;
			case ConstantesBotones.VER_TRANSACCIONES:
				vp.cargarPanel(vtr);
				if(transaccionesDAO.selectTransacciones() != null) vtr.cargarTabla(transaccionesDAO.selectTransacciones());
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
		    	if(comp != null) { 
		    		vcuenta.cargarDatos(comp);
		    		}
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
			}
		}
	}

	private void acVCuenta(String ac) {
		switch (ac) {
	    case ConstantesBotones.MODIFICAR_COMPRADOR:
	        Comprador comp = vcuenta.obtenerDatosFormulario(usuarioLogueado.getUserId());
	        
	        if (comp != null) {
	            JOptionPane.showMessageDialog(vcuenta, usuarioDAO.updateComprador(comp),
	                    "Resultado", JOptionPane.INFORMATION_MESSAGE);
	            usuarioLogueado = usuarioDAO.login(comp.getNombre(), comp.getContrasenia());
	        }
	        break;
	    case ConstantesBotones.DARSE_DE_BAJA:
	    	int res = JOptionPane.showConfirmDialog(vp, "¿Seguro que desea darse de baja?",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (res == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(vcuenta, usuarioDAO.darDeBaja(usuarioLogueado.getUserId()),
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

	private void acVT(String ac) {
		switch (ac) {

		}
	}

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

	private void acVR(String ac) {
		switch (ac) {
		case ConstantesBotones.REGISTRARSE:
			if (vr.obtenerDatos() != null) {
				Comprador compr = vr.obtenerDatos();
				if (usuarioDAO.insertUsr(compr) > 0) {
					JOptionPane.showMessageDialog(vgemp, "Usuario insertado con éxito.", "Resultado",
							JOptionPane.INFORMATION_MESSAGE);

					vp.cargarPanel(vlf);
					vlf.setTxtUsuario(compr.getNombre());
					vlf.getRootPane().setDefaultButton(vlf.getBtnEntrar());
				} else {
					JOptionPane.showMessageDialog(vgemp, "Error al insertar el usuario.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(vr, "El teléfono es obligatorio.", "Error de datos",
						JOptionPane.ERROR_MESSAGE);
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
			if(emp != null) vgemp.cargarTabla(emp);
			break;
		case EMPLEADO:
			vp.crearMenuBase();
			vp.cargarPanel(vgstk);
			if(productoDAO.selectProductos(null, null, null, true) != null) vgstk.cargarTabla(productoDAO.selectProductos(null, null, null, false));
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
		    if (comp != null) vcuenta.cargarDatos(comp);
			break;
		default:
			vp.quitarMenu();
			break;
		}

		vp.revalidate();
		vp.repaint();
	}

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
		}else {
			int res = JOptionPane.showConfirmDialog(vp, "¿Seguro que desea cerrar sesion?",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
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

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object src = e.getSource();

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
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("over");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

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
			        vgstk.setBtnMenosEnabled(true);
			        if (vgstk.isDescripcionVisible()) {
			            vgstk.verDescripcion(vgstk.getProductoEnFila(fila).getDescripcion());
			        }
			    } else {
			        vgstk.setVerMasEnabled(false);
			        vgstk.setBtnMenosEnabled(false);
			        vgstk.hideDescripcion();
			    }
			}

		}
	}

}
