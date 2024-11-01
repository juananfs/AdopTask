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

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' }, 'datos.sexo': { $in: ?2 }, 'idProtectora': { $in: ?3 } }")
	Page<Animal> findPublicacionesSinCategorias(String nombre, List<SexoAnimal> sexos, List<String> protectoras,
			Pageable pageable);

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' }, 'datos.categoria': { $in: ?1 }, 'idProtectora': { $in: ?3 } }")
	Page<Animal> findPublicacionesSinSexos(String nombre, List<CategoriaAnimal> categorias, List<String> protectoras,
			Pageable pageable);

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' }, 'datos.categoria': { $in: ?1 }, 'datos.sexo': { $in: ?2 } }")
	Page<Animal> findPublicacionesSinProtectoras(String nombre, List<CategoriaAnimal> categorias,
			List<SexoAnimal> sexos, Pageable pageable);

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' }, 'idProtectora': { $in: ?3 } }")
	Page<Animal> findPublicacionesSoloProtectoras(String nombre, List<String> protectoras, Pageable pageable);

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' }, 'datos.sexo': { $in: ?2 } }")
	Page<Animal> findPublicacionesSoloSexos(String nombre, List<SexoAnimal> sexos, Pageable pageable);

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' }, 'datos.categoria': { $in: ?1 } }")
	Page<Animal> findPublicacionesSoloCategorias(String nombre, List<CategoriaAnimal> categorias, Pageable pageable);

	@Query("{ 'estado': 'EN_ADOPCION', 'datos.nombre': { $regex: ?0, $options: 'i' } }")
	Page<Animal> findPublicacionesSinFiltros(String nombre, Pageable pageable);

	@Query("{ 'id': { $in: ?0 }, 'estado': 'EN_ADOPCION' }")
	Page<Animal> findPublicacionesByIdIn(List<String> ids, Pageable pageable);
}
