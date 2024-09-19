package adoptask.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import adoptask.modelo.Usuario;

public interface RepositorioUsuariosMongo extends RepositorioUsuarios, MongoRepository<Usuario, String> {

}
