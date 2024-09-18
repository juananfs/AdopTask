package adoptask.modelo;

public class Permiso {

	private TipoPermiso tipo;
	private Protectora protectora;

	public Permiso() {
	}

	public Permiso(TipoPermiso tipo, Protectora protectora) {
		this.tipo = tipo;
		this.protectora = protectora;
	}

	public TipoPermiso getTipo() {
		return tipo;
	}

	public void setTipo(TipoPermiso tipo) {
		this.tipo = tipo;
	}

	public Protectora getProtectora() {
		return protectora;
	}

	public void setProtectora(Protectora protectora) {
		this.protectora = protectora;
	}

}
