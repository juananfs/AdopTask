package adoptask.dto;

import adoptask.modelo.EstadoTarea;
import adoptask.modelo.PrioridadTarea;

public class TareaDto {

	private String id;
	private String idProtectora;
	private String idUsuario;
	private String titulo;
	private String descripcion;
	private PrioridadTarea prioridad;
	private EstadoTarea estado;
	private String idEncargado;
	private String nickEncargado;

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

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public PrioridadTarea getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(PrioridadTarea prioridad) {
		this.prioridad = prioridad;
	}

	public EstadoTarea getEstado() {
		return estado;
	}

	public void setEstado(EstadoTarea estado) {
		this.estado = estado;
	}

	public String getIdEncargado() {
		return idEncargado;
	}

	public void setIdEncargado(String idEncargado) {
		this.idEncargado = idEncargado;
	}

	public String getNickEncargado() {
		return nickEncargado;
	}

	public void setNickEncargado(String nickEncargado) {
		this.nickEncargado = nickEncargado;
	}

}
