package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Person;
import model.Skill;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl extends AbstractDao<Skill> implements SkillDao {
  private EntityManager entityManager;

  @Override
  public Skill get(Long id) {

    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    Skill skillToFind = entityManager.find(Skill.class, id);
    entityManager.close();

    return skillToFind;
  }

  @Override
  public Skill get(Skill skill) {

    Skill resultSkill = null;

    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();

    String hql = "FROM Skill S WHERE S.name = :name and S.domain = :domain and S.level = :level";
    Query query = entityManager.createQuery(hql);
    query.setParameter("name", skill.getName());
    query.setParameter("domain", skill.getDomain());
    query.setParameter("level", skill.getLevel());

    if (!query.getResultList().isEmpty()) {
      resultSkill = (Skill) query.getResultList().get(0);
    }

    entityManager.close();

    return resultSkill;
  }

  @Override
  public List<Skill> getAll() {

    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Skill> criteriaBuilderQuery = criteriaBuilder.createQuery(Skill.class);
    Root<Skill> rootEntry = criteriaBuilderQuery.from(Skill.class);
    CriteriaQuery<Skill> criteriaQuery = criteriaBuilderQuery.select(rootEntry);
    TypedQuery<Skill> allQuery = entityManager.createQuery(criteriaQuery);

    entityManager.close();

    return allQuery.getResultList();
  }

  @Override
  public void persist(Skill skill) {

    if (get(skill) == null && skill.isValid()) {
      super.persist(skill);
    } else {
      super.update(skill);
    }
  }

  @Override
  public Skill update(Skill skill) {
    Skill updatedSkill = super.update(skill);

    List<Person> people = new ArrayList<>(skill.getPersons());

    updatePeople(people);

    return updatedSkill;
  }

  @Override
  public void delete(Skill skill) {

    removeSkillFromLocalPeople(skill);

    Skill skillToDelete = get(skill);
    entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();

    List<Person> people = new ArrayList<>(skillToDelete.getPersons());

    removeSkillFromRemotePeople(skill, people);

    if (entityManager != null) {
      entityManager.getTransaction().commit();
      entityManager.close();
    }

    super.delete(skill);
  }

  private void updatePeople(List<Person> people) {
    people.forEach(
            person -> {
              int oldTotalCost = person.getTotalCost();
              person.calculateTotalCost();
              int newTotalCost = person.getTotalCost();

              if (oldTotalCost != newTotalCost) {
                entityManager.merge(person);
              }
            }
    );
  }

  private void removeSkillFromRemotePeople(Skill skill, List<Person> people) {
    people.forEach(
        person -> {
          TypedQuery<Person> queryPersonFromDataBase = getPersonFromDataBase(person);

          if (!queryPersonFromDataBase.getResultList().isEmpty()) {

            Person foundPerson = queryPersonFromDataBase.getResultList().get(0);
            foundPerson.removeSkill(skill);
            entityManager.merge(foundPerson);
          }
        });
  }

  private static void removeSkillFromLocalPeople(Skill skill) {
    (new ArrayList<>(skill.getPersons())).forEach(person -> person.removeSkill(skill));
  }

  private TypedQuery<Person> getPersonFromDataBase(Person person) {
    TypedQuery<Person> query =
        entityManager.createQuery("select p from Person p " + "where p.id = :id", Person.class);
    query.setParameter("id", person.getId());
    return query;
  }
}
