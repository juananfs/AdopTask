package adoptask.repositorio;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Animal;

@NoRepositoryBean
public interface RepositorioAnimales extends PagingAndSortingRepository<Animal, String> {

}
