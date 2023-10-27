package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.Person;
import model.Skill;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl extends AbstractDao<Person> implements PersonDao {
  private EntityManager entityManager;

  @Override
  public Person get(Long id) {


    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    Person person = entityManager.find(Person.class, id);
    entityManager.close();

    return person;
  }

  @Override
  public Person get(Person person) {

    Person resultPerson = null;

    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();

    String hql = "FROM Person P WHERE P.name = :name and P.surname = :surname and P.email = :email";
    Query query = entityManager.createQuery(hql);
    query.setParameter("name", person.getName());
    query.setParameter("surname", person.getSurname());
    query.setParameter("email", person.getEmail());

    if (!query.getResultList().isEmpty()) {
      resultPerson = (Person) query.getResultList().get(0);
    }

    entityManager.close();

    return resultPerson;
  }

  @Override
  public List<Person> getAll() {

    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();

    List<Person> people = entityManager.createQuery("from Person ", Person.class).getResultList();

    entityManager.close();
    return people;
  }

  @Override
  public void persist(Person person) {
    if (get(person) == null && person.isValid()) {
      super.persist(person);
    } else {
      super.update(person);
    }
  }

  @Override
  public void persistAll(List<Person> t) {
    super.persistAll(t);
  }

  @Override
  public Person update(Person person) {
    return super.update(person);
  }

  @Override
  public void delete(Person person) {
    List<Skill> skills = new ArrayList<>(person.getSkills());

    for (Skill skill : skills) {
      skill.removePerson(person);
    }
    super.delete(person);
  }

  @Override
  public void deleteAll(List<Person> t) {
    super.deleteAll(t);
  }
}
