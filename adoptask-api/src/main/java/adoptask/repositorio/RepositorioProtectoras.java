package adoptask.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Protectora;

@NoRepositoryBean
public interface RepositorioProtectoras
		extends PagingAndSortingRepository<Protectora, String>, CrudRepository<Protectora, String> {

	Optional<Protectora> findByIdAdmin(String idAdmin);
}
