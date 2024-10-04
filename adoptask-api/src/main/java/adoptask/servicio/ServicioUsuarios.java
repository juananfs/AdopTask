package adoptask.servicio;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import adoptask.dto.BusquedaDto;
import adoptask.dto.PublicacionDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.UsuarioDto;
import adoptask.mapper.AnimalMapper;
import adoptask.mapper.UsuarioMapper;
import adoptask.modelo.Animal;
import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.Protectora;
import adoptask.modelo.SexoAnimal;
import adoptask.modelo.Usuario;
import adoptask.repositorio.RepositorioAnimales;
import adoptask.repositorio.RepositorioProtectoras;
import adoptask.repositorio.RepositorioUsuarios;

public class ServicioUsuarios implements IServicioUsuarios {

	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioAnimales repositorioAnimales;
	private RepositorioProtectoras repositorioProtectoras;

	private UsuarioMapper usuarioMapper;
	private AnimalMapper animalMapper;

	@Autowired
	public ServicioUsuarios(RepositorioUsuarios repositorioUsuarios, RepositorioAnimales repositorioAnimales,
			RepositorioProtectoras repositorioProtectoras, UsuarioMapper usuarioMapper, AnimalMapper animalMapper) {
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioAnimales = repositorioAnimales;
		this.repositorioProtectoras = repositorioProtectoras;
		this.usuarioMapper = usuarioMapper;
		this.animalMapper = animalMapper;
	}

	private Usuario findUsuario(String idUsuario) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El id del usuario no debe ser nulo ni estar vacío o en blanco");

		return repositorioUsuarios.findById(idUsuario)
				.orElseThrow(() -> new EntityNotFoundException("No existe usuario con id: " + idUsuario));
	}

	private Animal findPublicacion(String idAnimal) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El id del animal no debe ser nulo ni estar vacío o en blanco");

		return repositorioAnimales.findPublicacion(idAnimal)
				.orElseThrow(() -> new EntityNotFoundException("No existe animal en adopción con id: " + idAnimal));
	}

	private Protectora findProtectora(String idProtectora) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El id de la protectora no debe ser nulo ni estar vacío o en blanco");

		return repositorioProtectoras.findById(idProtectora)
				.orElseThrow(() -> new EntityNotFoundException("No existe protectora con id: " + idProtectora));
	}

	@Override
	public String verificarPassword(String nick, String password) {

		if (nick == null || nick.trim().isEmpty())
			throw new IllegalArgumentException("El nick no debe ser nulo ni estar vacío o en blanco");
		if (password == null || password.trim().isEmpty())
			throw new IllegalArgumentException("La contraseña no debe ser nula ni estar vacía o en blanco");

		Usuario usuario = repositorioUsuarios.findByNick(nick)
				.orElseThrow(() -> new UsernameNotFoundException("No existe usuario con nick: " + nick));

		if (!usuario.getPassword().equals(password))
			throw new BadCredentialsException("Contraseña incorrecta para el usuario con nick: " + nick);

		return usuario.getId();
	}

	@Override
	public Page<ResumenAnimalDto> getPublicaciones(BusquedaDto busquedaDto) {

		if (busquedaDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");

		String nombre = (busquedaDto.getBusqueda() != null && !busquedaDto.getBusqueda().trim().isEmpty())
				? busquedaDto.getBusqueda()
				: ".*";

		List<CategoriaAnimal> categorias = (busquedaDto.getCategorias() != null) ? busquedaDto.getCategorias()
				: Collections.emptyList();

		List<SexoAnimal> sexos = (busquedaDto.getSexos() != null) ? busquedaDto.getSexos() : Collections.emptyList();

		List<String> protectoras = (busquedaDto.getProtectoras() != null) ? busquedaDto.getProtectoras()
				: Collections.emptyList();

		Pageable pageable;
		if (busquedaDto.getOrden() != null) {
			pageable = PageRequest.of(busquedaDto.getPage(), busquedaDto.getSize(), Sort
					.by(busquedaDto.isAscendente() ? Sort.Direction.ASC : Sort.Direction.DESC, busquedaDto.getOrden()));
		} else {
			pageable = PageRequest.of(busquedaDto.getPage(), busquedaDto.getSize());
		}

		Page<Animal> animales = repositorioAnimales.findPublicaciones(nombre, categorias, sexos, protectoras, pageable);

		return animales.map(animal -> animalMapper.toResumenDTO(animal));
	}

	@Override
	public PublicacionDto getPublicacion(String idAnimal) {

		Animal animal = findPublicacion(idAnimal);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		return animalMapper.toPublicacionDTO(animal, protectora);
	}

	@Override
	public String altaUsuario(UsuarioDto usuarioDto) {

		if (usuarioDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");

		Usuario usuario = usuarioMapper.toEntity(usuarioDto);

		return repositorioUsuarios.save(usuario).getId();
	}

	@Override
	public UsuarioDto getUsuario(String idUsuario) {

		Usuario usuario = findUsuario(idUsuario);

		return usuarioMapper.toDTO(usuario);
	}

	@Override
	public void bajaUsuario(String idUsuario) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El id del usuario no debe ser nulo ni estar vacío o en blanco");

		repositorioUsuarios.deleteById(idUsuario);
	}

	@Override
	public void updateUsuario(UsuarioDto usuarioDto) {

		if (usuarioDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");

		Usuario usuario = findUsuario(usuarioDto.getId());

		usuario.setNick(usuarioDto.getNick());
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setEmail(usuarioDto.getEmail());
		usuario.setPassword(usuarioDto.getPassword());
		usuario.setFoto(usuarioDto.getFoto());

		repositorioUsuarios.save(usuario);
	}

	@Override
	public Page<ResumenAnimalDto> getFavoritos(String idUsuario, Pageable pageable) {

		Usuario usuario = findUsuario(idUsuario);

		return repositorioAnimales.findPublicaciones(usuario.getFavoritos(), pageable).map(animalMapper::toResumenDTO);
	}

	@Override
	public void addFavorito(String idUsuario, String idAnimal) {

		Usuario usuario = findUsuario(idUsuario);
		Animal animal = findPublicacion(idAnimal);

		usuario.addFavorito(idAnimal);
		animal.addLike(idUsuario);

		repositorioUsuarios.save(usuario);
		repositorioAnimales.save(animal);
	}

	@Override
	public void removeFavorito(String idUsuario, String idAnimal) {

		Usuario usuario = findUsuario(idUsuario);
		Animal animal = findPublicacion(idAnimal);

		usuario.removeFavorito(idAnimal);
		animal.removeLike(idUsuario);

		repositorioUsuarios.save(usuario);
		repositorioAnimales.save(animal);

	}

}
