package adoptask.servicio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import adoptask.dto.ActividadDto;
import adoptask.dto.AnimalDto;
import adoptask.dto.ProtectoraDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.ResumenProtectoraDto;
import adoptask.dto.TareaDto;
import adoptask.dto.VoluntarioDto;
import adoptask.mapper.ActividadMapper;
import adoptask.mapper.AnimalMapper;
import adoptask.mapper.ProtectoraMapper;
import adoptask.mapper.TareaMapper;
import adoptask.mapper.UsuarioMapper;
import adoptask.modelo.Actividad;
import adoptask.modelo.Animal;
import adoptask.modelo.Archivo;
import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.EstadoAnimal;
import adoptask.modelo.Permiso;
import adoptask.modelo.Protectora;
import adoptask.modelo.TipoPermiso;
import adoptask.modelo.Usuario;
import adoptask.repositorio.RepositorioActividades;
import adoptask.repositorio.RepositorioAnimales;
import adoptask.repositorio.RepositorioProtectoras;
import adoptask.repositorio.RepositorioUsuarios;

public class ServicioProtectoras implements IServicioProtectoras {

	private RepositorioProtectoras repositorioProtectoras;
	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioAnimales repositorioAnimales;
	private RepositorioActividades repositorioActividades;

	private ProtectoraMapper protectoraMapper;
	private UsuarioMapper usuarioMapper;
	private AnimalMapper animalMapper;
	private TareaMapper tareaMapper;
	private ActividadMapper actividadMapper;

	@Autowired
	public ServicioProtectoras(RepositorioProtectoras repositorioProtectoras, RepositorioUsuarios repositorioUsuarios,
			RepositorioAnimales repositorioAnimales, RepositorioActividades repositorioActividades,
			ProtectoraMapper protectoraMapper, UsuarioMapper usuarioMapper, AnimalMapper animalMapper,
			TareaMapper tareaMapper, ActividadMapper actividadMapper) {
		this.repositorioProtectoras = repositorioProtectoras;
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioAnimales = repositorioAnimales;
		this.repositorioActividades = repositorioActividades;
		this.protectoraMapper = protectoraMapper;
		this.usuarioMapper = usuarioMapper;
		this.animalMapper = animalMapper;
		this.tareaMapper = tareaMapper;
		this.actividadMapper = actividadMapper;
	}

	private Protectora findProtectora(String idProtectora) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El id de la protectora no debe ser nulo ni estar vacío o en blanco");

