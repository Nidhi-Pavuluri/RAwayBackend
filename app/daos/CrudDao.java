package daos;


import java.util.Collection;
import java.util.Optional;

public interface CrudDao<E, K> {

    E create(E entity);
    Optional<E> read(K id);
    E update(E entity);
    E delete(K id);
    Collection<E> all();

}
