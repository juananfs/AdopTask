package adoptask.dto;

import java.time.LocalDate;
import java.util.List;

import adoptask.modelo.Archivo;
import adoptask.modelo.CampoAdicional;
import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.Documento;
import adoptask.modelo.EstadoAnimal;
import adoptask.modelo.SexoAnimal;

public class AnimalDto {

	private String id;
	private String idProtectora;
	private Archivo portada;
	private String nombre;
	private CategoriaAnimal categoria;
	private String raza;
	private SexoAnimal sexo;
	private LocalDate fechaNacimiento;
	private Integer peso;
	private EstadoAnimal estado;
	private LocalDate fechaEntrada;
	private String descripcion;
	private List<CampoAdicional> camposAdicionales;
	private List<Archivo> imagenes;
	private List<Documento> documentos;

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

	public Archivo getPortada() {
		return portada;
	}

	public void setPortada(Archivo portada) {
		this.portada = portada;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CategoriaAnimal getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaAnimal categoria) {
		this.categoria = categoria;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public SexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(SexoAnimal sexo) {
		this.sexo = sexo;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
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
		return camposAdicionales;
	}

	public void setCamposAdicionales(List<CampoAdicional> camposAdicionales) {
		this.camposAdicionales = camposAdicionales;
	}

	public List<Archivo> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Archivo> imagenes) {
		this.imagenes = imagenes;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public String getRutaPortada() {
		return portada.getRuta();
	}

}
