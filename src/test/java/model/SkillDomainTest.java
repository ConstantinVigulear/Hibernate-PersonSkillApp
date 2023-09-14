package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillDomainTest {

  @Test
  void getSkillDomainByName() {
    assertEquals(SkillDomain.SECURITY, SkillDomain.getSkillDomainByName("Security"));
  }
}
