package dao;

import model.Skill;
import model.SkillDomain;
import utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDao implements Dao<Skill> {

  PreparedStatement preparedStatement;
  ResultSet resultSet;

  @Override
  public <L> Skill get(L id) {
    try (Connection connection = Utils.establishDataBaseConnection()) {

      String query = "SELECT name, \"domain\" FROM SKILLS s WHERE name = ?";
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, (String) id);

      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        String name = resultSet.getString("name");
        String domain = resultSet.getString("domain");

        return new Skill.SkillBuilder()
            .name(name)
            .domain(SkillDomain.getSkillDomainByName(domain))
            .build();
      }

    } catch (SQLException | IOException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<Skill> getAll() {
    return new ArrayList<>();
  }

  @Override
  public <L> L save(Skill skill) throws SQLException, IOException {
    try (Connection connection = Utils.establishDataBaseConnection()) {

      if (getSkillPrimaryKey(skill).isEmpty()) {

        String query = "INSERT INTO SKILLS s values(?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, skill.getName());
        preparedStatement.setString(2, skill.getDomain().name());
        preparedStatement.executeQuery();
      } else {
        update(skill);
      }

    }
    return (L) skill.getName();
  }

  @Override
  public void update(Skill skill) {
    System.out.println("updating skill...");
  }

  @Override
  public void delete(Skill skill) {
    System.out.println("deleting skill...");
  }

  public String getSkillPrimaryKey(Skill skill) throws SQLException, IOException {
    try (Connection connection = Utils.establishDataBaseConnection()) {

      String getPersonQuery = "SELECT name FROM skills WHERE name = ?";
      preparedStatement = connection.prepareStatement(getPersonQuery);
      preparedStatement.setString(1, skill.getName());
      preparedStatement.executeQuery();

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) return resultSet.getString("name");

      return "";
    }
  }
}
