package adoptask.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import adoptask.modelo.Actividad;

public interface RepositorioActividadesMongo extends RepositorioActividades, MongoRepository<Actividad, String> {

}
