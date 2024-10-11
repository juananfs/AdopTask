package adoptask.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tareas")
public class Tarea {

	@Id
	private String id;
	@Indexed
	private String idProtectora;
	private String titulo;
	private String descripcion;
	private PrioridadTarea prioridad;
	private EstadoTarea estado;
	private String encargado;

	public Tarea() {
	}

	public Tarea(String idProtectora, String titulo, String descripcion, PrioridadTarea prioridad) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		estado = EstadoTarea.PENDIENTE;
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

	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}

}
