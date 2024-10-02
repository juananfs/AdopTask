package adoptask.mapper;

import org.springframework.stereotype.Component;

import adoptask.dto.AnimalDto;
import adoptask.dto.PublicacionDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.modelo.Animal;
import adoptask.modelo.DatosAnimal;
import adoptask.modelo.Protectora;

@Component
public class AnimalMapper {

	public Animal toEntity(AnimalDto animalDto) {
		if (animalDto == null) {
			return null;
		}
		DatosAnimal datos = new DatosAnimal.Builder(animalDto.getNombre(), animalDto.getCategoria())
				.raza(animalDto.getRaza())
				.sexo(animalDto.getSexo())
				.fechaNacimiento(animalDto.getFechaNacimiento())
				.peso(animalDto.getPeso())
				.build();
		Animal animal = new Animal.Builder(animalDto.getIdProtectora(), datos, animalDto.getEstado(),
				animalDto.getFechaEntrada(), animalDto.getRutaPortada())
				.descripcion(animalDto.getDescripcion())
				.build();
		return animal;
	}
	
	public AnimalDto toDTO(Animal animal) {
		if (animal == null) {
			return null;
		}
		AnimalDto animalDto = new AnimalDto();
		animalDto.setId(animal.getId());
		animalDto.setIdProtectora(animal.getIdProtectora());
		animalDto.setPortada(animal.getPortada());
		animalDto.setNombre(animal.getNombre());
		animalDto.setCategoria(animal.getCategoria());
		animalDto.setRaza(animal.getRaza());
		animalDto.setSexo(animal.getSexo());
		animalDto.setFechaNacimiento(animal.getFechaNacimiento());
		animalDto.setPeso(animal.getPeso());
		animalDto.setEstado(animal.getEstado());
		animalDto.setFechaEntrada(animal.getFechaEntrada());
		animalDto.setDescripcion(animal.getDescripcion());
		animalDto.setCamposAdicionales(animal.getCamposAdicionales());
		return animalDto;
	}
	
	public ResumenAnimalDto toResumenDTO(Animal animal) {
		if (animal == null) {
			return null;
		}
		ResumenAnimalDto resumenAnimalDto = new ResumenAnimalDto();
		resumenAnimalDto.setId(animal.getId());
		resumenAnimalDto.setImagen(animal.getRutaPortada());
		resumenAnimalDto.setNombre(animal.getNombre());
		return resumenAnimalDto;
	}
	
	public PublicacionDto toPublicacionDTO(Animal animal, Protectora protectora) {
		if (animal == null || protectora == null) {
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
		publicacionDto.setFecha(animal.getFechaPublicacion());
		publicacionDto.setLikes(animal.getLikes());
		publicacionDto.setEmail(protectora.getEmail());
		publicacionDto.setUbicacion(protectora.getUbicacion());
		publicacionDto.setTelefono(protectora.getTelefono());
		publicacionDto.setWeb(protectora.getWeb());
		publicacionDto.setImagenes(animal.getImagenes());
		return publicacionDto;
	}
}
