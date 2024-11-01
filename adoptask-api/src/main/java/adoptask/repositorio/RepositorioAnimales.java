package adoptask.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Animal;
import adoptask.modelo.CategoriaAnimal;
import adoptask.modelo.EstadoAnimal;
import adoptask.modelo.SexoAnimal;

@NoRepositoryBean
public interface RepositorioAnimales
		extends PagingAndSortingRepository<Animal, String>, CrudRepository<Animal, String> {

	Optional<Animal> findPublicacion(String id);

	Page<Animal> findPublicaciones(String nombre, List<CategoriaAnimal> categorias, List<SexoAnimal> sexos,
			List<String> protectoras, Pageable pageable);

	Page<Animal> findPublicacionesSinCategorias(String nombre, List<SexoAnimal> sexos, List<String> protectoras,
			Pageable pageable);

	Page<Animal> findPublicacionesSinSexos(String nombre, List<CategoriaAnimal> categorias, List<String> protectoras,
			Pageable pageable);

	Page<Animal> findPublicacionesSinProtectoras(String nombre, List<CategoriaAnimal> categorias,
			List<SexoAnimal> sexos, Pageable pageable);

	Page<Animal> findPublicacionesSoloProtectoras(String nombre, List<String> protectoras, Pageable pageable);

	Page<Animal> findPublicacionesSoloSexos(String nombre, List<SexoAnimal> sexos, Pageable pageable);

	Page<Animal> findPublicacionesSoloCategorias(String nombre, List<CategoriaAnimal> categorias, Pageable pageable);

	Page<Animal> findPublicacionesSinFiltros(String nombre, Pageable pageable);

	Page<Animal> findPublicacionesByIdIn(List<String> ids, Pageable pageable);

	Page<Animal> findByIdProtectoraAndDatosCategoriaAndEstado(String idProtectora, CategoriaAnimal categoria,
			EstadoAnimal estado, Pageable pageable);

	Page<Animal> findByIdProtectoraAndDatosCategoria(String idProtectora, CategoriaAnimal categoria, Pageable pageable);

	Page<Animal> findByIdProtectoraAndEstado(String idProtectora, EstadoAnimal estado, Pageable pageable);

	Page<Animal> findByIdProtectora(String idProtectora, Pageable pageable);
}
