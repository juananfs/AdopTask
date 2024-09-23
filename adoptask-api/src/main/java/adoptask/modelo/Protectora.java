package adoptask.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "protectoras")
public class Protectora {

	@Id
	private String id;
	private String idAdmin;
	private String nombre;
	private DatosContacto contacto;
	private String descripcion;
	private String logotipo;
	private List<String> voluntarios;
	private List<String> animales;
	private List<Documento> documentos;
	private List<Actividad> historial;
	private List<Tarea> tareas;

	public Protectora() {
		voluntarios = new LinkedList<>();
		animales = new LinkedList<>();
		documentos = new LinkedList<>();
		historial = new LinkedList<>();
		tareas = new LinkedList<>();
	}

	public Protectora(String idAdmin, String nombre, DatosContacto contacto, String descripcion, String logotipo) {
		this();
		this.idAdmin = idAdmin;
		this.nombre = nombre;
		this.contacto = contacto;
		this.descripcion = descripcion;
		this.logotipo = logotipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DatosContacto getContacto() {
		return contacto;
	}

	public void setContacto(DatosContacto contacto) {
		this.contacto = contacto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLogotipo() {
		return logotipo;
	}

	public void setLogotipo(String logotipo) {
		this.logotipo = logotipo;
	}

	public List<String> getVoluntarios() {
		return new ArrayList<>(voluntarios);
	}

	public void setVoluntarios(List<String> voluntarios) {
		this.voluntarios = voluntarios;
	}

	public void addVoluntario(String idVoluntario) {
		voluntarios.add(idVoluntario);
	}

	public void removeVoluntario(String idVoluntario) {
		voluntarios.remove(idVoluntario);
	}

	public List<String> getAnimales() {
		return new ArrayList<>(animales);
	}

	public void setAnimales(List<String> animales) {
		this.animales = animales;
	}

	public void addAnimal(String idAnimal) {
		animales.add(idAnimal);
	}

	public void removeAnimal(String idAnimal) {
		animales.remove(idAnimal);
	}

	public List<Documento> getDocumentos() {
		return new ArrayList<>(documentos);
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public void addDocumento(Documento documento) {
		documentos.add(documento);
	}

	public void removeDocumento(Documento documento) {
		documentos.remove(documento);
	}

	public List<Actividad> getHistorial() {
		return new ArrayList<>(historial);
	}

	public void setHistorial(List<Actividad> historial) {
		this.historial = historial;
	}

	public void addActividad(Actividad actividad) {
		historial.add(actividad);
	}

	public List<Tarea> getTareas() {
		return new ArrayList<>(tareas);
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public void addTarea(Tarea tarea) {
		tareas.add(tarea);
	}

	public void removeTarea(Tarea tarea) {
		tareas.remove(tarea);
	}

}
