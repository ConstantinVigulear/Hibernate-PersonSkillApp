package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import utils.HibernateUtil;

import java.util.List;

public abstract class AbstractDao<T> implements Dao<T> {

  EntityManager entityManager;

  @Override
  public void persist(T t) {
    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(t);

    if (entityManager != null) {
      entityManager.getTransaction().commit();
      entityManager.close();
    }
  }

  @Override
  public void persistAll(List<T> t) {
    t.forEach(this::persist);
  }

  @Override
  public T update(T t) {
    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();

    T returnedT = null;

    try {
      returnedT = entityManager.merge(t);
      entityManager.getTransaction().commit();
      entityManager.close();
    } catch (PersistenceException exception) {
        exception.printStackTrace();
    }
    return returnedT;
  }

  @Override
  public void delete(T t) {

    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();

    entityManager.remove(t);

    if (entityManager != null) {
      entityManager.getTransaction().commit();
      entityManager.close();
    }
  }

  @Override
  public void deleteAll(List<T> t) {
    t.forEach(this::delete);
  }
}
