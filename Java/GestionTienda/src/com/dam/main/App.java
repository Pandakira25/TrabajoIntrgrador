package com.dam.main;

import javax.swing.SwingUtilities;

import com.dam.ctrl.Ctrl;
import com.dam.view.VCarrito;
import com.dam.view.VGestionEmp;
import com.dam.view.VGestionProd;
import com.dam.view.VGestionStock;
import com.dam.view.VPrincipal;
import com.dam.view.VRegistrarse;
import com.dam.view.VShop;
import com.dam.view.VTrans;
import com.dam.view.VloginForm;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			VPrincipal vp       = new VPrincipal();
			VloginForm vlf      = new VloginForm();
			VCarrito vca        = new VCarrito();
			VGestionEmp vgemp   = new VGestionEmp();
			VGestionProd vgprod = new VGestionProd();
			VGestionStock vgstk = new VGestionStock();
			VRegistrarse vr     = new VRegistrarse();
			VShop vsh           = new VShop();
			VTrans vtr          = new VTrans();

		Ctrl ctrl = new Ctrl(vp, vlf, vca, vgemp, vgprod, vgstk, vr, vsh, vtr);

		vp.setControlador(ctrl);
		vlf.setControlador(ctrl);
			vca.setControlador(ctrl);
			vgemp.setControlador(ctrl);
			vgprod.setControlador(ctrl);
			vgstk.setControlador(ctrl);
			vr.setControlador(ctrl);
			vsh.setControlador(ctrl);
			vtr.setControlador(ctrl);

			vp.hacerVisible();
		});
	}
}
