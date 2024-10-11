package adoptask.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Actividad;

public interface RepositorioActividades
		extends PagingAndSortingRepository<Actividad, String>, CrudRepository<Actividad, String> {

	Page<Actividad> findByIdProtectora(String idProtectora, Pageable pageable);
}
