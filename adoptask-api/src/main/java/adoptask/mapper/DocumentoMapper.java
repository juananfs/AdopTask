package adoptask.mapper;

import org.springframework.stereotype.Component;

import adoptask.dto.DocumentoDto;
import adoptask.modelo.Documento;

@Component
public class DocumentoMapper {
	public DocumentoDto toDTO(Documento documento) {
		if (documento == null) {
			return null;
		}
		DocumentoDto documentoDto = new DocumentoDto();
		documentoDto.setNombre(documento.getNombre());
		documentoDto.setFecha(documento.getFecha());
		return documentoDto;
	}
}