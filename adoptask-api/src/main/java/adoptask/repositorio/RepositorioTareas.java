package adoptask.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.EstadoTarea;
import adoptask.modelo.Tarea;

@NoRepositoryBean
public interface RepositorioTareas extends PagingAndSortingRepository<Tarea, String>, CrudRepository<Tarea, String> {

	Page<Tarea> findByIdProtectora(String idProtectora, Pageable pageable);

	Page<Tarea> findByIdProtectoraAndEstado(String idProtectora, EstadoTarea estado, Pageable pageable);

}