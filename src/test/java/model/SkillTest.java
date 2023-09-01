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
}
