package adoptask.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animales")
@CompoundIndex(name = "estado_categoria_protectora_idx", def = "{'estado': 1, 'datos.categoria': 1, 'idProtectora': 1}")
@CompoundIndex(name = "estado_categoria_sexo_protectora_idx", def = "{'estado': 1, 'datos.categoria': 1, 'datos.sexo': 1, 'idProtectora': 1, 'fechaPublicacion': -1}")
public class Animal {

	public static final int MAX_IMAGENES = 5;
	public static final int MAX_DOCUMENTOS = 5;

	@Id
	private String id;
	@Indexed
	private String idProtectora;
	private String portada;
	private DatosAnimal datos;
	@Indexed
	private EstadoAnimal estado;
	private LocalDate fechaEntrada;
	private String descripcion;
	private LocalDateTime fechaPublicacion;
	private int likes;
	private List<CampoAdicional> camposAdicionales;
	private List<String> imagenes;
	private List<Documento> documentos;

	public Animal() {
		imagenes = new LinkedList<>();
		camposAdicionales = new LinkedList<>();
		documentos = new LinkedList<>();
	}

	public Animal(String idProtectora, DatosAnimal datos, EstadoAnimal estado, LocalDate fechaEntrada,
			String descripcion) {
		this();
		this.idProtectora = idProtectora;
		this.datos = datos;
		this.estado = estado;
		this.fechaEntrada = fechaEntrada;
		this.descripcion = descripcion;
		if (isEnAdopcion())
			fechaPublicacion = LocalDateTime.now();
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

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public DatosAnimal getDatos() {
		return datos;
	}

	public void setDatos(DatosAnimal datos) {
		this.datos = datos;
	}

	public EstadoAnimal getEstado() {
		return estado;
	}

	public void setEstado(EstadoAnimal estado) {
		this.estado = estado;
	}

	public boolean isEnAdopcion() {
		return estado == EstadoAnimal.EN_ADOPCION;
	}

	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void addLike(String idUsuario) {
		likes++;
	}

	public void removeLike(String idUsuario) {
		likes--;
	}

	public List<CampoAdicional> getCamposAdicionales() {
		return new ArrayList<>(camposAdicionales);
	}

	public void setCamposAdicionales(List<CampoAdicional> camposAdicionales) {
		this.camposAdicionales = camposAdicionales;
	}

	public List<String> getImagenes() {
		return new ArrayList<>(imagenes);
	}

	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}

	public boolean containsImagen(String imagen) {
		return imagenes.contains(imagen);
	}

	public boolean addImagen(String imagen) {
		if (imagenes.size() < MAX_IMAGENES) {
			imagenes.add(imagen);
			return true;
		}
		return false;
	}

	public boolean removeImagen(String imagen) {
		return imagenes.remove(imagen);
	}

	public List<Documento> getDocumentos() {
		return new ArrayList<>(documentos);
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public boolean addDocumento(Documento documento) {
		if (documentos.size() < MAX_DOCUMENTOS) {
			documentos.add(documento);
			return true;
		}
		return false;
	}

	public boolean removeDocumento(String nombre) {
		Optional<Documento> documento = documentos.stream().filter(archivo -> archivo.getNombre().equals(nombre))
				.findFirst();
		if (documento.isPresent())
			return documentos.remove(documento.get());
		return false;
	}

	public String getNombre() {
		return datos.getNombre();
	}

	public void setNombre(String nombre) {
		datos.setNombre(nombre);
	}

	public CategoriaAnimal getCategoria() {
		return datos.getCategoria();
	}

	public void setCategoria(CategoriaAnimal categoria) {
		datos.setCategoria(categoria);
	}

	public String getRaza() {
		return datos.getRaza();
	}

	public void setRaza(String raza) {
		datos.setRaza(raza);
	}

	public SexoAnimal getSexo() {
		return datos.getSexo();
	}

	public void setSexo(SexoAnimal sexo) {
		datos.setSexo(sexo);
	}

	public LocalDate getFechaNacimiento() {
		return datos.getFechaNacimiento();
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		datos.setFechaNacimiento(fechaNacimiento);
	}

	public Integer getPeso() {
		return datos.getPeso();
	}

	public void setPeso(Integer peso) {
		datos.setPeso(peso);
	}

	public Integer getEdad() {
		return datos.getEdad();
	}

}
