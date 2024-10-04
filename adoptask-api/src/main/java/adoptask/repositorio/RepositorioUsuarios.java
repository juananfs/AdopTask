package adoptask.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Usuario;

@NoRepositoryBean
public interface RepositorioUsuarios
		extends PagingAndSortingRepository<Usuario, String>, CrudRepository<Usuario, String> {

	Optional<Usuario> findByNick(String nick);

	Page<Usuario> findByIdIn(List<String> ids, Pageable pageable);
}
