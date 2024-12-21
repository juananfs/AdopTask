package adoptask.servicio;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import adoptask.dto.ActividadDto;
import adoptask.dto.AnimalDto;
import adoptask.dto.DocumentoDto;
import adoptask.dto.ProtectoraDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.ResumenProtectoraDto;
import adoptask.dto.TareaDto;
import adoptask.dto.VoluntarioDto;
import adoptask.mapper.ActividadMapper;
import adoptask.mapper.AnimalMapper;
import adoptask.mapper.DocumentoMapper;
import adoptask.mapper.ProtectoraMapper;
import adoptask.mapper.TareaMapper;
import adoptask.mapper.UsuarioMapper;
import adoptask.modelo.Actividad;
import adoptask.modelo.Animal;
import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.Documento;
import adoptask.modelo.EstadoAnimal;
import adoptask.modelo.EstadoTarea;
import adoptask.modelo.Permiso;
import adoptask.modelo.Protectora;
import adoptask.modelo.Tarea;
import adoptask.modelo.TipoPermiso;
import adoptask.modelo.Usuario;
import adoptask.repositorio.RepositorioActividades;
import adoptask.repositorio.RepositorioAnimales;
import adoptask.repositorio.RepositorioProtectoras;
import adoptask.repositorio.RepositorioTareas;
import adoptask.repositorio.RepositorioUsuarios;

@Service
public class ServicioProtectoras implements IServicioProtectoras {

	private RepositorioProtectoras repositorioProtectoras;
	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioAnimales repositorioAnimales;
	private RepositorioTareas repositorioTareas;
	private RepositorioActividades repositorioActividades;

	private ProtectoraMapper protectoraMapper;
	private UsuarioMapper usuarioMapper;
	private AnimalMapper animalMapper;
	private TareaMapper tareaMapper;
	private DocumentoMapper documentoMapper;
	private ActividadMapper actividadMapper;

	@Autowired
	public ServicioProtectoras(RepositorioProtectoras repositorioProtectoras, RepositorioUsuarios repositorioUsuarios,
			RepositorioAnimales repositorioAnimales, RepositorioTareas repositorioTareas,
			RepositorioActividades repositorioActividades, ProtectoraMapper protectoraMapper,
			UsuarioMapper usuarioMapper, AnimalMapper animalMapper, TareaMapper tareaMapper,
			DocumentoMapper documentoMapper, ActividadMapper actividadMapper) {
		this.repositorioProtectoras = repositorioProtectoras;
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioAnimales = repositorioAnimales;
		this.repositorioTareas = repositorioTareas;
		this.repositorioActividades = repositorioActividades;
		this.protectoraMapper = protectoraMapper;
		this.usuarioMapper = usuarioMapper;
		this.animalMapper = animalMapper;
		this.tareaMapper = tareaMapper;
		this.documentoMapper = documentoMapper;
		this.actividadMapper = actividadMapper;
	}

	private Protectora findProtectora(String idProtectora) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");

