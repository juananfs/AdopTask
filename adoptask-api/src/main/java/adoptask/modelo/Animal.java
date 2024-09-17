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
	private String nombre;
	private String raza;
	private String descripcion;
	private LocalDate fechaEntrada;
	private LocalDate fechaNacimiento;
	private int peso;
	private SexoAnimal sexo;
	private CategoriaAnimal categoria;
	private EstadoAnimal estado;
	private Protectora protectora;
	private List<String> imagenes;
	private List<CampoAdicional> campos;
	private List<Documento> documentos;

	public Animal() {
		imagenes = new LinkedList<>();
		campos = new LinkedList<>();
		documentos = new LinkedList<>();
	}

	public Animal(String nombre, String raza, String descripcion, LocalDate fechaEntrada, LocalDate fechaNacimiento,
			int peso, SexoAnimal sexo, CategoriaAnimal categoria, EstadoAnimal estado, Protectora protectora) {
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
		this.protectora = protectora;
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

	public Protectora getProtectora() {
		return protectora;
	}

	public void setProtectora(Protectora protectora) {
		this.protectora = protectora;
	}

	public List<String> getImagenes() {
		return new ArrayList<>(imagenes);
	}

	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}

	public void add(String imagen) {
		imagenes.add(imagen);
	}

	public void remove(String imagen) {
		imagenes.remove(imagen);
	}

	public List<CampoAdicional> getCampos() {
		return new ArrayList<>(campos);
	}

	public void setCampos(List<CampoAdicional> campos) {
		this.campos = campos;
	}

	public void add(CampoAdicional campo) {
		campos.add(campo);
	}

	public void remove(CampoAdicional campo) {
		campos.remove(campo);
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

}
