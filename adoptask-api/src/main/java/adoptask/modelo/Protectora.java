package adoptask.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
	private List<Documento> documentos;

	public Protectora() {
		voluntarios = new LinkedList<>();
		documentos = new LinkedList<>();
	}

	public Protectora(String idAdmin, String nombre, DatosContacto contacto, String descripcion, String logotipo) {
		this();
		this.idAdmin = idAdmin;
		this.nombre = nombre;
		this.contacto = contacto;
		this.descripcion = descripcion;
		this.logotipo = logotipo;
		voluntarios.add(idAdmin);
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

	public boolean isAdmin(String idUsuario) {
		return idUsuario.equals(idAdmin);
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

	public boolean tieneAcceso(String idUsuario) {
		return voluntarios.contains(idUsuario);
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

	public Optional<Documento> getDocumento(String id) {
		return documentos.stream().filter(archivo -> archivo.getId().equals(id)).findFirst();
	}

	public void removeDocumento(Documento documento) {
		documentos.remove(documento);
	}

	public String getNif() {
		return contacto.getNif();
	}

	public void setNif(String nif) {
		contacto.setNif(nif);
	}

	public String getEmail() {
		return contacto.getEmail();
	}

	public void setEmail(String email) {
		contacto.setEmail(email);
	}

	public String getUbicacion() {
		return contacto.getUbicacion();
	}

	public void setUbicacion(String ubicacion) {
		contacto.setUbicacion(ubicacion);
	}

	public String getTelefono() {
		return contacto.getTelefono();
	}

	public void setTelefono(String telefono) {
		contacto.setTelefono(telefono);
	}

	public String getWeb() {
		return contacto.getWeb();
	}

	public void setWeb(String web) {
		contacto.setWeb(web);
	}

}
