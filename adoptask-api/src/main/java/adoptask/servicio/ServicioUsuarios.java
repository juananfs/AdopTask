package adoptask.servicio;

import java.util.List;

import org.springframework.data.domain.Page;

import adoptask.dto.BusquedaDto;
import adoptask.dto.PublicacionDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.UsuarioDto;

public class ServicioUsuarios implements IServicioUsuarios {

	@Override
	public String verificarPassword(String nick, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ResumenAnimalDto> getPublicaciones(BusquedaDto busquedaDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicacionDto getPublicacion(String idPublicacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addPublicacion(String idAnimal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePublicacion(String idPublicacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public String altaUsuario(UsuarioDto usuarioDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioDto getUsuario(String idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bajaUsuario(String idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUsuario(UsuarioDto usuarioDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ResumenAnimalDto> getFavoritos(String idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFavorito(String idUsuario, String idPublicacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFavorito(String idUsuario, String idPublicacion) {
		// TODO Auto-generated method stub

	}

}
