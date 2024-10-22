package adoptask.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animales")
@CompoundIndex(name = "estado_categoria_sexo_protectora_idx", def = "{'estado': 1, 'datos.categoria': 1, 'datos.sexo': 1, 'idProtectora': 1, 'fechaPublicacion': -1}")
public class Animal {

	public static final int MAX_IMAGENES = 5;
	public static final int MAX_DOCUMENTOS = 5;

	@Id
	private String id;
	@Indexed
	private String idProtectora;
	private String idPortada;
	private DatosAnimal datos;
	@Indexed
	private EstadoAnimal estado;
	private LocalDate fechaEntrada;
	private String descripcion;
	private LocalDate fechaPublicacion;
	private int likes;
	private List<CampoAdicional> camposAdicionales;
	private List<Archivo> imagenes;
	private List<Documento> documentos;

	public static class Builder {
		private String idProtectora;
		private DatosAnimal datos;
		private EstadoAnimal estado;
		private LocalDate fechaEntrada;
		private String descripcion;

		public Builder(String idProtectora, DatosAnimal datos, EstadoAnimal estado, LocalDate fechaEntrada) {
			this.idProtectora = idProtectora;
			this.datos = datos;
			this.estado = estado;
			this.fechaEntrada = fechaEntrada;
		}

		public Builder descripcion(String descripcion) {
			this.descripcion = descripcion;
			return this;
		}

		public Animal build() {
			return new Animal(this);
		}
	}

	public Animal() {
		imagenes = new LinkedList<>();
		camposAdicionales = new LinkedList<>();
		documentos = new LinkedList<>();
	}

	public Animal(Builder builder) {
		this();
		Archivo portada = new Archivo("");
		idProtectora = builder.idProtectora;
		idPortada = portada.getId();
		datos = builder.datos;
		estado = builder.estado;
		fechaEntrada = builder.fechaEntrada;
		descripcion = builder.descripcion;
		imagenes.add(portada);
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

	public String getIdPortada() {
		return idPortada;
	}

	public void setIdPortada(String idPortada) {
		this.idPortada = idPortada;
	}

	public String getRutaPortada() {
		Optional<Archivo> portada = getImagen(idPortada);
		if (portada.isPresent())
			return portada.get().getRuta();
		return null;
	}

	public void setRutaPortada(String rutaPortada) {
		Optional<Archivo> portada = getImagen(idPortada);
		if (portada.isPresent())
			portada.get().setRuta(rutaPortada);
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

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
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

	public List<Archivo> getImagenes() {
		return new ArrayList<>(imagenes);
	}

	public void setImagenes(List<Archivo> imagenes) {
		this.imagenes = imagenes;
	}

	public Optional<Archivo> getImagen(String id) {
		return imagenes.stream().filter(archivo -> archivo.getId().equals(id)).findFirst();
	}

	public boolean addImagen(Archivo imagen) {
		if (imagenes.size() < MAX_IMAGENES) {
			imagenes.add(imagen);
			return true;
		}
		return false;
	}

	public void removeImagen(String id) {
		Optional<Archivo> imagen = getImagen(id);
		if (imagen.isPresent())
			imagenes.remove(imagen.get());
	}

	public List<Documento> getDocumentos() {
		return new ArrayList<>(documentos);
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Optional<Documento> getDocumento(String id) {
		return documentos.stream().filter(archivo -> archivo.getId().equals(id)).findFirst();
	}

	public boolean addDocumento(Documento documento) {
		if (documentos.size() < MAX_DOCUMENTOS) {
			documentos.add(documento);
			return true;
		}
		return false;
	}

	public void removeDocumento(String id) {
		Optional<Documento> documento = getDocumento(id);
		if (documento.isPresent())
			documentos.remove(documento.get());
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

	public int getPeso() {
		return datos.getPeso();
	}

	public void setPeso(int peso) {
		datos.setPeso(peso);
	}

	public int getEdad() {
		return datos.getEdad();
	}

}
