package dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Person;
import model.Skill;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl extends AbstractDao<Person> implements PersonDao {
  private Session session;

  @Override
  public Person get(Long id) {

    Person resultPerson;

    session = HibernateUtil.getSessionFactory().openSession();
    resultPerson = session.get(Person.class, id);

    if (session != null) {
      session.close();
    }

    return resultPerson;
  }

  @Override
  public Person get(Person person) {

    Person resultPerson = null;

    session = HibernateUtil.getSessionFactory().openSession();

    String hql = "FROM Person P WHERE P.name = :name and P.surname = :surname and P.email = :email";
    SelectionQuery<?> query = session.createSelectionQuery(hql);
    query.setParameter("name", person.getName());
    query.setParameter("surname", person.getSurname());
    query.setParameter("email", person.getEmail());

    if (!query.list().isEmpty()) {
      resultPerson = (Person) query.list().get(0);
    }

    if (session != null) {
      session.close();
    }

    return resultPerson;
  }

  @Override
  public List<Person> getAll() {

    session = HibernateUtil.getSessionFactory().openSession();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Person> criteriaBuilderQuery = criteriaBuilder.createQuery(Person.class);
    Root<Person> rootEntry = criteriaBuilderQuery.from(Person.class);
    CriteriaQuery<Person> criteriaQuery = criteriaBuilderQuery.select(rootEntry);
    TypedQuery<Person> allQuery = session.createQuery(criteriaQuery);

    List<Person> results = allQuery.getResultList();

    if (session != null) {
      session.close();
    }

    return results;
  }

  @Override
  public void persist(Person person) {

    if (get(person) == null && person.isValid()) {
      super.persist(person);
    }
  }

  @Override
  public void persistAll(List<Person> t) {
    super.persistAll(t);
  }

  @Override
  public void update(Person person) {
      super.update(person);
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
