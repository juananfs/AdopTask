package adoptask.modelo;

public class CampoAdicional {

	private String clave;
	private String valor;
	private boolean privado;

	public CampoAdicional() {
	}

	public CampoAdicional(String clave, String valor, boolean privado) {
		this.clave = clave;
		this.valor = valor;
		this.privado = privado;
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

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

}
