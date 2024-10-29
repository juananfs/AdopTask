package adoptask.servicio;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

@Service
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
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");

		return repositorioUsuarios.findById(idUsuario)
				.orElseThrow(() -> new EntityNotFoundException("No existe usuario con ID: " + idUsuario));
	}

	private Animal findPublicacion(String idAnimal) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");

		return repositorioAnimales.findPublicacion(idAnimal)
				.orElseThrow(() -> new EntityNotFoundException("No existe animal en adopción con ID: " + idAnimal));
	}

	private Protectora findProtectora(String idProtectora) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");

		return repositorioProtectoras.findById(idProtectora)
				.orElseThrow(() -> new EntityNotFoundException("No existe protectora con ID: " + idProtectora));
	}

	@Override
	public UsuarioDto verificarPassword(String nick, String password) {

		if (nick == null || nick.trim().isEmpty())
			throw new IllegalArgumentException("El nick no debe ser nulo ni estar vacío o en blanco");
		if (password == null || password.trim().isEmpty())
			throw new IllegalArgumentException("La contraseña no debe ser nula ni estar vacía o en blanco");

		Usuario usuario = repositorioUsuarios.findByNick(nick)
				.orElseThrow(() -> new UsernameNotFoundException("No existe usuario con nick: " + nick));

		if (!usuario.getPassword().equals(password))
			throw new BadCredentialsException("Contraseña incorrecta para el usuario con nick: " + nick);

		return usuarioMapper.toDTO(usuario);
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

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findPublicacion(idAnimal);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		return animalMapper.toPublicacionDTO(animal, protectora);
	}

	@Override
	public String altaUsuario(UsuarioDto usuarioDto) {

		if (usuarioDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (usuarioDto.getNick() == null || usuarioDto.getNick().trim().isEmpty())
			throw new IllegalArgumentException("El nickname no debe ser nulo ni estar vacío o en blanco");
		if (usuarioDto.getNombre() == null || usuarioDto.getNombre().trim().isEmpty())
			throw new IllegalArgumentException("El nombre no debe ser nulo ni estar vacío o en blanco");
		if (usuarioDto.getEmail() == null || usuarioDto.getEmail().trim().isEmpty())
			throw new IllegalArgumentException("El email no debe ser nulo ni estar vacío o en blanco");
		if (usuarioDto.getPassword() == null || usuarioDto.getPassword().trim().isEmpty())
			throw new IllegalArgumentException("La contraseña no debe ser nula ni estar vacía o en blanco");
		repositorioUsuarios.findByNick(usuarioDto.getNick()).ifPresent(u -> {
			throw new IllegalArgumentException("El nickname debe ser único");
		});
		repositorioUsuarios.findByEmail(usuarioDto.getEmail()).ifPresent(u -> {
			throw new IllegalArgumentException("El email debe ser único");
		});

		Usuario usuario = usuarioMapper.toEntity(usuarioDto);

		return repositorioUsuarios.save(usuario).getId();
	}

	@Override
	public void altaUsuarioFoto(String idUsuario, String nombreFoto) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");
		if (nombreFoto == null || nombreFoto.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idUsuario);

		usuario.setFoto(nombreFoto);

		repositorioUsuarios.save(usuario);
	}

	@Override
	public UsuarioDto getUsuario(String idUsuario) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idUsuario);

		return usuarioMapper.toDTO(usuario);
	}

	@Override
	public void bajaUsuario(String idUsuario) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idUsuario);

		if (repositorioProtectoras.findByIdAdmin(idUsuario).isPresent())
			throw new ServiceException(
					"El usuario debe establecer un nuevo administrador para su protectora o darla de baja");

		repositorioUsuarios.delete(usuario);
		try {
			Files.deleteIfExists(Paths.get(String.format(DIRECTORIO_FOTOS_PERFIL, idUsuario), usuario.getFoto()));
			Files.deleteIfExists(Paths.get(String.format(DIRECTORIO_FOTOS_PERFIL, idUsuario)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUsuario(UsuarioDto usuarioDto) {

		if (usuarioDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (usuarioDto.getId() == null || usuarioDto.getId().trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(usuarioDto.getId());

		if (usuarioDto.getNick() != null && !usuarioDto.getNick().trim().isEmpty())
			usuario.setNick(usuarioDto.getNick());
		if (usuarioDto.getNombre() != null && !usuarioDto.getNombre().trim().isEmpty())
			usuario.setNombre(usuarioDto.getNombre());
		if (usuarioDto.getEmail() != null && !usuarioDto.getEmail().trim().isEmpty())
			usuario.setEmail(usuarioDto.getEmail());
		if (usuarioDto.getPassword() != null && !usuarioDto.getPassword().trim().isEmpty())
			usuario.setPassword(usuarioDto.getPassword());
		if (usuarioDto.getFoto() != null && !usuarioDto.getFoto().trim().isEmpty()
				&& !usuarioDto.getFoto().equals(usuario.getFoto())) {
			try {
				Files.deleteIfExists(
						Paths.get(String.format(DIRECTORIO_FOTOS_PERFIL, usuario.getId()), usuario.getFoto()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			usuario.setFoto(usuarioDto.getFoto());
		}

		repositorioUsuarios.save(usuario);
	}

	@Override
	public Page<ResumenAnimalDto> getFavoritos(String idUsuario, Pageable pageable) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");
		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");

		Usuario usuario = findUsuario(idUsuario);

		return repositorioAnimales.findPublicaciones(usuario.getFavoritos(), pageable).map(animalMapper::toResumenDTO);
	}

	@Override
	public void addFavorito(String idUsuario, String idAnimal) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");
		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idUsuario);
		Animal animal = findPublicacion(idAnimal);

		usuario.addFavorito(idAnimal);
		animal.addLike(idUsuario);

		repositorioUsuarios.save(usuario);
		repositorioAnimales.save(animal);
	}

	@Override
	public void removeFavorito(String idUsuario, String idAnimal) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");
		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idUsuario);
		Animal animal = findPublicacion(idAnimal);

		usuario.removeFavorito(idAnimal);
		animal.removeLike(idUsuario);

		repositorioUsuarios.save(usuario);
		repositorioAnimales.save(animal);

	}

}
