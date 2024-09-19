package adoptask.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import adoptask.modelo.Animal;

public interface RepositorioAnimalesMongo extends RepositorioAnimales, MongoRepository<Animal, String> {

}
