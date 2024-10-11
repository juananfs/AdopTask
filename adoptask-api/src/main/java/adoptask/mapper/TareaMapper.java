package adoptask.mapper;

import org.springframework.stereotype.Component;

import adoptask.dto.TareaDto;
import adoptask.modelo.Tarea;

@Component
public class TareaMapper {

	public Tarea toEntity(TareaDto tareaDto) {
		if (tareaDto == null) {
			return null;
		}
		Tarea tarea = new Tarea(tareaDto.getIdProtectora(), tareaDto.getTitulo(), tareaDto.getDescripcion(),
				tareaDto.getPrioridad());
		return tarea;
	}

	public TareaDto toDTO(Tarea tarea) {
		if (tarea == null) {
			return null;
		}
		TareaDto tareaDto = new TareaDto();
		tareaDto.setId(tarea.getId());
		tareaDto.setIdProtectora(tarea.getIdProtectora());
		tareaDto.setTitulo(tarea.getTitulo());
		tareaDto.setDescripcion(tarea.getDescripcion());
		tareaDto.setPrioridad(tarea.getPrioridad());
		tareaDto.setEstado(tarea.getEstado());
		tareaDto.setEncargado(tarea.getEncargado());
		return tareaDto;
	}
}
