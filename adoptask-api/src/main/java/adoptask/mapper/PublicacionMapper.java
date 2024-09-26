package adoptask.mapper;

import adoptask.dto.PublicacionDto;
import adoptask.modelo.Animal;
import adoptask.modelo.Protectora;
import adoptask.modelo.Publicacion;

public class PublicacionMapper {

	public static PublicacionDto toDTO(Publicacion publicacion, Animal animal, Protectora protectora) {
		if (publicacion == null || animal == null || protectora == null) {
			return null;
		}
		PublicacionDto publicacionDto = new PublicacionDto();
		publicacionDto.setPortada(animal.getPortada());
		publicacionDto.setNombre(animal.getNombre());
		publicacionDto.setDescripcion(animal.getDescripcion());
		publicacionDto.setCategoria(animal.getCategoria());
		publicacionDto.setRaza(animal.getRaza());
		publicacionDto.setEdad(animal.getEdad());
		publicacionDto.setSexo(animal.getSexo());
		publicacionDto.setPeso(animal.getPeso());
		publicacionDto.setCamposAdicionales(animal.getCamposAdicionales());
		publicacionDto.setFecha(publicacion.getFecha());
		publicacionDto.setLikes(publicacion.getLikes());
		publicacionDto.setEmail(protectora.getEmail());
		publicacionDto.setUbicacion(protectora.getUbicacion());
		publicacionDto.setTelefono(protectora.getTelefono());
		publicacionDto.setWeb(protectora.getWeb());
		publicacionDto.setImagenes(animal.getImagenes());
		return publicacionDto;
	}
}
