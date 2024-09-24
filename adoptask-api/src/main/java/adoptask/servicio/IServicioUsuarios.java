package adoptask.servicio;

import java.util.List;

import org.springframework.data.domain.Page;

import adoptask.dto.BusquedaDto;
import adoptask.dto.PublicacionDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.UsuarioDto;

public interface IServicioUsuarios {

	String verificarPassword(String nick, String password);

	Page<ResumenAnimalDto> getPublicaciones(BusquedaDto busquedaDto);

	PublicacionDto getPublicacion(String idPublicacion);

	String addPublicacion(String idAnimal);

	void removePublicacion(String idPublicacion);

	String altaUsuario(UsuarioDto usuarioDto);

	UsuarioDto getUsuario(String idUsuario);

	void bajaUsuario(String idUsuario);

	void updateUsuario(UsuarioDto usuarioDto);

	List<ResumenAnimalDto> getFavoritos(String idUsuario);

	void addFavorito(String idUsuario, String idPublicacion);

	void removeFavorito(String idUsuario, String idPublicacion);

}
