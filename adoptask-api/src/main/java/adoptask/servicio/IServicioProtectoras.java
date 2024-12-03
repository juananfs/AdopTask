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

	static final String DIRECTORIO_PROTECTORA = "protectoras/%s/";
	static final String DIRECTORIO_ANIMALES = "protectoras/%s/animales/";
	static final String DIRECTORIO_ANIMAL = "protectoras/%s/animales/%s/";
	static final String DIRECTORIO_IMAGENES_ANIMAL = "protectoras/%s/animales/%s/imagenes/";
	static final String DIRECTORIO_DOCUMENTOS_ANIMAL = "protectoras/%s/animales/%s/documentos/";
	static final String DIRECTORIO_DOCUMENTOS = "protectoras/%s/documentos/";

	Page<ResumenProtectoraDto> getProtectoras(Pageable pageable);

	String altaProtectora(ProtectoraDto protectoraDto);

	void altaProtectoraLogo(String idProtectora, String nombreLogo, String idAdmin);

	ProtectoraDto getProtectora(String idProtectora, String idAdmin);

	void bajaProtectora(String idProtectora, String idAdmin);

	void updateProtectora(ProtectoraDto protectoraDto);

	VoluntarioDto verificarAcceso(String idUsuario, String idProtectora);

	Page<VoluntarioDto> getVoluntarios(String idProtectora, Pageable pageable, String idAdmin);

	String addVoluntario(String idProtectora, String nick, String idAdmin);

	void removeVoluntario(String idProtectora, String idVoluntario, String idAdmin);

	void updatePermisos(VoluntarioDto voluntarioDto, String idAdmin);

	Page<ResumenAnimalDto> getAnimales(String idProtectora, String categoria, String estado, Pageable pageable,
			String idVoluntario);

	String altaAnimal(AnimalDto animalDto, String idVoluntario);

	void altaAnimalPortada(String idAnimal, String nombrePortada, String idVoluntario);

	AnimalDto getAnimal(String idAnimal, String idVoluntario);

	void bajaAnimal(String idAnimal, String idVoluntario);

	void updateAnimal(AnimalDto animalDto, String idVoluntario);

	void addImagenAnimal(String idAnimal, String nombre, String idVoluntario);

	void removeImagenAnimal(String idAnimal, String nombre, String idVoluntario);

	void addDocumentoAnimal(String idAnimal, String nombre, String idVoluntario);

	void removeDocumentoAnimal(String idAnimal, String nombre, String idVoluntario);

	Page<TareaDto> getTareas(String idProtectora, String estado, Pageable pageable, String idVoluntario);

	String addTarea(TareaDto tareaDto, String idVoluntario);

	void removeTarea(String idTarea, String idVoluntario);

	void updateTarea(TareaDto tareaDto, String idVoluntario);

	List<DocumentoDto> getDocumentos(String idProtectora, String idVoluntario);

	void addDocumento(String idProtectora, String nombre, String idVoluntario);

	void removeDocumento(String idProtectora, String nombre, String idVoluntario);

	Page<ActividadDto> getHistorial(String idProtectora, Pageable pageable, String idVoluntario);

}
