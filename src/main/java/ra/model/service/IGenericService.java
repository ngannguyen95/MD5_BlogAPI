package ra.model.service;

import java.util.List;
import java.util.Optional;

public interface IGenericService<E,T> {
    Iterable<E> findAll();
    Optional<E> findById(T id);
    Optional<E> save(E e);
    void deleteById(T id);
}
