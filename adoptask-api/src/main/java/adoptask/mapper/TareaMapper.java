package adoptask.mapper;

import org.springframework.stereotype.Component;

import adoptask.dto.TareaDto;
import adoptask.modelo.Tarea;
import adoptask.modelo.Usuario;

@Component
public class TareaMapper {

	public Tarea toEntity(TareaDto tareaDto) {
		if (tareaDto == null) {
			return null;
		}
		Tarea tarea = new Tarea(tareaDto.getIdProtectora(), tareaDto.getTitulo(), tareaDto.getDescripcion(),
				tareaDto.getPrioridad(), tareaDto.getIdEncargado());
		return tarea;
	}

	public TareaDto toDTO(Tarea tarea, Usuario encargado) {
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
		tareaDto.setIdEncargado(tarea.getIdEncargado());
		tareaDto.setNickEncargado(encargado.getNick());
		return tareaDto;
	}
}
