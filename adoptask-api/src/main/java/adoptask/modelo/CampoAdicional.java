package adoptask.modelo;

public class CampoAdicional {

	private String clave;
	private String valor;
	private boolean publico;

	public CampoAdicional() {
	}

	public CampoAdicional(String clave, String valor, boolean publico) {
		this.clave = clave;
		this.valor = valor;
		this.publico = publico;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

}
