package adoptask.modelo;

import java.time.LocalDateTime;

public class Actividad {

	private LocalDateTime fecha;
	private String idUsuario;
	private String accion;

	public Actividad() {
	}

	public Actividad(String idUsuario, String accion) {
		this.idUsuario = idUsuario;
		this.accion = accion;
		fecha = LocalDateTime.now();
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
