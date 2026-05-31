package com.dam.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.dam.model.acessbd.TableCarritoDAO;
import com.dam.model.acessbd.TableCarritoProductoDAO;
import com.dam.model.acessbd.TableCompradorDAO;
import com.dam.model.acessbd.TableEmpleadoDAO;
import com.dam.model.acessbd.TableProductoDAO;
import com.dam.model.acessbd.TableTransaccionesDAO;
import com.dam.model.acessbd.TableUsuarioDAO;
import com.dam.model.pojos.Usuario;
import com.dam.view.VCarrito;
import com.dam.view.VGestionEmp;
import com.dam.view.VGestionProd;
import com.dam.view.VGestionStock;
import com.dam.view.VRegistrarse;
import com.dam.view.VShop;
import com.dam.view.VTrans;
import com.dam.view.VloginForm;

public class Ctrl implements ActionListener {

	private VloginForm vloginForm;
	private VCarrito vca;
	private VGestionEmp vgemp;
	private VGestionProd vgprod;
	private VGestionStock vgstk;
	private VRegistrarse vr;
	private VShop vsh;
	private VTrans vtr;
	
	private TableUsuarioDAO usuarioDAO;
	private TableCarritoDAO carritoDao;
	private TableEmpleadoDAO empleadoDAO;
	private TableCompradorDAO compradorDAO;
	private TableProductoDAO productoDAO;
	private TableCarritoProductoDAO carritoProductoDAO;
	private TableTransaccionesDAO transaccionesDAO;
	
	private Usuario usuarioLogueado;

	public Ctrl(VloginForm vloginForm, VCarrito vca, VGestionEmp vgemp, VGestionProd vgprod, VGestionStock vgstk, VRegistrarse vr, VShop vsh, VTrans vtr) {
		usuarioDAO = new TableUsuarioDAO();
		this.vloginForm = vloginForm;
		this.vca = vca;
		this.vgemp = vgemp;
		this.vgprod = vgprod;
		this.vgstk = vgstk;
		this.vr = vr;
		this.vsh = vsh;
		this.vtr = vtr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		System.out.println(src);
		System.out.println(vloginForm.getMntmCerrarSesion());
		if (src == vloginForm.getBtnEntrar()) {
			entrar();
		} else if (src == vloginForm.getBtnregister()) {
			vloginForm.cargarPanel(vr);
		} else if (src == vloginForm.getMntmCerrarSesion()) {
			cerrarSesion();
		} else if (src == vloginForm.getMntmGestionEmp()) {
			vloginForm.cargarPanel(vgemp);
		} else if (src == vloginForm.getMntmGestionProd()) {
			vloginForm.cargarPanel(vgprod);
		} else if (src == vloginForm.getMntmTransacciones()) {
			vloginForm.cargarPanel(vtr);
		} else if (src == vloginForm.getMntmGestionStock()) {
			vloginForm.cargarPanel(vgstk);
		} else if (src == vloginForm.getMntmShop()) {
			vloginForm.cargarPanel(vsh);
		} else if (src == vloginForm.getMntmCarrito()) {
			vloginForm.cargarPanel(vca);
		}
	}


	private void entrar() {
		String nombre = vloginForm.getTxtuser().getText().trim();
		String pwd = new String(vloginForm.getTxtpwd().getPassword());

		if (nombre.isEmpty() || pwd.isEmpty()) {
			JOptionPane.showMessageDialog(vloginForm, "Introduce usuario y contraseña.", "Login",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Usuario u = usuarioDAO.login(nombre, pwd);

		if (u == null) {
			JOptionPane.showMessageDialog(vloginForm, "Usuario o contraseña incorrectos.", "Login",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!u.isActivo()) {
			JOptionPane.showMessageDialog(vloginForm, "Esta cuenta está desactivada.", "Login",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		usuarioLogueado = u;
		vloginForm.autorizacionesMenu(u.getAutorizacion());
	}
	
	private void cerrarSesion() {
		usuarioLogueado = null;
		vloginForm.quitarMenu();
		vloginForm.getTxtuser().setText("");
		vloginForm.getTxtpwd().setText("");
	}

	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

}
