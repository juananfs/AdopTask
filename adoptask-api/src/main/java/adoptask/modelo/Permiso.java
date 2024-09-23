package adoptask.modelo;

public class Permiso {

	private String idProtectora;
	private TipoPermiso tipo;

	public Permiso() {
	}

	public Permiso(String idProtectora, TipoPermiso tipo) {
		this.idProtectora = idProtectora;
		this.tipo = tipo;
	}

	public String getIdProtectora() {
		return idProtectora;
	}

	public void setIdProtectora(String idProtectora) {
		this.idProtectora = idProtectora;
	}

	public TipoPermiso getTipo() {
		return tipo;
	}

	public void setTipo(TipoPermiso tipo) {
		this.tipo = tipo;
	}

}
