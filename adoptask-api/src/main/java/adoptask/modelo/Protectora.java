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
	private Usuario admin;
	private List<Usuario> voluntarios;
	private List<Publicacion> publicaciones;
	private List<Animal> animales;
	private List<Documento> documentos;
	private List<Actividad> historial;
	private List<Tarea> tareas;

	public Protectora() {
		voluntarios = new LinkedList<>();
		publicaciones = new LinkedList<>();
		animales = new LinkedList<>();
		documentos = new LinkedList<>();
		historial = new LinkedList<>();
		tareas = new LinkedList<>();
	}

	public Protectora(String nombre, String nif, String email, String descripcion, String ubicacion, String logotipo,
			String web, Usuario admin) {
		this();
		this.nombre = nombre;
		this.nif = nif;
		this.email = email;
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
		this.logotipo = logotipo;
		this.web = web;
		this.admin = admin;
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

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public List<Usuario> getVoluntarios() {
		return new ArrayList<>(voluntarios);
	}

	public void setVoluntarios(List<Usuario> voluntarios) {
		this.voluntarios = voluntarios;
	}

	public void add(Usuario voluntario) {
		voluntarios.add(voluntario);
	}

	public void remove(Usuario voluntario) {
		voluntarios.remove(voluntario);
	}

	public List<Publicacion> getPublicaciones() {
		return new ArrayList<>(publicaciones);
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	public void add(Publicacion publicacion) {
		publicaciones.add(publicacion);
	}

	public void remove(Publicacion publicacion) {
		publicaciones.remove(publicacion);
	}

	public List<Animal> getAnimales() {
		return new ArrayList<>(animales);
	}

	public void setAnimales(List<Animal> animales) {
		this.animales = animales;
	}

	public void add(Animal animal) {
		animales.add(animal);
	}

	public void remove(Animal animal) {
		animales.remove(animal);
	}

	public List<Documento> getDocumentos() {
		return new ArrayList<>(documentos);
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public void add(Documento documento) {
		documentos.add(documento);
	}

	public void remove(Documento documento) {
		documentos.remove(documento);
	}

	public List<Actividad> getHistorial() {
		return new ArrayList<>(historial);
	}

	public void setHistorial(List<Actividad> historial) {
		this.historial = historial;
	}

	public void add(Actividad actividad) {
		historial.add(actividad);
	}

	public List<Tarea> getTareas() {
		return new ArrayList<>(tareas);
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public void add(Tarea tarea) {
		tareas.add(tarea);
	}

	public void remove(Tarea tarea) {
		tareas.remove(tarea);
	}

}
