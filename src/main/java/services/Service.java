package services;

import java.util.List;

public interface Service<T> {

    public void persist(T entity);

    public void persistAll(List<T> entities);

    public void update(T entity);

    public T findById(Long id);
    public T findByEntity(T t);

    public void delete(T entity);

    public List<T> findAll();

    public void deleteAll(List<T> entities);
}
