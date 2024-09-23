package adoptask.modelo;

import org.springframework.data.annotation.Id;

public class Archivo {

	@Id
	private String id;
	private String ruta;

	public Archivo() {
	}

	public Archivo(String ruta) {
		this.ruta = ruta;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

}
