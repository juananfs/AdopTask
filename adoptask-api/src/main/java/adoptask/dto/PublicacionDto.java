package adoptask.dto;

import java.time.LocalDate;
import java.util.List;

import adoptask.modelo.CampoAdicional;
import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.SexoAnimal;

public class PublicacionDto {

	private String id;
	private String idProtectora;
	private String portada;
	private String nombre;
	private String descripcion;
	private CategoriaAnimal categoria;
	private String raza;
	private int edad;
	private SexoAnimal sexo;
	private int peso;
	List<CampoAdicional> camposAdicionales;
	private LocalDate fecha;
	private int likes;
	private String email;
	private String ubicacion;
	private String telefono;
	private String web;
	private List<String> imagenes;

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public SexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(SexoAnimal sexo) {
		this.sexo = sexo;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public List<CampoAdicional> getCamposAdicionales() {
		return camposAdicionales;
	}

	public void setCamposAdicionales(List<CampoAdicional> camposAdicionales) {
		this.camposAdicionales = camposAdicionales;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public List<String> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}

}
