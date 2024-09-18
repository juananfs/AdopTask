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
	private String nombre;
	private String nif;
	private String email;
	private String descripcion;
	private String ubicacion;
	private String logotipo;
	private String web;
	private String idAdmin;
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

	public Protectora(String nombre, String nif, String email, String descripcion, String ubicacion, String logotipo,
			String web, String idAdmin) {
		this();
		this.nombre = nombre;
		this.nif = nif;
		this.email = email;
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
		this.logotipo = logotipo;
		this.web = web;
		this.idAdmin = idAdmin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getLogotipo() {
		return logotipo;
	}

	public void setLogotipo(String logotipo) {
		this.logotipo = logotipo;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
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
