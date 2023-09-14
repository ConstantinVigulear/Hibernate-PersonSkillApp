package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

  Skill skill1;

  @BeforeEach
  void setUp() {
    skill1 = new Skill();
  }

  @Test
  void testGetSetId() {
    skill1.setId(999L);

    assertEquals(999L, skill1.getId());
  }

  @Test
  void testGetSetName() {
    skill1.setName("Test");

    assertEquals("Test", skill1.getName());
  }

  @Test
  void testGetSetDomain() {
    skill1.setDomain(SkillDomain.PROGRAMMING);

    assertEquals(SkillDomain.PROGRAMMING, skill1.getDomain());
  }

  @Test
  void testGetSetLevel() {
    skill1.setLevel(SkillLevel.GODLIKE);

    assertEquals(SkillLevel.GODLIKE, skill1.getLevel());
  }

  @Test
  void testGetSetPersons() {

    skill1.setName("Test");

    Person person = new Person();
    person.setName("Test");
    person.setSurname("Test");
    person.setEmail("teste.test@gmail.com");

    Set<Person> people = new HashSet<>();
    people.add(person);
    skill1.setPersons(people);

    assertTrue(skill1.getPersons().contains(person));
  }

  @Test
  void whenNameDomainLevelPresentThenValid() {
    skill1.setName("Test");
    skill1.setDomain(SkillDomain.NONE);
    skill1.setLevel(SkillLevel.NONE);

    assertTrue(skill1.isValid());
  }

  @Test
  void whenNameOrDomainOrLevelAbsentThenInvalid() {
    skill1.setName("");
    assertFalse(skill1.isValid());
  }

  @Test
  void whenEqualNameDomainSkillThenEqual() {
    skill1 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);
    Skill skill2 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);

    assertEquals(skill1, skill2);
  }

  @Test
  void whenNotEqualOrNameOrDomainAndSkillThenNotEqual() {
    skill1 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);
    Skill skill2 = new Skill("Java ++", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);

    assertNotEquals(skill1, skill2);
  }

  @Test
  void getSkillCost() {
    skill1 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);

    assertEquals(300, skill1.getSkillCost());
  }

  @Test
  void removePerson() {
    skill1 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);

    Person person = new Person();
    person.setName("Test");
    person.setSurname("Test");
    person.setEmail("teste.test@gmail.com");

    skill1.addPerson(person);

    assertTrue(skill1.getPersons().contains(person));
    assertTrue(person.getSkills().contains(skill1));

    skill1.removePerson(person);

    assertFalse(skill1.getPersons().contains(person));
    assertFalse(person.getSkills().contains(skill1));

  }
}
