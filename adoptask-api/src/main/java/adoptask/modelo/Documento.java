package adoptask.modelo;

import java.time.LocalDate;

public class Documento {

	private String nombre;
	private String ruta;
	private LocalDate fecha;

	public Documento() {
	}

	public Documento(String nombre, String ruta) {
		this.nombre = nombre;
		this.ruta = ruta;
		fecha = LocalDate.now();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

}
