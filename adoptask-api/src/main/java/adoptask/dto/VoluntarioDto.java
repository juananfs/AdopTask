package adoptask.dto;

import java.util.List;

import adoptask.modelo.TipoPermiso;

public class VoluntarioDto {

	private String id;
	private String nick;
	private String nombre;
	private String foto;
	private List<TipoPermiso> permisos;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<TipoPermiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<TipoPermiso> permisos) {
		this.permisos = permisos;
	}

}
