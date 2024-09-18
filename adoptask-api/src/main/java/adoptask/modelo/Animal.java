package adoptask.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animales")
public class Animal {

	@Id
	private String id;
	private String nombre;
	private String raza;
	private String descripcion;
	private LocalDate fechaEntrada;
	private LocalDate fechaNacimiento;
	private int peso;
	private SexoAnimal sexo;
	private CategoriaAnimal categoria;
	private EstadoAnimal estado;
	private String idProtectora;
	private List<String> imagenes;
	private List<CampoAdicional> camposAdicionales;
	private List<Documento> documentos;

	public Animal() {
		imagenes = new LinkedList<>();
		camposAdicionales = new LinkedList<>();
		documentos = new LinkedList<>();
	}

	public Animal(String nombre, String raza, String descripcion, LocalDate fechaEntrada, LocalDate fechaNacimiento,
			int peso, SexoAnimal sexo, CategoriaAnimal categoria, EstadoAnimal estado, String idProtectora) {
		this();
		this.nombre = nombre;
		this.raza = raza;
		this.descripcion = descripcion;
		this.fechaEntrada = fechaEntrada;
		this.fechaNacimiento = fechaNacimiento;
		this.peso = peso;
		this.sexo = sexo;
		this.categoria = categoria;
		this.estado = estado;
		this.idProtectora = idProtectora;
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

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public SexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(SexoAnimal sexo) {
		this.sexo = sexo;
	}

	public CategoriaAnimal getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaAnimal categoria) {
		this.categoria = categoria;
	}

	public EstadoAnimal getEstado() {
		return estado;
	}

	public void setEstado(EstadoAnimal estado) {
		this.estado = estado;
	}

	public String getIdProtectora() {
		return idProtectora;
	}

	public void setIdProtectora(String idProtectora) {
		this.idProtectora = idProtectora;
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
		return Period.between(fechaNacimiento, LocalDate.now()).getYears();
	}

}
