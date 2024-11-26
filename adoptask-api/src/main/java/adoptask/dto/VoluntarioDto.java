package adoptask.dto;

import java.util.List;

import adoptask.modelo.TipoPermiso;

public class VoluntarioDto {

	private String id;
	private String idProtectora;
	private String nombreProtectora;
	private String nick;
	private String nombre;
	private String foto;
	private boolean admin;
	private List<TipoPermiso> permisos;

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

	public String getNombreProtectora() {
		return nombreProtectora;
	}

	public void setNombreProtectora(String nombreProtectora) {
		this.nombreProtectora = nombreProtectora;
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<TipoPermiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<TipoPermiso> permisos) {
		this.permisos = permisos;
	}

	public boolean tienePermiso(TipoPermiso permiso) {
		return permisos.contains(permiso);
	}

}
