package com.dam.model.pojos;

public class Transacciones {

	private int comptradorId;
	private int empleadoId;
	private int carritoId;
	private String fecha;
	private double importe;
	
	public Transacciones(int comptradorId, int empleadoId, int carritoId, String fecha, double importe) {
		this.comptradorId = comptradorId;
		this.empleadoId = empleadoId;
		this.carritoId = carritoId;
		this.fecha = fecha;
		this.importe = importe;
	}
	
	
	@Override
	public String toString() {
		return "Transacciones [comptradorId=" + comptradorId + ", empleadoId=" + empleadoId + ", carritoId=" + carritoId
				+ ", fecha=" + fecha + ", importe=" + importe + "]";
	}
}
