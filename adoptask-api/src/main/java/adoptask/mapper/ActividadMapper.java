package adoptask.mapper;

import org.springframework.stereotype.Component;

import adoptask.dto.ActividadDto;
import adoptask.modelo.Actividad;

@Component
public class ActividadMapper {

	public ActividadDto toDTO(Actividad actividad) {
		if (actividad == null) {
			return null;
		}
		ActividadDto actividadDto = new ActividadDto();
		actividadDto.setFecha(actividad.getFecha());
		actividadDto.setNick(actividad.getNick());
		actividadDto.setAccion(actividad.getAccion());
		return actividadDto;
	}

}
