package adoptask.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animales")
public class Animal {

	@Id
	private String id;
	private String idProtectora;
	private DatosAnimal datos;
	private EstadoAnimal estado;
	private LocalDate fechaEntrada;
	private String descripcion;
	private List<CampoAdicional> camposAdicionales;
	private List<String> imagenes;
	private List<Documento> documentos;

	public static class Builder {
		private String idProtectora;
		private DatosAnimal datos;
		private EstadoAnimal estado;
		private LocalDate fechaEntrada;
		private String imagen;
		private String descripcion;

		public Builder(String idProtectora, DatosAnimal datos, EstadoAnimal estado, LocalDate fechaEntrada,
				String imagen) {
			this.idProtectora = idProtectora;
			this.datos = datos;
			this.estado = estado;
			this.fechaEntrada = fechaEntrada;
			this.imagen = imagen;
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
		idProtectora = builder.idProtectora;
		datos = builder.datos;
		estado = builder.estado;
		fechaEntrada = builder.fechaEntrada;
		descripcion = builder.descripcion;
		imagenes.add(builder.imagen);
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

	public List<CampoAdicional> getCamposAdicionales() {
		return new ArrayList<>(camposAdicionales);
	}

	public void setCamposAdicionales(List<CampoAdicional> camposAdicionales) {
		this.camposAdicionales = camposAdicionales;
	}

	public void addCampo(CampoAdicional campo) {
		camposAdicionales.add(campo);
	}

	public void removeCampo(CampoAdicional campo) {
		camposAdicionales.remove(campo);
	}

	public List<String> getImagenes() {
		return new ArrayList<>(imagenes);
	}

	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}

	public void addImagen(String imagen) {
		imagenes.add(imagen);
	}

	public void removeImagen(String imagen) {
		imagenes.remove(imagen);
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

	public int getEdad() {
		return datos.getEdad();
	}

}
