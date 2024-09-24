package adoptask.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "publicaciones")
public class Publicacion {

	@Id
	private String id;
	private String idAnimal;
	private LocalDate fecha;
	private int likes;
	private List<String> usuariosLikes;

	public Publicacion() {
		usuariosLikes = new ArrayList<>();
	}

	public Publicacion(String idAnimal) {
		this.idAnimal = idAnimal;
		fecha = LocalDate.now();
		likes = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdAnimal() {
		return idAnimal;
	}

	public void setIdAnimal(String idAnimal) {
		this.idAnimal = idAnimal;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void addLike(String idUsuario) {
		likes++;
		usuariosLikes.add(idUsuario);
	}

	public void removeLike(String idUsuario) {
		likes--;
		usuariosLikes.remove(idUsuario);
	}

	public List<String> getUsuariosLikes() {
		return new ArrayList<>(usuariosLikes);
	}

	public void setUsuariosLikes(List<String> usuariosLikes) {
		this.usuariosLikes = usuariosLikes;
	}

}
