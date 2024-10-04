package adoptask.modelo;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actividades")
@CompoundIndex(name = "protectora_fecha_idx", def = "{'idProtectora' : 1, 'fecha' : -1}")
public class Actividad {

	@Id
	private String id;
	private String idProtectora;
	private LocalDateTime fecha;
	private String nick;
	private String accion;

	public Actividad() {
	}

	public Actividad(String idProtectora, String nick, String accion) {
		this.idProtectora = idProtectora;
		this.nick = nick;
		this.accion = accion;
		fecha = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdProtectora() {
		return idProtectora;
	}

	public void setIdProtectora(String idProtectora) {
		this.idProtectora = idProtectora;
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
