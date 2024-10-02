package adoptask.dto;

import java.util.List;

import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.SexoAnimal;

public class BusquedaDto {

	private String busqueda;
	private String orden;
	private boolean ascendente;
	private int page;
	private int size;
	List<String> protectoras;
	List<CategoriaAnimal> categorias;
	List<SexoAnimal> sexos;

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public boolean isAscendente() {
		return ascendente;
	}

	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<String> getProtectoras() {
		return protectoras;
	}

	public void setProtectoras(List<String> protectoras) {
		this.protectoras = protectoras;
	}

	public List<CategoriaAnimal> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaAnimal> categorias) {
		this.categorias = categorias;
	}

	public List<SexoAnimal> getSexos() {
		return sexos;
	}

	public void setSexos(List<SexoAnimal> sexos) {
		this.sexos = sexos;
	}

}
