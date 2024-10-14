package adoptask.servicio;

import java.util.List;

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

public interface IServicioProtectoras {

	Page<ResumenProtectoraDto> getProtectoras(Pageable pageable);

	String altaProtectora(ProtectoraDto protectoraDto);

	ProtectoraDto getProtectora(String idProtectora, String idAdmin);

	void bajaProtectora(String idProtectora, String idAdmin);

	void updateProtectora(ProtectoraDto protectoraDto);

	VoluntarioDto verificarAcceso(String idUsuario, String idProtectora);

	Page<VoluntarioDto> getVoluntarios(String idProtectora, Pageable pageable, String idAdmin);

	void addVoluntario(String idProtectora, String nick, String idAdmin);

	void removeVoluntario(String idProtectora, String idUsuario, String idAdmin);

	void updatePermisos(VoluntarioDto voluntarioDto, String idAdmin);

	Page<ResumenAnimalDto> getAnimales(String idProtectora, String categoria, String estado, Pageable pageable);

	String altaAnimal(AnimalDto animalDto, String idVoluntario);

	AnimalDto getAnimal(String idAnimal, String idVoluntario);

	void bajaAnimal(String idAnimal, String idVoluntario);

	void updateAnimal(AnimalDto animalDto, String idVoluntario);

	String addImagenAnimal(String idAnimal, String ruta, String idVoluntario);

	void removeImagenAnimal(String idAnimal, String idImagen, String idVoluntario);

	String addDocumentoAnimal(String idAnimal, String nombre, String ruta, String idVoluntario);

	void removeDocumentoAnimal(String idAnimal, String idDocumento, String idVoluntario);

	Page<TareaDto> getTareas(String idProtectora, Pageable pageable, String idVoluntario);

	String addTarea(TareaDto tareaDto, String idVoluntario);

	void removeTarea(String idTarea, String idVoluntario);

	void updateTarea(TareaDto tareaDto, String idVoluntario);

	List<DocumentoDto> getDocumentos(String idProtectora, String idVoluntario);

	String addDocumento(String idProtectora, String nombre, String ruta, String idVoluntario);

	void removeDocumento(String idProtectora, String idDocumento, String idVoluntario);

	Page<ActividadDto> getHistorial(String idProtectora, Pageable pageable, String idVoluntario);

}
