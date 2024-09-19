package adoptask.repositorio;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Publicacion;

@NoRepositoryBean
public interface RepositorioPublicaciones extends PagingAndSortingRepository<Publicacion, String> {

}
