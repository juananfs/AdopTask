package adoptask.repositorio;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Usuario;

@NoRepositoryBean
public interface RepositorioUsuarios extends PagingAndSortingRepository<Usuario, String> {

}
