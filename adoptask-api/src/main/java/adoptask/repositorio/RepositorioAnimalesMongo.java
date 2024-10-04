package adoptask.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import adoptask.modelo.Animal;
import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.SexoAnimal;

public interface RepositorioAnimalesMongo extends RepositorioAnimales, MongoRepository<Animal, String> {

	@Query("{ 'id': ?0, 'estado': 'EN_ADOPCION' }")
	Optional<Animal> findPublicacion(String id);

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' }, 'datos.categoria': { $in: ?1 }, 'datos.sexo': { $in: ?2 }, 'idProtectora': { $in: ?3 } }")
	Page<Animal> findPublicaciones(String nombre, List<CategoriaAnimal> categorias, List<SexoAnimal> sexos,
			List<String> protectoras, Pageable pageable);

	@Query("{ 'id': { $in: ?0 }, 'estado': 'EN_ADOPCION' }")
	Page<Animal> findPublicaciones(List<String> ids, Pageable pageable);
}
