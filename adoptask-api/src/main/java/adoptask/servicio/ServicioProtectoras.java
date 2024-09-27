package adoptask.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
import adoptask.repositorio.RepositorioAnimales;
import adoptask.repositorio.RepositorioProtectoras;
import adoptask.repositorio.RepositorioUsuarios;

public class ServicioProtectoras implements IServicioProtectoras {

	private RepositorioProtectoras repositorioProtectoras;
	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioAnimales repositorioAnimales;

	private ProtectoraMapper protectoraMapper;
	private UsuarioMapper usuarioMapper;
	private AnimalMapper animalMapper;
	private TareaMapper tareaMapper;
	private DocumentoMapper documentoMapper;
	private ActividadMapper actividadMapper;

	@Autowired
	public ServicioProtectoras(RepositorioProtectoras repositorioProtectoras, RepositorioUsuarios repositorioUsuarios,
			RepositorioAnimales repositorioAnimales, ProtectoraMapper protectoraMapper, UsuarioMapper usuarioMapper,
			AnimalMapper animalMapper, TareaMapper tareaMapper, DocumentoMapper documentoMapper,
			ActividadMapper actividadMapper) {
		super();
		this.repositorioProtectoras = repositorioProtectoras;
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioAnimales = repositorioAnimales;
		this.protectoraMapper = protectoraMapper;
		this.usuarioMapper = usuarioMapper;
		this.animalMapper = animalMapper;
		this.tareaMapper = tareaMapper;
		this.documentoMapper = documentoMapper;
		this.actividadMapper = actividadMapper;
	}

	@Override
	public Page<ResumenProtectoraDto> getProtectoras(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String altaProtectora(ProtectoraDto protectoraDto, String logotipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProtectoraDto getProtectora(String idProtectora) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bajaProtectora(String idProtectora) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProtectora(ProtectoraDto protectoraDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verificarAcceso(String idUsuario, String idProtectora) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<VoluntarioDto> getVoluntarios(String idProtectora, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
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
	public List<DocumentoDto> getDocumentos(String idProtectora) {
		// TODO Auto-generated method stub
		return null;
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
	public List<ActividadDto> getHistorial(String idProtectora) {
		// TODO Auto-generated method stub
		return null;
	}

	private void addActividad(String idProtectora, String nick, String accion) {

	}

}
