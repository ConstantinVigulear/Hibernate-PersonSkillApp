package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

  private Skill skill;

  @BeforeEach
  void setUp() {
    skill =
        new Skill.SkillBuilder().name("Penetration Testing").domain(SkillDomain.SECURITY).build();
  }

  @Test
  void testNameGetter() {
    assertEquals("Penetration Testing", skill.getName());
  }

  @Test
  void testDomainGetter() {
    assertEquals(SkillDomain.SECURITY, skill.getDomain());
  }

  @Test
  void whenNameAndDomainThenValid() {
    assertTrue(skill.isValid());
  }

  @Test
  void whenNameEmptyThenNotValid() {
    skill = new Skill.SkillBuilder().name("").domain(SkillDomain.SECURITY).build();

    assertFalse(skill.isValid());
  }

  @Test
  void whenSameNamesAndDomainsThenSkillEqual() {
    Skill skill1 =
        new Skill.SkillBuilder().name("Penetration Testing").domain(SkillDomain.SECURITY).build();

    assertEquals(skill1, skill);
  }

  @Test
  void whenDifferentNamesSameDomainsThenSkillNotEqual() {
    Skill skill1 =
            new Skill.SkillBuilder().name("SQL Injecting").domain(SkillDomain.SECURITY).build();

    assertNotEquals(skill1, skill);
  }

  @Test
  void whenDifferentDomainsSameNamesThenSkillNotEqual() {
    Skill skill1 =
            new Skill.SkillBuilder().name("Penetration Testing").domain(SkillDomain.PROGRAMMING).build();

    assertNotEquals(skill1, skill);
  }
}
