package dao;

import model.*;
import utils.Utils;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class SkillSetDao implements Dao<SkillSet> {

  public SkillSetDao() {}

  @Override
  public <L> SkillSet get(L id) throws SQLException, IOException {
    Map<Person, Map<Skill, SkillLevel>> result = new HashMap<>();

    try (Connection connection = Utils.establishDataBaseConnection()) {

      String query =
          "SELECT p.name, p.surname, p.email, skill, (SELECT \"domain\"  FROM SKILLS s WHERE s.NAME = skill) AS \"domain\", \"level\" FROM PERSONS_SKILLS ps JOIN persons p ON ps.PERSON = p.id WHERE p.ID = ?";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, (Integer) id);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        parseSkillSet(resultSet, result);
      }
    }

    if (!composeFinalResult(result).isEmpty()) {
      return composeFinalResult(result).get(0);
    }

    return null;
  }

  @Override
  public List<SkillSet> getAll() {

    Map<Person, Map<Skill, SkillLevel>> result = new HashMap<>();

    try (Connection connection = Utils.establishDataBaseConnection();
        Statement statement = connection.createStatement()) {

      String query =
          """
                  SELECT\s
                  \tp.name, p.surname, p.email,\s
                  \tSKILL, (SELECT "domain"  FROM SKILLS s WHERE s.NAME = skill) AS "domain", "level"
                  FROM PERSONS_SKILLS ps JOIN persons p ON ps.PERSON = p.id
                  """;

      ResultSet resultSet = statement.executeQuery(query);

      while (resultSet.next()) {
        parseSkillSet(resultSet, result);
      }

      composeFinalResult(result);

    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }

    return composeFinalResult(result);
  }

  @Override
  public <L> L save(SkillSet skillSet) throws SQLException, IOException {
    PersonDao personDao = new PersonDao();
    SkillDao skillDao = new SkillDao();
    Person person = skillSet.getPerson();

    long id = personDao.getPersonId(person);

    // save person if NOT in db
    if (id == 0) {
      id = personDao.save(person);
    }

    // check if each skill is already in DB else insert it
    for (Skill skill : skillSet.getSkills().keySet()) {
      String skillPrimaryKey = skillDao.getSkillPrimaryKey(skill);

      // save skill if NOT in db
      if (skillPrimaryKey.isEmpty()) {
        skillDao.save(skill);
      }

      // check if recording is unique before inserting
      if (isRecordUnique(id, skill)) {
        // insert id(person) and skillName and levelName
        try (Connection connection = Utils.establishDataBaseConnection()) {

          PreparedStatement preparedStatement;
          String query = "INSERT INTO PERSONS_SKILLS ps VALUES (?, ?, ?)";

          preparedStatement = connection.prepareStatement(query);
          preparedStatement.setLong(1, id);
          preparedStatement.setString(2, skill.getName());
          preparedStatement.setString(3, skillSet.getSkills().get(skill).name());
          preparedStatement.executeQuery();

        } catch (SQLException | IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return null;
  }

  @Override
  public void update(SkillSet skillSet) {
    System.out.println("updating skillSet...");
  }

  @Override
  public void delete(SkillSet skillSet) {}

  private boolean isPersonNew(Map<Person, Map<Skill, SkillLevel>> result, Person person) {
    return result.keySet().stream().noneMatch(e -> e.getName().equals(person.getName()));
  }

  private void parseSkillSet(ResultSet resultSet, Map<Person, Map<Skill, SkillLevel>> result)
      throws SQLException {
    String nameToken = resultSet.getString("name");
    String surnameToken = resultSet.getString("surname");
    String emailToken = resultSet.getString("email");
    String skillNameToken = resultSet.getString("skill");
    String domainNameToken = resultSet.getString("domain");
    String levelNameToken = resultSet.getString("level");

    Person person =
        new Person.PersonBuilder().name(nameToken).surname(surnameToken).email(emailToken).build();
    SkillDomain domain = SkillDomain.getSkillDomainByName(domainNameToken);
    SkillLevel level = SkillLevel.getSkillLevelByName(levelNameToken);
    Skill skill = new Skill.SkillBuilder().name(skillNameToken).domain(domain).build();

    if (isPersonNew(result, person)) {
      result.put(
          person,
          new HashMap<>() {
            {
              put(skill, level);
            }
          });
    } else {

      // add skill to existing person
      result
          .get(
              result.keySet().stream()
                  .filter(e -> e.getName().equals(person.getName()))
                  .findFirst()
                  .orElse(null))
          .put(skill, level);
    }
  }

  private List<SkillSet> composeFinalResult(Map<Person, Map<Skill, SkillLevel>> result) {
    List<SkillSet> skillSets = new ArrayList<>();
    result
        .keySet()
        .forEach(
            key -> {
              SkillSet skillSet = new SkillSet(key, result.get(key));
              skillSets.add(skillSet);
            });

    return skillSets;
  }

  private boolean isRecordUnique(long id, Skill skill) {
    try (Connection connection = Utils.establishDataBaseConnection()) {

      PreparedStatement preparedStatement;
      String query = "SELECT person, skill FROM PERSONS_SKILLS ps WHERE PERSON = ? AND skill = ?";

      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setLong(1, id);
      preparedStatement.setString(2, skill.getName());
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) return false;

    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }

    return true;
  }
}
