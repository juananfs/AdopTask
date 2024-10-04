package adoptask.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
import adoptask.modelo.Protectora;
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

		Protectora protectora = protectoraMapper.toEntity(protectoraDto);

		return repositorioProtectoras.save(protectora).getId();
	}

	@Override
	public ProtectoraDto getProtectora(String idProtectora) {

		Protectora protectora = findProtectora(idProtectora);

		return protectoraMapper.toDTO(protectora);
	}

	@Override
	public void bajaProtectora(String idProtectora) {

		if (idProtectora == null || idProtectora.trim().isEmpty())
			throw new IllegalArgumentException("El id de la protectora no debe ser nulo ni estar vacío o en blanco");

		repositorioProtectoras.deleteById(idProtectora);

	}

	@Override
	public void updateProtectora(ProtectoraDto protectoraDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verificarAcceso(String idUsuario, String idProtectora) {

		Protectora protectora = findProtectora(idProtectora);

		return protectora.tieneAcceso(idUsuario);
	}

	@Override
	public Page<VoluntarioDto> getVoluntarios(String idProtectora, Pageable pageable) {

		Protectora protectora = findProtectora(idProtectora);

		return repositorioUsuarios.findByIdIn(protectora.getVoluntarios(), pageable)
				.map(usuario -> usuarioMapper.toVoluntarioDTO(usuario, idProtectora));
	}

	@Override
	public void addVoluntario(String idProtectora, String nick) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeVoluntario(String idProtectora, String idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePermisos(String idProtectora, VoluntarioDto voluntarioDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<ResumenAnimalDto> getAnimales(String idProtectora, String categoria, String estado, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String altaAnimal(AnimalDto animalDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnimalDto getAnimal(String idAnimal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAnimal(String idAnimal, String idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAnimal(AnimalDto animalDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addImagenAnimal(String idAnimal, String ruta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeImagenAnimal(String idAnimal, String idImagen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDocumentoAnimal(String idAnimal, String ruta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDocumentoAnimal(String idAnimal, String idDocumento) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<TareaDto> getTareas(String idProtectora, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTarea(TareaDto tareaDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTarea(String idTarea) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTarea(TareaDto tareaDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDocumento(String nombre, String ruta, String idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDocumento(String idDocumento, String idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<ActividadDto> getHistorial(String idProtectora, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
