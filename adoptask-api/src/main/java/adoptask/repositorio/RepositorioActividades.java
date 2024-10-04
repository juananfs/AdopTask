package adoptask.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import adoptask.modelo.Actividad;

public interface RepositorioActividades
		extends PagingAndSortingRepository<Actividad, String>, CrudRepository<Actividad, String> {

}
