package adoptask.modelo;

import java.time.LocalDate;

public class Documento {

	private String nombre;
	private LocalDate fecha;

	public Documento() {
	}

	public Documento(String nombre) {
		this.nombre = nombre;
		fecha = LocalDate.now();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

}
