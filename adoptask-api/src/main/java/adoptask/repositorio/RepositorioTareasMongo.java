package adoptask.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import adoptask.modelo.Tarea;

public interface RepositorioTareasMongo extends RepositorioTareas, MongoRepository<Tarea, String> {

}