package com.dam.model.acessbd;

public class TableEmpleadoDAO {
	
	private AccessDBProp acc;
	
	private static final String COL_EMPLEADO_ID = "empleado_id";
	private static final String COL_N_SEG_SOCIAL = "n_seg_social";
	private static final String COL_IBAN = "iban";
	
	public TableEmpleadoDAO() {
		acc = new AccessDBProp();
	}
}
