package adoptask.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Tarea;

@NoRepositoryBean
public interface RepositorioTareas extends PagingAndSortingRepository<Tarea, String>, CrudRepository<Tarea, String> {

}