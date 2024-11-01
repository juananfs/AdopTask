package adoptask.mapper;

import org.springframework.stereotype.Component;

import adoptask.dto.ProtectoraDto;
import adoptask.dto.ResumenProtectoraDto;
import adoptask.modelo.DatosContacto;
import adoptask.modelo.Protectora;

@Component
public class ProtectoraMapper {

	public Protectora toEntity(ProtectoraDto protectoraDto) {
		if (protectoraDto == null) {
			return null;
		}
		DatosContacto contacto = new DatosContacto(protectoraDto.getNif(), protectoraDto.getEmail(),
				protectoraDto.getUbicacion(), protectoraDto.getTelefono(), protectoraDto.getWeb());
		Protectora protectora = new Protectora(protectoraDto.getIdAdmin(), protectoraDto.getNombre(), contacto,
				protectoraDto.getDescripcion(), protectoraDto.getLogotipo());
		return protectora;
	}

	public ProtectoraDto toDTO(Protectora protectora) {
		if (protectora == null) {
			return null;
		}
		ProtectoraDto protectoraDto = new ProtectoraDto();
		protectoraDto.setId(protectora.getId());
		protectoraDto.setIdAdmin(protectora.getIdAdmin());
		protectoraDto.setNombre(protectora.getNombre());
		protectoraDto.setNif(protectora.getNif());
		protectoraDto.setEmail(protectora.getEmail());
		protectoraDto.setUbicacion(protectora.getUbicacion());
		protectoraDto.setTelefono(protectora.getTelefono());
		protectoraDto.setWeb(protectora.getWeb());
		protectoraDto.setDescripcion(protectora.getDescripcion());
		protectoraDto.setLogotipo(protectora.getLogotipo());
		return protectoraDto;
	}

	public ResumenProtectoraDto toResumenDTO(Protectora protectora) {
		if (protectora == null) {
			return null;
		}
		ResumenProtectoraDto resumenProtectoraDto = new ResumenProtectoraDto();
		resumenProtectoraDto.setId(protectora.getId());
		resumenProtectoraDto.setLogotipo(protectora.getLogotipo());
		resumenProtectoraDto.setNombre(protectora.getNombre());
		resumenProtectoraDto.setDescripcion(protectora.getDescripcion());
		resumenProtectoraDto.setEmail(protectora.getEmail());
		resumenProtectoraDto.setUbicacion(protectora.getUbicacion());
		resumenProtectoraDto.setTelefono(protectora.getTelefono());
		resumenProtectoraDto.setWeb(protectora.getWeb());
		return resumenProtectoraDto;
	}
}