		return repositorioProtectoras.findById(idProtectora)
				.orElseThrow(() -> new EntityNotFoundException("No existe protectora con ID: " + idProtectora));
	}

	private Usuario findUsuario(String idUsuario) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");

		return repositorioUsuarios.findById(idUsuario)
				.orElseThrow(() -> new EntityNotFoundException("No existe usuario con ID: " + idUsuario));
	}

	private Animal findAnimal(String idAnimal) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");

		return repositorioAnimales.findById(idAnimal)
				.orElseThrow(() -> new EntityNotFoundException("No existe animal con ID: " + idAnimal));
	}

	private Tarea findTarea(String idTarea) {

		if (idTarea == null || idTarea.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la tarea no debe ser nulo ni estar vacío o en blanco");

		return repositorioTareas.findById(idTarea)
				.orElseThrow(() -> new EntityNotFoundException("No existe tarea con ID: " + idTarea));
	}

	private void addActividad(String idProtectora, String nick, String accion) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (nick == null || nick.trim().isEmpty())
			throw new IllegalArgumentException("El nick no debe ser nulo ni estar vacío o en blanco");
		if (accion == null || accion.trim().isEmpty())
			throw new IllegalArgumentException("La acción no debe ser nula ni estar vacía o en blanco");

		Actividad actividad = new Actividad(idProtectora, nick, accion);

		repositorioActividades.save(actividad);
	}

	private void deletePathIfExists(Path path) {

		try {
			Files.deleteIfExists(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Animal deleteAnimal(String idAnimal, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Protectora protectora = findProtectora(animal.getIdProtectora());
		Usuario usuario = findUsuario(idVoluntario);

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.DELETE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para eliminar animales a la protectora");

		repositorioAnimales.delete(animal);

		try {
			Stream.concat(
					animal.getImagenes().stream()
							.map(i -> Paths.get(String.format(DIRECTORIO_IMAGENES_ANIMAL, protectora.getId(), idAnimal),
									i)),
					animal.getDocumentos().stream()
							.map(d -> Paths.get(
									String.format(DIRECTORIO_DOCUMENTOS_ANIMAL, protectora.getId(), idAnimal),
									d.getNombre())))
					.forEach(this::deletePathIfExists);
			deletePathIfExists(Paths.get(String.format(DIRECTORIO_IMAGENES_ANIMAL, protectora.getId(), idAnimal)));
			deletePathIfExists(Paths.get(String.format(DIRECTORIO_DOCUMENTOS_ANIMAL, protectora.getId(), idAnimal)));
			deletePathIfExists(Paths.get(String.format(DIRECTORIO_ANIMAL, protectora.getId(), idAnimal)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return animal;
	}

	@Override
	public Page<ResumenProtectoraDto> getProtectoras(Pageable pageable) {

		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");

		return repositorioProtectoras.findAll(pageable).map(protectoraMapper::toResumenDTO);
	}

	@Override
	public String altaProtectora(ProtectoraDto protectoraDto) {

		if (protectoraDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (protectoraDto.getIdAdmin() == null || protectoraDto.getIdAdmin().trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");
		if (protectoraDto.getNombre() == null || protectoraDto.getNombre().trim().isEmpty())
			throw new IllegalArgumentException("El nombre no debe ser nulo ni estar vacío o en blanco");
		if (protectoraDto.getNif() == null || protectoraDto.getNif().trim().isEmpty())
			throw new IllegalArgumentException("El NIF no debe ser nulo ni estar vacío o en blanco");
		if (protectoraDto.getEmail() == null || protectoraDto.getEmail().trim().isEmpty())
			throw new IllegalArgumentException("El email no debe ser nulo ni estar vacío o en blanco");
		if (protectoraDto.getUbicacion() == null || protectoraDto.getUbicacion().trim().isEmpty())
			throw new IllegalArgumentException("La ubicación no debe ser nula ni estar vacía o en blanco");

		Usuario admin = findUsuario(protectoraDto.getIdAdmin());

		Protectora protectora = protectoraMapper.toEntity(protectoraDto);

		String id = repositorioProtectoras.save(protectora).getId();

		admin.addPermisosDefault(id);

		repositorioUsuarios.save(admin);

		return id;
	}

	@Override
	public void altaProtectoraLogo(String idProtectora, String nombreLogo, String idAdmin) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (nombreLogo == null || nombreLogo.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		protectora.setLogotipo(nombreLogo);

		repositorioProtectoras.save(protectora);
	}

	@Override
	public ProtectoraDto getProtectora(String idProtectora, String idAdmin) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		return protectoraMapper.toDTO(protectora);
	}

	@Override
	public void bajaProtectora(String idProtectora, String idAdmin) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		repositorioAnimales.findByIdProtectora(idProtectora, Pageable.unpaged()).map(Animal::getId)
				.forEach(id -> deleteAnimal(id, idAdmin));
		try {
			deletePathIfExists(Paths.get(String.format(DIRECTORIO_ANIMALES, idProtectora)));

			protectora.getDocumentos().stream()
					.map(d -> Paths.get(String.format(DIRECTORIO_DOCUMENTOS, idProtectora), d.getNombre()))
					.forEach(this::deletePathIfExists);
			deletePathIfExists(Paths.get(String.format(DIRECTORIO_DOCUMENTOS, idProtectora)));

			if (protectora.getLogotipo() != null)
				deletePathIfExists(
						Paths.get(String.format(DIRECTORIO_PROTECTORA, idProtectora), protectora.getLogotipo()));

			deletePathIfExists(Paths.get(String.format(DIRECTORIO_PROTECTORA, idProtectora)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Page<Tarea> tareas = repositorioTareas.findByIdProtectora(idProtectora, Pageable.unpaged());
		repositorioTareas.deleteAll(tareas);

		Page<Actividad> actividades = repositorioActividades.findByIdProtectora(idProtectora, Pageable.unpaged());
		repositorioActividades.deleteAll(actividades);

		Page<Usuario> usuarios = repositorioUsuarios.findByIdIn(protectora.getVoluntarios(), Pageable.unpaged());
		usuarios.forEach(usuario -> usuario.getPermisos().stream()
				.filter(permiso -> permiso.getIdProtectora().equals(idProtectora)).forEach(usuario::removePermiso));
		repositorioUsuarios.saveAll(usuarios);

		repositorioProtectoras.delete(protectora);
	}

	@Override
	public void updateProtectora(ProtectoraDto protectoraDto) {

		if (protectoraDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (protectoraDto.getId() == null || protectoraDto.getId().trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (protectoraDto.getIdAdmin() == null || protectoraDto.getIdAdmin().trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(protectoraDto.getId());

		if (!protectora.isAdmin(protectoraDto.getIdAdmin()))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		if (protectoraDto.getNombre() != null && !protectoraDto.getNombre().trim().isEmpty())
			protectora.setNombre(protectoraDto.getNombre());
		if (protectoraDto.getNif() != null && !protectoraDto.getNif().trim().isEmpty())
			protectora.setNif(protectoraDto.getNif());
		if (protectoraDto.getEmail() != null && !protectoraDto.getEmail().trim().isEmpty())
			protectora.setEmail(protectoraDto.getEmail());
		if (protectoraDto.getUbicacion() != null && !protectoraDto.getUbicacion().trim().isEmpty())
			protectora.setUbicacion(protectoraDto.getUbicacion());
		if (protectoraDto.getTelefono() != null && !protectoraDto.getTelefono().trim().isEmpty())
			protectora.setTelefono(protectoraDto.getTelefono());
		if (protectoraDto.getWeb() != null && !protectoraDto.getWeb().trim().isEmpty())
			protectora.setWeb(protectoraDto.getWeb());
		if (protectoraDto.getDescripcion() != null && !protectoraDto.getDescripcion().trim().isEmpty())
			protectora.setDescripcion(protectoraDto.getDescripcion());
		if (protectoraDto.getLogotipo() != null && !protectoraDto.getLogotipo().trim().isEmpty()
				&& !protectoraDto.getLogotipo().equals(protectora.getLogotipo())) {
			if (protectora.getLogotipo() != null)
				try {
					deletePathIfExists(Paths.get(String.format(DIRECTORIO_PROTECTORA, protectora.getId()),
							protectora.getLogotipo()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			protectora.setLogotipo(protectoraDto.getLogotipo());
		}

		repositorioProtectoras.save(protectora);
	}

	@Override
	public VoluntarioDto verificarAcceso(String idUsuario, String idProtectora) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del usuario no debe ser nulo ni estar vacío o en blanco");
		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.tieneAcceso(idUsuario))
			throw new AccessDeniedException("El usuario no tiene acceso a la protectora");

		Usuario usuario = findUsuario(idUsuario);

		return usuarioMapper.toVoluntarioDTO(usuario, protectora);
	}

	@Override
	public Page<VoluntarioDto> getVoluntarios(String idProtectora, Pageable pageable, String idAdmin) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		return repositorioUsuarios.findByIdIn(protectora.getVoluntarios(), pageable)
				.map(usuario -> usuarioMapper.toVoluntarioDTO(usuario, protectora));
	}

	@Override
	public String addVoluntario(String idProtectora, String nick, String idAdmin) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (nick == null || nick.trim().isEmpty())
			throw new IllegalArgumentException("El nick no debe ser nulo ni estar vacío o en blanco");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		Usuario voluntario = repositorioUsuarios.findByNick(nick)
				.orElseThrow(() -> new UsernameNotFoundException("No existe usuario con nick: " + nick));

		if (protectora.tieneAcceso(voluntario.getId()))
			throw new ServiceException("El usuario ya tiene acceso a la protectora");

		protectora.addVoluntario(voluntario.getId());
		voluntario.addPermisosDefault(idProtectora);

		repositorioProtectoras.save(protectora);
		repositorioUsuarios.save(voluntario);

		return voluntario.getId();
	}

	@Override
	public void removeVoluntario(String idProtectora, String idVoluntario, String idAdmin) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		if (protectora.isAdmin(idVoluntario))
			throw new ServiceException("El administrador de la protectora no puede ser eliminado");

		Usuario voluntario = findUsuario(idVoluntario);

		protectora.removeVoluntario(idVoluntario);
		voluntario.getPermisos().stream().filter(permiso -> permiso.getIdProtectora().equals(idProtectora))
				.forEach(voluntario::removePermiso);

		repositorioProtectoras.save(protectora);
		repositorioUsuarios.save(voluntario);
	}

	@Override
	public void updatePermisos(VoluntarioDto voluntarioDto, String idAdmin) {

		if (voluntarioDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (voluntarioDto.getId() == null || voluntarioDto.getId().trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");
		if (voluntarioDto.getIdProtectora() == null || voluntarioDto.getIdProtectora().trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El ID del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(voluntarioDto.getIdProtectora());

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		Usuario usuario = findUsuario(voluntarioDto.getId());

		if (voluntarioDto.isAdmin()) {

			protectora.setIdAdmin(usuario.getId());

			repositorioProtectoras.save(protectora);

		} else {

			List<Permiso> permisos = usuario.getPermisos().stream()
					.filter(p -> p.getIdProtectora().equals(protectora.getId())).toList();
			permisos.stream().filter(permiso -> !voluntarioDto.tienePermiso(permiso.getTipo()))
					.forEach(usuario::removePermiso);
			List<TipoPermiso> tiposPermiso = permisos.stream().map(Permiso::getTipo).toList();
			voluntarioDto.getPermisos().stream().filter(tipo -> !tiposPermiso.contains(tipo))
					.forEach(tipo -> usuario.addPermiso(new Permiso(protectora.getId(), tipo)));

			repositorioUsuarios.save(usuario);
		}
	}

	@Override
	public Page<ResumenAnimalDto> getAnimales(String idProtectora, String categoria, String estado, Pageable pageable,
			String idVoluntario) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(idProtectora);
		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.READ_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para ver los animales de la protectora");

		if ((categoria == null || categoria.trim().isEmpty()) && (estado == null || estado.trim().isEmpty())) {
			return repositorioAnimales.findByIdProtectora(idProtectora, pageable).map(animalMapper::toResumenDTO);
		}
		if (categoria == null || categoria.trim().isEmpty()) {
			return repositorioAnimales.findByIdProtectoraAndEstado(idProtectora, EstadoAnimal.valueOf(estado), pageable)
					.map(animalMapper::toResumenDTO);
		}
		if (estado == null || estado.trim().isEmpty()) {
			return repositorioAnimales
					.findByIdProtectoraAndDatosCategoria(idProtectora, CategoriaAnimal.valueOf(categoria), pageable)
					.map(animalMapper::toResumenDTO);
		}
		return repositorioAnimales.findByIdProtectoraAndDatosCategoriaAndEstado(idProtectora,
				CategoriaAnimal.valueOf(categoria), EstadoAnimal.valueOf(estado), pageable)
				.map(animalMapper::toResumenDTO);
	}

	@Override
	public String altaAnimal(AnimalDto animalDto, String idVoluntario) {

		if (animalDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (animalDto.getIdProtectora() == null || animalDto.getIdProtectora().trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (animalDto.getNombre() == null || animalDto.getNombre().trim().isEmpty())
			throw new IllegalArgumentException("El nombre no debe ser nulo ni estar vacío o en blanco");
		if (animalDto.getCategoria() == null)
			throw new IllegalArgumentException("La categoría no debe ser nula");
		if (animalDto.getEstado() == null)
			throw new IllegalArgumentException("El estado no debe ser nulo");
		if (animalDto.getFechaEntrada() == null)
			throw new IllegalArgumentException("La fecha de entrada no debe ser nula");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animalDto.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.CREATE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para añadir animales a la protectora");

		Animal animal = animalMapper.toEntity(animalDto);

		String id = repositorioAnimales.save(animal).getId();

		addActividad(protectora.getId(), usuario.getNick(), "ha añadido un animal: " + animal.getNombre());

		return id;
	}

	@Override
	public void altaAnimalPortada(String idAnimal, String nombrePortada, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (nombrePortada == null || nombrePortada.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.CREATE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para añadir animales a la protectora");

		animal.setPortada(nombrePortada);
		animal.addImagen(nombrePortada);

		repositorioAnimales.save(animal);
	}

	@Override
	public AnimalDto getAnimal(String idAnimal, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.READ_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para ver los datos del animal");

		return animalMapper.toDTO(animal);
	}

	@Override
	public void bajaAnimal(String idAnimal, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idVoluntario);
		Animal animal = deleteAnimal(idAnimal, idVoluntario);

		addActividad(animal.getIdProtectora(), usuario.getNick(), "ha eliminado un animal: " + animal.getNombre());
	}

	@Override
	public void updateAnimal(AnimalDto animalDto, String idVoluntario) {

		if (animalDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (animalDto.getId() == null || animalDto.getId().trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(animalDto.getId());
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.UPDATE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para modificar la ficha del animal");

		if (animalDto.getPortada() != null && !animalDto.getPortada().trim().isEmpty()) {
			String portada = animalDto.getPortada();
			if (animal.containsImagen(portada))
				animal.setPortada(portada);
			else
				throw new EntityNotFoundException("No existe imagen: " + portada);
		}
		if (animalDto.getEstado() != null) {
			if (animalDto.isEnAdopcion())
				animal.setFechaPublicacion(LocalDate.now());
			animal.setEstado(animalDto.getEstado());
		}
		if (animalDto.getNombre() != null && !animalDto.getNombre().trim().isEmpty())
			animal.setNombre(animalDto.getNombre());
		if (animalDto.getCategoria() != null)
			animal.setCategoria(animalDto.getCategoria());

		if (animalDto.getFechaEntrada() != null)
			animal.setFechaEntrada(animalDto.getFechaEntrada());
		if (animalDto.getCamposAdicionales() != null)
			animal.setCamposAdicionales(animalDto.getCamposAdicionales());
		animal.setRaza(animalDto.getRaza());
		animal.setSexo(animalDto.getSexo());
		animal.setFechaNacimiento(animalDto.getFechaNacimiento());
		animal.setPeso(animalDto.getPeso());
		animal.setDescripcion(animalDto.getDescripcion());

		repositorioAnimales.save(animal);

		addActividad(protectora.getId(), usuario.getNick(), "ha modificado un animal: " + animal.getNombre());
	}

	@Override
	public void addImagenAnimal(String idAnimal, String nombre, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (nombre == null || nombre.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.UPDATE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para modificar la ficha del animal");

		if (!animal.addImagen(nombre))
			throw new ServiceException("Se ha alcanzado el límite de imágenes para este animal");

		repositorioAnimales.save(animal);

		addActividad(protectora.getId(), usuario.getNick(), "ha añadido una imagen a un animal: " + animal.getNombre());
	}

	@Override
	public void removeImagenAnimal(String idAnimal, String nombre, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (nombre == null || nombre.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.UPDATE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para modificar la ficha del animal");

		if (animal.getPortada().equals(nombre))
			throw new ServiceException("No se puede eliminar la imagen de portada");

		if (!animal.removeImagen(nombre))
			throw new EntityNotFoundException("No existe imagen: " + nombre);

		repositorioAnimales.save(animal);

		try {
			deletePathIfExists(
					Paths.get(String.format(DIRECTORIO_IMAGENES_ANIMAL, protectora.getId(), idAnimal), nombre));
		} catch (Exception e) {
			e.printStackTrace();
		}

		addActividad(protectora.getId(), usuario.getNick(),
				"ha eliminado una imagen de un animal: " + animal.getNombre());
	}

	@Override
	public void addDocumentoAnimal(String idAnimal, String nombre, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (nombre == null || nombre.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.UPDATE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para modificar la ficha del animal");

		Documento documento = new Documento(nombre);
		if (!animal.addDocumento(documento))
			throw new ServiceException("Se ha alcanzado el límite de documentos para este animal");

		repositorioAnimales.save(animal);

		addActividad(protectora.getId(), usuario.getNick(),
				"ha añadido un documento a un animal: " + animal.getNombre());
	}

	@Override
	public void removeDocumentoAnimal(String idAnimal, String nombre, String idVoluntario) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El ID del animal no debe ser nulo ni estar vacío o en blanco");
		if (nombre == null || nombre.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.UPDATE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para modificar la ficha del animal");

		if (!animal.removeDocumento(nombre))
			throw new EntityNotFoundException("No existe documento: " + nombre);

		repositorioAnimales.save(animal);

		try {
			deletePathIfExists(
					Paths.get(String.format(DIRECTORIO_DOCUMENTOS_ANIMAL, protectora.getId(), idAnimal), nombre));
		} catch (Exception e) {
			e.printStackTrace();
		}

		addActividad(protectora.getId(), usuario.getNick(),
				"ha eliminado un documento de un animal: " + animal.getNombre());
	}

	@Override
	public Page<TareaDto> getTareas(String idProtectora, String estado, Pageable pageable, String idVoluntario) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(idProtectora, TipoPermiso.READ_TAREAS))
			throw new AccessDeniedException("El usuario no tiene permiso para ver las tareas");

		if (estado != null) {
			EstadoTarea estadoTarea = EstadoTarea.valueOf(estado);
			return repositorioTareas.findByIdProtectoraAndEstado(idProtectora, estadoTarea, pageable)
					.map(tareaMapper::toDTO);
		}

		return repositorioTareas.findByIdProtectora(idProtectora, pageable).map(tareaMapper::toDTO);
	}

	@Override
	public String addTarea(TareaDto tareaDto, String idVoluntario) {

		if (tareaDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (tareaDto.getIdProtectora() == null || tareaDto.getIdProtectora().trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (tareaDto.getTitulo() == null || tareaDto.getTitulo().trim().isEmpty())
			throw new IllegalArgumentException("El título no debe ser nulo ni estar vacío o en blanco");
		if (tareaDto.getPrioridad() == null)
			throw new IllegalArgumentException("La prioridad no debe ser nula");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(tareaDto.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.CREATE_TAREAS))
			throw new AccessDeniedException("El usuario no tiene permiso para crear tareas");

		Tarea tarea = tareaMapper.toEntity(tareaDto);

		String id = repositorioTareas.save(tarea).getId();

		addActividad(protectora.getId(), usuario.getNick(), "ha añadido una tarea: " + tarea.getTitulo());

		return id;
	}

	@Override
	public void removeTarea(String idTarea, String idVoluntario) {

		if (idTarea == null || idTarea.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la tarea no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Tarea tarea = findTarea(idTarea);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(tarea.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.DELETE_TAREAS))
			throw new AccessDeniedException("El usuario no tiene permiso para eliminar tareas");

		repositorioTareas.delete(tarea);

		addActividad(protectora.getId(), usuario.getNick(), "ha eliminado una tarea: " + tarea.getTitulo());
	}

	@Override
	public void updateTarea(TareaDto tareaDto, String idVoluntario) {

		if (tareaDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (tareaDto.getId() == null || tareaDto.getId().trim().isEmpty())
			throw new IllegalArgumentException("El ID de la tarea no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Tarea tarea = findTarea(tareaDto.getId());
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(tarea.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.UPDATE_TAREAS))
			throw new AccessDeniedException("El usuario no tiene permiso para modificar tareas");

		if (tarea.getEstado().equals(EstadoTarea.PENDIENTE) && tareaDto.getEstado().equals(EstadoTarea.EN_CURSO)) {

			tarea.setEstado(EstadoTarea.EN_CURSO);
			tarea.setEncargado(usuario.getNick());

			repositorioTareas.save(tarea);

			addActividad(protectora.getId(), usuario.getNick(), "ha empezado una tarea: " + tarea.getTitulo());

		} else if (tarea.getEstado().equals(EstadoTarea.EN_CURSO)) {

			if (tareaDto.getEstado().equals(EstadoTarea.PENDIENTE)) {

				tarea.setEstado(EstadoTarea.PENDIENTE);
				tarea.setEncargado(null);

				repositorioTareas.save(tarea);

				addActividad(protectora.getId(), usuario.getNick(), "ha abandonado una tarea: " + tarea.getTitulo());

			} else if (tareaDto.getEstado().equals(EstadoTarea.COMPLETADA)) {

				tarea.setEstado(EstadoTarea.COMPLETADA);

				repositorioTareas.save(tarea);

				addActividad(protectora.getId(), usuario.getNick(), "ha completado una tarea: " + tarea.getTitulo());
			}
		}
	}

	@Override
	public List<DocumentoDto> getDocumentos(String idProtectora, String idVoluntario) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);
		Usuario usuario = findUsuario(idVoluntario);

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.READ_DOCUMENTOS))
			throw new AccessDeniedException("El usuario no tiene permiso para ver los documentos de la protectora");

		return protectora.getDocumentos().stream().map(documentoMapper::toDTO).toList();
	}

	@Override
	public void addDocumento(String idProtectora, String nombre, String idVoluntario) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (nombre == null || nombre.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idVoluntario)
				&& !usuario.tienePermiso(protectora.getId(), TipoPermiso.CREATE_DOCUMENTOS))
			throw new AccessDeniedException("El usuario no tiene permiso para añadir documentos a la protectora");

		Documento documento = new Documento(nombre);
		protectora.addDocumento(documento);

		repositorioProtectoras.save(protectora);

		addActividad(protectora.getId(), usuario.getNick(), "ha añadido un documento: " + documento.getNombre());
	}

	@Override
	public void removeDocumento(String idProtectora, String nombre, String idVoluntario) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (nombre == null || nombre.trim().isEmpty())
			throw new IllegalArgumentException("El nombre del archivo no debe ser nulo ni estar vacío o en blanco");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);
		Usuario usuario = findUsuario(idVoluntario);

		if (!protectora.isAdmin(idVoluntario)
				&& !usuario.tienePermiso(protectora.getId(), TipoPermiso.DELETE_DOCUMENTOS))
			throw new AccessDeniedException("El usuario no tiene permiso para eliminar documentos de la protectora");

		if (!protectora.removeDocumento(nombre))
			throw new EntityNotFoundException("No existe documento: " + nombre);

		repositorioProtectoras.save(protectora);

		try {
			deletePathIfExists(Paths.get(String.format(DIRECTORIO_DOCUMENTOS, protectora.getId()), nombre));
		} catch (Exception e) {
			e.printStackTrace();
		}

		addActividad(protectora.getId(), usuario.getNick(), "ha eliminado un documento: " + nombre);
	}

	@Override
	public Page<ActividadDto> getHistorial(String idProtectora, Pageable pageable, String idVoluntario) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El ID de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El ID del voluntario no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);
		Usuario usuario = findUsuario(idVoluntario);

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(idProtectora, TipoPermiso.READ_HISTORIAL))
			throw new AccessDeniedException("El usuario no tiene permiso para ver el historial");

		return repositorioActividades.findByIdProtectora(idProtectora, pageable).map(actividadMapper::toDTO);
	}

}
