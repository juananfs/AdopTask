package adoptask.dto;

import java.time.LocalDate;

public class DocumentoDto {
	private String nombre;
	private LocalDate fecha;

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