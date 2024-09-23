package adoptask.modelo;

import java.time.LocalDateTime;

public class Actividad {

	private LocalDateTime fecha;
	private String nick;
	private String accion;

	public Actividad() {
	}

	public Actividad(String nick, String accion) {
		this.nick = nick;
		this.accion = accion;
		fecha = LocalDateTime.now();
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
