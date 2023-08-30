package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

  private Skill skill;

  @BeforeEach
  void setUp() {
    skill = new Skill();
  }

  @Test
  void testNameGetterSetter() {
    skill.setName("Penetration Testing");
    assertEquals("Penetration Testing", skill.getName());
  }

  @Test
  void testDomainGetterSetter() {
    skill.setDomain(SkillDomain.SECURITY);
    assertEquals(SkillDomain.SECURITY, skill.getDomain());
  }
}
