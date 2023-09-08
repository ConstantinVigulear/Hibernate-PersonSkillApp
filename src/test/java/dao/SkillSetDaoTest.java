package dao;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillSetDaoTest {
  SkillSetDao skillSetDao;
  SkillSet skillSet;

  @BeforeEach
  void setup() {
    skillSetDao = new SkillSetDao();
  }

  @Test
  void get() throws SQLException, IOException {
    assertNull(skillSetDao.get(-1));
  }

  @Test
  void getAll() {
    List<SkillSet> skillSets = skillSetDao.getAll();
    assertNotNull(skillSets);
  }

  @Test
  void save() throws SQLException, IOException {

    Person person;
    Skill skill1;

    person =
        new Person.PersonBuilder().name("test").surname("test").email("test").build();

    skillSet = new SkillSet(person);

    skill1 = new Skill.SkillBuilder().name("test").domain(SkillDomain.PROGRAMMING).build();
    skillSet.addSkill(skill1, SkillLevel.GODLIKE);

    skillSetDao.save(skillSet);
  }

  @Test
  void update() {
    skillSetDao.update(skillSet);
  }

  @Test
  void delete() {
    skillSetDao.delete(skillSet);
  }
}
