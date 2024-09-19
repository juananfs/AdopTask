package adoptask.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import adoptask.modelo.Protectora;

public interface RepositorioProtectorasMongo extends RepositorioProtectoras, MongoRepository<Protectora, String> {

}
