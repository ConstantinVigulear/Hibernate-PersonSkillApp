package dao;

import model.Skill;
import model.SkillDomain;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SkillDaoTest {

  SkillDao skillDao = new SkillDao();
  Skill skill;

  @Test
  void get() throws SQLException, IOException {

    String expected = "Java Core";
    skill = new Skill.SkillBuilder().name(expected).domain(SkillDomain.NONE).build();
    skillDao.save(skill);
    skill = skillDao.get(expected);
    String actual = skill.getName();

    assertEquals(expected, actual);
  }

  @Test
  void getAll() {
    assertNotNull(skillDao.getAll());
  }

  @Test
  void save() throws SQLException, IOException {
    String expected = "1";
    skill = new Skill.SkillBuilder().name(expected).domain(SkillDomain.NONE).build();
    String res = skillDao.save(skill);
    skill = skillDao.get(expected);
    String actual = skill.getName();

    assertEquals(expected, actual);
  }

  @Test
  void update() {
    skillDao.update(skill);
  }

  @Test
  void delete() {
    skillDao.delete(skill);
  }

}
