package adoptask.modelo;

public class DatosContacto {

	private String nif;
	private String email;
	private String ubicacion;
	private String web;

	public void DatosContactos() {
	}

	public DatosContacto(String nif, String email, String ubicacion, String web) {
		this.nif = nif;
		this.email = email;
		this.ubicacion = ubicacion;
		this.web = web;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
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

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

}
