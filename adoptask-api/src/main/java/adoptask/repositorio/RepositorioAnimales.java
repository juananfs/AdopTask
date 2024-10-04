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
import adoptask.modelo.SexoAnimal;

@NoRepositoryBean
public interface RepositorioAnimales
		extends PagingAndSortingRepository<Animal, String>, CrudRepository<Animal, String> {

	Optional<Animal> findPublicacion(String id);

	Page<Animal> findPublicaciones(String nombre, List<CategoriaAnimal> categorias, List<SexoAnimal> sexos,
			List<String> protectoras, Pageable pageable);

	Page<Animal> findPublicaciones(List<String> ids, Pageable pageable);
}
