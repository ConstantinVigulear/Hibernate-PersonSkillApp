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

public class SkillDaoImpl extends AbstractDao<Skill> implements SkillDao {
  private Session session;

  @Override
  public Skill get(Long id) {

    Skill resultSkill;

    session = HibernateUtil.getSessionFactory().openSession();
    resultSkill = session.get(Skill.class, id);

    if (session != null) {
      session.close();
    }

    return resultSkill;
  }

  @Override
  public Skill get(Skill skill) {

    Skill resultSkill = null;

    session = HibernateUtil.getSessionFactory().openSession();

    String hql = "FROM Skill S WHERE S.name = :name and S.domain = :domain and S.level = :level";
    SelectionQuery<?> query = session.createSelectionQuery(hql);
    query.setParameter("name", skill.getName());
    query.setParameter("domain", skill.getDomain());
    query.setParameter("level", skill.getLevel());

    if (!query.list().isEmpty()) {
      resultSkill = (Skill) query.list().get(0);
    }

    if (session != null) {
      session.close();
    }

    return resultSkill;
  }

  @Override
  public List<Skill> getAll() {

    session = HibernateUtil.getSessionFactory().openSession();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Skill> criteriaBuilderQuery = criteriaBuilder.createQuery(Skill.class);
    Root<Skill> rootEntry = criteriaBuilderQuery.from(Skill.class);
    CriteriaQuery<Skill> criteriaQuery = criteriaBuilderQuery.select(rootEntry);
    TypedQuery<Skill> allQuery = session.createQuery(criteriaQuery);

    List<Skill> results = allQuery.getResultList();

    if (session != null) {
      session.close();
    }

    return results;
  }

  @Override
  public void persist(Skill skill) {

    if (get(skill) == null && skill.isValid()) {
      super.persist(skill);
    }
  }

  @Override
  public void update(Skill skill) {
    super.update(skill);

    List<Person> people = new ArrayList<>(skill.getPersons());

    people.forEach(Person::calculateTotalCost);

  }

  @Override
  public void delete(Skill skill) {

    removeSkillFromPeople(skill);

    Skill skillToDelete = get(skill);
    session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();

    List<Person> people = new ArrayList<>(skillToDelete.getPersons());

    people.forEach(
        person -> {
          TypedQuery<Person> query = getPersonTypedQuery(person);

          if (!query.getResultList().isEmpty()) {

            Person foundPerson = query.getResultList().get(0);
            foundPerson.removeSkill(skill);
            session.merge(foundPerson);
          }
        });

    if (session != null) {
      session.getTransaction().commit();
      session.close();
    }

    super.delete(skill);
  }

  private static void removeSkillFromPeople(Skill skill) {
    (new ArrayList<>(skill.getPersons())).forEach(
            person -> person.removeSkill(skill));
  }

  private TypedQuery<Person> getPersonTypedQuery(Person person) {
    TypedQuery<Person> query =
        session.createQuery("select p from Person p " + "where p.id = :id", Person.class);
    query.setParameter("id", person.getId());
    return query;
  }
}