		return repositorioProtectoras.findById(idProtectora)
				.orElseThrow(() -> new EntityNotFoundException("No existe protectora con id: " + idProtectora));
	}

	private Usuario findUsuario(String idUsuario) {

		if (idUsuario == null || idUsuario.trim().isEmpty())
			throw new IllegalArgumentException("El id del usuario no debe ser nulo ni estar vacío o en blanco");

		return repositorioUsuarios.findById(idUsuario)
				.orElseThrow(() -> new EntityNotFoundException("No existe usuario con id: " + idUsuario));
	}

	private Animal findAnimal(String idAnimal) {

		if (idAnimal == null || idAnimal.trim().isEmpty())
			throw new IllegalArgumentException("El id del animal no debe ser nulo ni estar vacío o en blanco");

		return repositorioAnimales.findPublicacion(idAnimal)
				.orElseThrow(() -> new EntityNotFoundException("No existe animal con id: " + idAnimal));
	}

	private void addActividad(String idProtectora, String nick, String accion) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El id de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (nick == null || nick.trim().isEmpty())
			throw new IllegalArgumentException("El nick no debe ser nulo ni estar vacío o en blanco");
		if (accion == null || accion.trim().isEmpty())
			throw new IllegalArgumentException("La acción no debe ser nula ni estar vacía o en blanco");

		Actividad actividad = new Actividad(idProtectora, nick, accion);

		repositorioActividades.save(actividad);
	}

	private Animal deleteAnimal(String idAnimal, String idVoluntario) {

		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El id del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Protectora protectora = findProtectora(animal.getIdProtectora());
		Usuario usuario = findUsuario(idVoluntario);

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.DELETE_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para eliminar animales a la protectora");

		repositorioAnimales.delete(animal);

		Stream.concat(animal.getImagenes().stream().map(archivo -> Paths.get(archivo.getRuta())),
				animal.getDocumentos().stream().map(documento -> Paths.get(documento.getRuta()))).forEach(path -> {
					try {
						Files.deleteIfExists(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

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
			throw new IllegalArgumentException("El id del admin no debe ser nulo ni estar vacío o en blanco");
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
	public ProtectoraDto getProtectora(String idProtectora, String idAdmin) {
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El id del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		return protectoraMapper.toDTO(protectora);
	}

	@Override
	public void bajaProtectora(String idProtectora, String idAdmin) {

		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El id del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		repositorioProtectoras.delete(protectora);

		repositorioAnimales.findByIdProtectora(idProtectora, Pageable.unpaged()).map(Animal::getId)
				.forEach(id -> deleteAnimal(id, idAdmin));

		List<Usuario> usuarios = repositorioUsuarios.findByIdIn(protectora.getVoluntarios(), Pageable.unpaged())
				.getContent();
		usuarios.forEach(usuario -> usuario.getPermisos().stream()
				.filter(permiso -> permiso.getIdProtectora().equals(idProtectora)).forEach(usuario::removePermiso));
		repositorioUsuarios.saveAll(usuarios);

		protectora.getDocumentos().stream().map(documento -> Paths.get(documento.getRuta())).forEach(path -> {
			try {
				Files.deleteIfExists(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void updateProtectora(ProtectoraDto protectoraDto) {

		if (protectoraDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");

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
		if (protectoraDto.getLogotipo() != null && !protectoraDto.getLogotipo().trim().isEmpty())
			protectora.setLogotipo(protectoraDto.getLogotipo());

		repositorioProtectoras.save(protectora);
	}

	@Override
	public VoluntarioDto verificarAcceso(String idUsuario, String idProtectora) {

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.tieneAcceso(idUsuario))
			throw new AccessDeniedException("El usuario no tiene acceso a la protectora");

		Usuario usuario = findUsuario(idUsuario);

		return usuarioMapper.toVoluntarioDTO(usuario, protectora);
	}

	@Override
	public Page<VoluntarioDto> getVoluntarios(String idProtectora, Pageable pageable) {

		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");

		Protectora protectora = findProtectora(idProtectora);

		return repositorioUsuarios.findByIdIn(protectora.getVoluntarios(), pageable)
				.map(usuario -> usuarioMapper.toVoluntarioDTO(usuario, protectora));
	}

	@Override
	public void addVoluntario(String idProtectora, String nick, String idAdmin) {

		if (nick == null || nick.trim().isEmpty())
			throw new IllegalArgumentException("El nick no debe ser nulo ni estar vacío o en blanco");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El id del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);
		Usuario voluntario = repositorioUsuarios.findByNick(nick)
				.orElseThrow(() -> new UsernameNotFoundException("No existe usuario con nick: " + nick));

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		protectora.addVoluntario(voluntario.getId());
		voluntario.addPermisosDefault(idProtectora);

		repositorioProtectoras.save(protectora);
		repositorioUsuarios.save(voluntario);
	}

	@Override
	public void removeVoluntario(String idProtectora, String idUsuario, String idAdmin) {

		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El id del admin no debe ser nulo ni estar vacío o en blanco");

		Protectora protectora = findProtectora(idProtectora);

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		protectora.removeVoluntario(idUsuario);

		repositorioProtectoras.save(protectora);
	}

	@Override
	public void updatePermisos(VoluntarioDto voluntarioDto, String idAdmin) {

		if (voluntarioDto == null)
			throw new IllegalArgumentException("El DTO no debe ser nulo");
		if (idAdmin == null || idAdmin.trim().isEmpty())
			throw new IllegalArgumentException("El id del admin no debe ser nulo ni estar vacío o en blanco");

		Usuario usuario = findUsuario(voluntarioDto.getId());
		Protectora protectora = findProtectora(voluntarioDto.getIdProtectora());

		if (!protectora.isAdmin(idAdmin))
			throw new AccessDeniedException("El usuario no es administrador de la protectora");

		List<Permiso> permisos = usuario.getPermisos().stream()
				.filter(p -> p.getIdProtectora().equals(protectora.getId())).collect(Collectors.toList());
		List<TipoPermiso> tiposPermiso = permisos.stream().map(Permiso::getTipo).collect(Collectors.toList());
		permisos.stream().filter(permiso -> !voluntarioDto.tienePermiso(permiso.getTipo()))
				.forEach(usuario::removePermiso);
		voluntarioDto.getPermisos().stream().filter(tipo -> !tiposPermiso.contains(tipo))
				.forEach(tipo -> usuario.addPermiso(new Permiso(protectora.getId(), tipo)));

		repositorioUsuarios.save(usuario);
	}

	@Override
	public Page<ResumenAnimalDto> getAnimales(String idProtectora, String categoria, String estado, Pageable pageable) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El id de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (pageable == null)
			throw new IllegalArgumentException("El pageable no debe ser nulo");

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
			throw new IllegalArgumentException("El id de la protectora no debe ser nulo ni estar vacío o en blanco");
		if (animalDto.getRutaPortada() == null || animalDto.getRutaPortada().trim().isEmpty())
			throw new IllegalArgumentException("La ruta de la portada no debe ser nula ni estar vacía o en blanco");
		if (animalDto.getNombre() == null || animalDto.getNombre().trim().isEmpty())
			throw new IllegalArgumentException("El nombre no debe ser nulo ni estar vacío o en blanco");
		if (animalDto.getCategoria() == null)
			throw new IllegalArgumentException("La categoría no debe ser nula");
		if (animalDto.getEstado() == null)
			throw new IllegalArgumentException("El estado no debe ser nulo");
		if (animalDto.getFechaEntrada() == null)
			throw new IllegalArgumentException("La fecha de entrada no debe ser nula");
		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El id del voluntario no debe ser nulo ni estar vacío o en blanco");

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
	public AnimalDto getAnimal(String idAnimal, String idVoluntario) {

		if (idVoluntario == null || idVoluntario.trim().isEmpty())
			throw new IllegalArgumentException("El id del voluntario no debe ser nulo ni estar vacío o en blanco");

		Animal animal = findAnimal(idAnimal);
		Usuario usuario = findUsuario(idVoluntario);
		Protectora protectora = findProtectora(animal.getIdProtectora());

		if (!protectora.isAdmin(idVoluntario) && !usuario.tienePermiso(protectora.getId(), TipoPermiso.READ_ANIMALES))
			throw new AccessDeniedException("El usuario no tiene permiso para ver los datos del animal");

		return animalMapper.toDTO(animal);
	}

	@Override
	public void bajaAnimal(String idAnimal, String idVoluntario) {

		Usuario usuario = findUsuario(idVoluntario);
		Animal animal = deleteAnimal(idAnimal, idVoluntario);

		addActividad(animal.getIdProtectora(), usuario.getNick(), "ha eliminado un animal: " + animal.getNombre());
	}

	@Override
	public void updateAnimal(AnimalDto animalDto, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addImagenAnimal(String idAnimal, String ruta, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeImagenAnimal(String idAnimal, String idImagen, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDocumentoAnimal(String idAnimal, String ruta, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDocumentoAnimal(String idAnimal, String idDocumento, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<TareaDto> getTareas(String idProtectora, Pageable pageable, String idVoluntario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTarea(TareaDto tareaDto, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTarea(String idTarea, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTarea(TareaDto tareaDto, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDocumento(String nombre, String ruta, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDocumento(String idDocumento, String idVoluntario) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<ActividadDto> getHistorial(String idProtectora, Pageable pageable, String idVoluntario) {
		// TODO Auto-generated method stub
		return null;
	}

}
