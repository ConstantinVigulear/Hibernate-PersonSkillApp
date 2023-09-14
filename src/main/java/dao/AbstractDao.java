package dao;

import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;

public abstract class AbstractDao<T> implements Dao<T> {

  private Session session;

  @Override
  public void persist(T t) {
    session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.persist(t);

    if (session != null) {
      session.getTransaction().commit();
      session.close();
    }
  }

  @Override
  public void persistAll(List<T> t) {
    t.forEach(this::persist);
  }

  @Override
  public void update(T t) {
    session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    session.merge(t);

    if (session != null) {
      session.getTransaction().commit();
      session.close();
    }
  }

  @Override
  public void delete(T t) {

    T objectToDelete = get(t);
    if (objectToDelete != null) {
      session = HibernateUtil.getSessionFactory().openSession();
      session.beginTransaction();
      session.remove(objectToDelete);

      if (session != null) {
        session.getTransaction().commit();
        session.close();
      }
    }
  }

  @Override
  public void deleteAll(List<T> t) {
    t.forEach(this::delete);
  }
}
