package adoptask.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Usuario;

@NoRepositoryBean
public interface RepositorioUsuarios
		extends PagingAndSortingRepository<Usuario, String>, CrudRepository<Usuario, String> {

	Optional<Usuario> findByNick(String nick);
}
