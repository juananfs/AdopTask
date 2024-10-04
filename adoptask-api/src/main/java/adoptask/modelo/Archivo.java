package adoptask.modelo;

import java.util.UUID;

public class Archivo {

	private String id;
	private String ruta;

	public Archivo() {
	}

	public Archivo(String ruta) {
		id = UUID.randomUUID().toString();
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
