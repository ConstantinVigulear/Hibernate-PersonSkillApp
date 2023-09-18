package services;

import dao.Dao;
import dao.PersonDaoImpl;
import jakarta.transaction.Transactional;
import model.Person;

import java.util.List;

@Transactional
public class PersonServiceImpl implements PersonService {

  private final Dao<Person> personDao = new PersonDaoImpl();

  @Override
  public void persist(Person entity) {
    personDao.persist(entity);
  }

  @Override
  public void persistAll(List<Person> entities) {
    personDao.persistAll(entities);
  }

  @Override
  public Person update(Person entity) {
    return personDao.update(entity);
  }

  @Override
  public Person findById(Long id) {
    return personDao.get(id);
  }

  @Override
  public Person findByEntity(Person person) {
    return personDao.get(person);
  }

  @Override
  public void delete(Person entity) {
    personDao.delete(entity);
  }

  @Override
  public List<Person> findAll() {
    return personDao.getAll();
  }

  @Override
  public void deleteAll(List<Person> entities) {
    personDao.deleteAll(entities);
  }
}
