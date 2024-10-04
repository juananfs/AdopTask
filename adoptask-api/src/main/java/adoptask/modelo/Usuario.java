package adoptask.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

	@Id
	private String id;
	@Indexed
	private String nick;
	private String nombre;
	private String email;
	private String password;
	private String foto;
	private List<Permiso> permisos;
	private List<String> favoritos;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void addPermiso(Permiso permiso) {
		permisos.add(permiso);
	}

	public void removePermiso(Permiso permiso) {
		permisos.remove(permiso);
	}

	public List<String> getFavoritos() {
		return new ArrayList<>(favoritos);
	}

	public void setFavoritos(List<String> favoritos) {
		this.favoritos = favoritos;
	}

	public void addFavorito(String favorito) {
		favoritos.add(favorito);
	}

	public void removeFavorito(String favorito) {
		favoritos.remove(favorito);
	}

}
