package adoptask.repositorio;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Protectora;

@NoRepositoryBean
public interface RepositorioProtectoras extends PagingAndSortingRepository<Protectora, String> {

}
