package com.dam.ctrl;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.dam.model.acessbd.TableCarritoDAO;
import com.dam.model.acessbd.TableCarritoProductoDAO;
import com.dam.model.acessbd.TableCompradorDAO;
import com.dam.model.acessbd.TableEmpleadoDAO;
import com.dam.model.acessbd.TableProductoDAO;
import com.dam.model.acessbd.TableTransaccionesDAO;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Producto;
import com.dam.model.pojos.Usuario;
import com.dam.view.VCarrito;
import com.dam.view.VGestionEmp;
import com.dam.view.VGestionProd;
import com.dam.view.VGestionStock;
import com.dam.view.VPrincipal;
import com.dam.view.VRegistrarse;
import com.dam.view.VShop;
import com.dam.view.VTrans;
import com.dam.view.VloginForm;

import com.dam.view.ConstantesBotones;

public class Ctrl implements ActionListener, MouseListener {

	private VPrincipal vp;
	
	private VloginForm vlf;
	private VCarrito vca;
	private VGestionEmp vgemp;
	private VGestionProd vgprod;
	private VGestionStock vgstk;
	private VRegistrarse vr;
	private VShop vsh;
	private VTrans vtr;

	private TableUsuarioDAO usuarioDAO = new TableUsuarioDAO();
	private TableCarritoDAO carritoDao = new TableCarritoDAO();
	private TableEmpleadoDAO empleadoDAO = new TableEmpleadoDAO();
	private TableCompradorDAO compradorDAO = new TableCompradorDAO();
	private TableProductoDAO productoDAO = new TableProductoDAO();
	private TableCarritoProductoDAO carritoProductoDAO = new TableCarritoProductoDAO();
	private TableTransaccionesDAO transaccionesDAO = new TableTransaccionesDAO();

	private Usuario usuarioLogueado;
	
	public static final int ADMIN = 1;
	public static final int EMPLEADO = 2;
	public static final int COMPRADOR = 3;

	public Ctrl(VPrincipal vp ,VloginForm vloginForm, VCarrito vca, VGestionEmp vgemp, VGestionProd vgprod, VGestionStock vgstk,
			VRegistrarse vr, VShop vsh, VTrans vtr) {
		this.vp = vp;
		this.vlf = vloginForm;
		this.vca = vca;
		this.vgemp = vgemp;
		this.vgprod = vgprod;
		this.vgstk = vgstk;
		this.vr = vr;
		this.vsh = vsh;
		this.vtr = vtr;
		
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
				break;
			case ConstantesBotones.VER_TRANSACCIONES:
				vp.cargarPanel(vtr);
				break;
			case ConstantesBotones.COMPRAR:
				vp.cargarPanel(vsh);
				break;
			case ConstantesBotones.CARRITO:
				vp.cargarPanel(vca);
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
				acVGS(ac);
				break;
			case VTrans.NAME:
				acVT(ac);
				break;
			case VCarrito.NAME:
				acVC(ac);
				break;
			case VShop.NAME:
				acVS(ac);
				break;
			}
		}
	}

	private void acVS(String ac) {
		switch (ac) {

		}
	}

	private void acVC(String ac) {
		switch (ac) {

		}
	}

	private void acVT(String ac) {
		switch (ac) {

		}
	}

	private void acVGS(String ac) {
		switch (ac) {

		}
	}

	private void acVGP(String ac) {
		switch (ac) {

		}
	}

	private void acVGE(String ac) {
		switch (ac) {

		}
	}

	private void acVR(String ac) {
		switch (ac) {
		case ConstantesBotones.REGISTRARSE:
			break;
		case ConstantesBotones.CANCELAR:
			int respuesta = JOptionPane.showConfirmDialog(vp, "¿Seguro que desea cancelar?",
					"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if(respuesta == JOptionPane.YES_OPTION) {
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
			JOptionPane.showMessageDialog(vlf, "Introduce usuario y contraseña.", "Login",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Usuario u = usuarioDAO.login(nombre, pwd);

		if (u == null) {
			JOptionPane.showMessageDialog(vp, "Usuario o contraseña incorrectos.", "Login",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!u.isActivo()) {
			JOptionPane.showMessageDialog(vp, "Esta cuenta está desactivada.", "Login",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		usuarioLogueado = u;
	
		int autorizacion = u.getAutorizacion();
		
		System.out.println(autorizacion);
		
		switch (autorizacion) {
		case ADMIN:
			vp.menuAdmin();
			vp.cargarPanel(vgemp);
			//vp.crearMenuBase();
			
			//Prueba tabla
			ArrayList<Producto> ps = new ArrayList<Producto>();
			ps.add(new Producto("prueba", "", 2, 2, ""));
			
			vgprod.cargarTabla(ps);
			
			break;
		case EMPLEADO:
			vp.crearMenuBase();
			vp.cargarPanel(vgstk);
			break;
		case COMPRADOR:
			vp.menuComprador();
			vp.cargarPanel(vsh);
			
			//Prueba tabla
			ps = new ArrayList<Producto>();
			ps.add(new Producto("prueba", "", 2, 2, ""));
			
			vsh.cargarTabla(ps);
			break;
		default:
			vp.quitarMenu();
			break;
		}
		
		vp.revalidate();
		vp.repaint();
	}

	private void cerrarSesion() {
		usuarioLogueado = null;
		vp.quitarMenu();

		vlf.getTxtUsuario().setText("");
		vlf.getTxtContrasenia().setText("");
		vp.cargarPanel(vlf);
	}

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("tablita");
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

}
