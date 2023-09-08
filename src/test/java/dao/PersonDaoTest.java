package dao;

import model.Person;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonDaoTest {

  Person person = new Person.PersonBuilder().name("test").surname("test").email("test").build();
  PersonDao personDao = new PersonDao();

  @Test
  void get() throws SQLException, IOException {
    personDao.save(person);
    long id = personDao.getPersonId(person);

    assertEquals(person, personDao.get(id));
  }

  @Test
  void whenPersonNotInDBWhenReturnNull() throws SQLException, IOException {
    assertNull(personDao.get(111L));
  }

  @Test
  void getAll() {
    assertNotNull(personDao.getAll());
  }

  @Test
  void save() throws SQLException, IOException {
    personDao.save(person);
    long id = personDao.getPersonId(person);

    assertEquals(person, personDao.get(id));
  }

  @Test
  void update() {}

  @Test
  void delete() {
    personDao.delete(person);
  }

  @Test
  void whenPersonNotInDBThenReturnZero() throws SQLException, IOException {
    long expected = 0;
    long actual = personDao.getPersonId(new Person.PersonBuilder().build());

    assertEquals(expected, actual);
  }

  @Test
  void whenPersonsTableIsNotEmptyThenReturnMaxPersonId() throws SQLException {
    try (ResultSet resultSet = mock(ResultSet.class)) {

      when(resultSet.getString("max(id)")).thenReturn("1");

      assertEquals(1, personDao.getMaxId(resultSet));
    }
  }

  @Test
  void whenPersonsTableIsEmptyThenReturnZero() throws SQLException {
    try (ResultSet resultSet = mock(ResultSet.class)) {

      when(resultSet.getString("max(id)")).thenReturn(null);

      assertEquals(0, personDao.getMaxId(resultSet));
    }
  }
}
