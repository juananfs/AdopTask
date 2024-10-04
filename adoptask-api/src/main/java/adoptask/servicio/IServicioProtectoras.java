package adoptask.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import adoptask.dto.ActividadDto;
import adoptask.dto.AnimalDto;
import adoptask.dto.ProtectoraDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.ResumenProtectoraDto;
import adoptask.dto.TareaDto;
import adoptask.dto.VoluntarioDto;

public interface IServicioProtectoras {

	Page<ResumenProtectoraDto> getProtectoras(Pageable pageable);

	String altaProtectora(ProtectoraDto protectoraDto);

	ProtectoraDto getProtectora(String idProtectora);

	void bajaProtectora(String idProtectora);

	void updateProtectora(ProtectoraDto protectoraDto);

	boolean verificarAcceso(String idUsuario, String idProtectora);

	Page<VoluntarioDto> getVoluntarios(String idProtectora, Pageable pageable);

	void addVoluntario(String idProtectora, String nick);

	void removeVoluntario(String idProtectora, String idUsuario);

	void updatePermisos(String idProtectora, VoluntarioDto voluntarioDto);

	Page<ResumenAnimalDto> getAnimales(String idProtectora, String categoria, String estado, Pageable pageable);

	String altaAnimal(AnimalDto animalDto);

	AnimalDto getAnimal(String idAnimal);

	void deleteAnimal(String idAnimal, String idUsuario);

	void updateAnimal(AnimalDto animalDto);

	void addImagenAnimal(String idAnimal, String ruta);

	void removeImagenAnimal(String idAnimal, String idImagen);

	void addDocumentoAnimal(String idAnimal, String ruta);

	void removeDocumentoAnimal(String idAnimal, String idDocumento);

	Page<TareaDto> getTareas(String idProtectora, Pageable pageable);

	void addTarea(TareaDto tareaDto);

	void removeTarea(String idTarea);

	void updateTarea(TareaDto tareaDto);

	void addDocumento(String nombre, String ruta, String idUsuario);

	void removeDocumento(String idDocumento, String idUsuario);

	Page<ActividadDto> getHistorial(String idProtectora, Pageable pageable);

}
