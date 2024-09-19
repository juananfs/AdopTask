package adoptask.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import adoptask.modelo.Publicacion;

public interface RepositorioPublicacionesMongo extends RepositorioPublicaciones, MongoRepository<Publicacion, String> {

}
