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

	public Ctrl() {
		usuarioDAO = new TableUsuarioDAO();
		vloginForm = new VloginForm();
		vloginForm.setControlador(this);
		vloginForm.hacerVisible();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == vloginForm.getBtnEntrar()) {
			entrar();
		} else if (e.getSource() == vloginForm.getBtnregister()) {
			registrarse();
		} else if (e.getSource() == vloginForm.getMntmCerrarSesion()) {
			cerrarSesion();
		} else if (e.getSource() == vloginForm.getMntmGestionEmp()) {
			gestionEmpresa();
		} else if (e.getSource() == vloginForm.getMntmGestionProd()) {
			gestionProductos();
		} else if (e.getSource() == vloginForm.getMntmTransacciones()) {
			transacciones();
		} else if (e.getSource() == vloginForm.getMntmGestionStock()) {
			gestionStock();
		} else if (e.getSource() == vloginForm.getMntmShop()) {
			shop();
		} else if (e.getSource() == vloginForm.getMntmCarrito()) {
			// TODO: cargarPanel VCarrito
			carrito();
		}
	}

	private void carrito() {
		vloginForm.cargarPanel(vca);
		//TODO: botones de la ventana
	}

	private void shop() {
		vloginForm.cargarPanel(vsh);
		//TODO: botones de la ventana
	}

	private void gestionStock() {
		vloginForm.cargarPanel(vgstk);
		//TODO: botones de la ventana
	}

	private void transacciones() {
		vloginForm.cargarPanel(vtr);
		//TODO: botones de la ventana
	}

	private void gestionProductos() {
		vloginForm.cargarPanel(vgprod);
		//TODO: botones de la ventana
	}

	private void gestionEmpresa() {
		vloginForm.cargarPanel(vgemp);
		//TODO: botones de la ventana
	}
	
	private void registrarse() {
		vloginForm.cargarPanel(vr);
		//TODO: botones de la ventana
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
