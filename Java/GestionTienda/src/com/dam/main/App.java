package com.dam.main;

import java.awt.EventQueue;
import java.io.File;

import com.dam.ctrl.Ctrl;
import com.dam.model.acessbd.AccessDBProp;
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

public class App {

	public static void main(String[] args) {
		AccessDBProp acc = new AccessDBProp();
		File dbFile = new File("DB/tiendaFnac.db");
		if (!dbFile.exists()) {
		    acc.ejecutarScript("DB/init.sql");
		}
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				VPrincipal vp = new VPrincipal();
				VloginForm vlf = new VloginForm();
				VCarrito vca = new VCarrito();
				VGestionProd vgprod = new VGestionProd();
				VGestionStock vgstk = new VGestionStock();
				VGestionEmp vgemp = new VGestionEmp();
				VRegistrarse vr = new VRegistrarse();
				VShop vsh = new VShop();
				VTrans vtr = new VTrans();
				VCuenta vcuenta = new VCuenta();
				VGestionUsuarios vgusr = new VGestionUsuarios();
				
				Ctrl c = new Ctrl(vp,vlf, vca, vgemp, vgprod, vgstk, vr, vsh, vtr, vcuenta, vgusr);
				
				vp.setControlador(c);
				vlf.setControlador(c);
				vca.setControlador(c);
				vgprod.setControlador(c);
				vgstk.setControlador(c);
				vgemp.setControlador(c);
				vr.setControlador(c);
				vsh.setControlador(c);
				vtr.setControlador(c);
				vcuenta.setControlador(c);
				vgusr.setControlador(c);
				
				vp.hacerVisible();
				
			}
		});
	}
}
