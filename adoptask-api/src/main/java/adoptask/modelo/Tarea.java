package adoptask.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tareas")
@CompoundIndex(name = "protectora_estado_prioridad_idx", def = "{'idProtectora': 1, 'estado': 1, 'prioridad': 1}")
public class Tarea {

	@Id
	private String id;
	@Indexed
	private String idProtectora;
	private String titulo;
	private String descripcion;
	private int prioridad;
	private EstadoTarea estado;
	private String encargado;

	public Tarea() {
	}

	public Tarea(String idProtectora, String titulo, String descripcion, PrioridadTarea prioridad) {
		this.idProtectora = idProtectora;
		this.titulo = titulo;
		this.descripcion = descripcion;
		setPrioridad(prioridad);
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
		return PrioridadTarea.values()[this.prioridad];
	}

	public void setPrioridad(PrioridadTarea prioridad) {
		this.prioridad = prioridad.ordinal();
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
