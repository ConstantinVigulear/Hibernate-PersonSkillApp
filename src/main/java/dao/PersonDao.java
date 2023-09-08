package dao;

import model.Person;
import utils.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao implements Dao<Person> {

  PreparedStatement preparedStatement;
  ResultSet resultSet;

  @Override
  public <L> Person get(L id) throws SQLException, IOException {
    try (Connection connection = Utils.establishDataBaseConnection()) {

      String query = "SELECT name, surname, email FROM PERSONS p WHERE ID = ?";
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setLong(1, (long) id);

      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        String name = resultSet.getString("name");
        String surName = resultSet.getString("surName");
        String email = resultSet.getString("email");

        return new Person.PersonBuilder().name(name).surname(surName).email(email).build();
      }

    }
    return null;
  }

  @Override
  public List<Person> getAll() {
    return new ArrayList<>();
  }

  @Override
  public <L> L save(Person person) throws SQLException, IOException {
    long newId = 0L;
    try (Connection connection = Utils.establishDataBaseConnection()) {

      if (getPersonId(person) == 0) {
        String idQuery = "select max(id) from persons";
        preparedStatement = connection.prepareStatement(idQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        newId = getMaxId(resultSet) + 1;

        String query = "INSERT INTO PERSONS p values(?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, newId);
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getSurname());
        preparedStatement.setString(4, person.getEmail());
        preparedStatement.executeQuery();
      } else {
        update(person);
      }

    }
    return (L) (Long) newId;
  }

  @Override
  public void update(Person person) {
    System.out.println("updating person...");
  }

  @Override
  public void delete(Person person) {
    System.out.println("deleting person...");
  }

  public long getPersonId(Person person) throws SQLException, IOException {

    try (Connection connection = Utils.establishDataBaseConnection()) {

      String getPersonQuery = "SELECT id FROM persons WHERE name = ? AND SURNAME = ?";
      preparedStatement = connection.prepareStatement(getPersonQuery);
      preparedStatement.setString(1, person.getName());
      preparedStatement.setString(2, person.getSurname());
      preparedStatement.executeQuery();

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) return resultSet.getLong("id");

    }

    return 0;
  }

  public long getMaxId(ResultSet resultSet) throws SQLException {
    resultSet.next();
    long id;
    if (resultSet.getString("max(id)") != null) id = Long.parseLong(resultSet.getString("max(id)"));
    else id = 0;
    return id;
  }
}
