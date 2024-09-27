package adoptask.mapper;

import org.springframework.stereotype.Component;

import adoptask.dto.AnimalDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.modelo.Animal;
import adoptask.modelo.DatosAnimal;

@Component
public class AnimalMapper {

	public static Animal toEntity(AnimalDto animalDto) {
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
	
	public static AnimalDto toDTO(Animal animal) {
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
	
	public static ResumenAnimalDto toResumenDTO(Animal animal) {
		if (animal == null) {
			return null;
		}
		ResumenAnimalDto resumenAnimalDto = new ResumenAnimalDto();
		resumenAnimalDto.setId(animal.getId());
		resumenAnimalDto.setImagen(animal.getRutaPortada());
		resumenAnimalDto.setNombre(animal.getNombre());
		return resumenAnimalDto;
	}
}
