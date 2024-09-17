package adoptask.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

	@Id
	private String nick;
	private String nombre;
	private String email;
	private String password;
	private String foto;
	private List<Permiso> permisos = new LinkedList<>();
	private List<Publicacion> favoritos = new LinkedList<>();

	public Usuario() {
		permisos = new LinkedList<>();
		favoritos = new LinkedList<>();
	}

	public Usuario(String nick, String nombre, String email, String password, String foto) {
		this();
		this.nick = nick;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.foto = foto;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Permiso> getPermisos() {
		return new ArrayList<>(permisos);
	}

	public void setPermisos(List<Permiso> permisos) {
		this.permisos = permisos;
	}

	public void add(Permiso permiso) {
		permisos.add(permiso);
	}

	public void remove(Permiso permiso) {
		permisos.remove(permiso);
	}

	public List<Publicacion> getFavoritos() {
		return new ArrayList<>(favoritos);
	}

	public void setFavoritos(List<Publicacion> favoritos) {
		this.favoritos = favoritos;
	}

	public void add(Publicacion favorito) {
		favoritos.add(favorito);
	}

	public void remove(Publicacion favorito) {
		favoritos.remove(favorito);
	}

}
