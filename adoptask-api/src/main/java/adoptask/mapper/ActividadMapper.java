package adoptask.mapper;

import adoptask.dto.ActividadDto;
import adoptask.modelo.Actividad;

public class ActividadMapper {

	public static ActividadDto toDTO(Actividad actividad) {
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
