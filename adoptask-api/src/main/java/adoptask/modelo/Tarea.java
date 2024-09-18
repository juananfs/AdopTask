package adoptask.modelo;

public class Tarea {

	private String titulo;
	private String descripcion;
	private PrioridadTarea prioridad;
	private EstadoTarea estado;
	private String idEncargado;

	public Tarea() {
	}

	public Tarea(String titulo, String descripcion, PrioridadTarea prioridad, String idEncargado) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.idEncargado = idEncargado;
		estado = EstadoTarea.PENDIENTE;
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

}
