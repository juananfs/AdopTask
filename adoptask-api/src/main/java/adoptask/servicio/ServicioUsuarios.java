package adoptask.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import adoptask.dto.BusquedaDto;
import adoptask.dto.PublicacionDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.UsuarioDto;
import adoptask.mapper.AnimalMapper;
import adoptask.mapper.PublicacionMapper;
import adoptask.mapper.UsuarioMapper;
import adoptask.repositorio.RepositorioAnimales;
import adoptask.repositorio.RepositorioPublicaciones;
import adoptask.repositorio.RepositorioUsuarios;

public class ServicioUsuarios implements IServicioUsuarios {

	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioPublicaciones repositorioPublicaciones;
	private RepositorioAnimales repositorioAnimales;

	private UsuarioMapper usuarioMapper;
	private PublicacionMapper publicacionMapper;
	private AnimalMapper animalMapper;

	@Autowired
	public ServicioUsuarios(RepositorioUsuarios repositorioUsuarios, RepositorioPublicaciones repositorioPublicaciones,
			RepositorioAnimales repositorioAnimales, UsuarioMapper usuarioMapper, PublicacionMapper publicacionMapper,
			AnimalMapper animalMapper) {
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioPublicaciones = repositorioPublicaciones;
		this.repositorioAnimales = repositorioAnimales;
		this.usuarioMapper = usuarioMapper;
		this.publicacionMapper = publicacionMapper;
		this.animalMapper = animalMapper;
	}

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
