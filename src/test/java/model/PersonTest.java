package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

  private Person person1;
  private Skill skill1;

  @BeforeEach
  void setUp() {

    person1 = new Person();
    person1.setName("George");
    person1.setSurname("Orwell");
    person1.setEmail("george.orwell@gmail.com");

    skill1 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);
    skill1.getPersons().add(person1);
    Skill skill2 = new Skill("SQL", SkillDomain.PROGRAMMING, SkillLevel.GODLIKE);

    Set<Skill> skills = new HashSet<>();
    skills.add(skill1);
    skills.add(skill2);

    person1.setSkills(skills);
  }

  @Test
  void whenPersonNotEqualThenFalse() {
    Person person2 = new Person();
    person2.setName("Test");
    person2.setSurname("Test");
    person2.setEmail("teste.test@gmail.com");

    assertNotEquals(person1, person2);
  }

  @Test
  void whenPersonAreEqualThenTrue() {
    Person person2 = new Person();
    person2.setName("George");
    person2.setSurname("Orwell");
    person2.setEmail("george.orwell@gmail.com");

    assertEquals(person1, person2);
  }

  @Test
  void testGetSetId() {
    person1.setId(999L);

    assertEquals(999L, person1.getId());
  }

  @Test
  void testGetSetName() {
    person1.setName("Test");

    assertEquals("Test", person1.getName());
  }

  @Test
  void testGetSetSurname() {
    person1.setSurname("Test");

    assertEquals("Test", person1.getSurname());
  }

  @Test
  void testGetSetEmail() {
    person1.setEmail("test@test.com");

    assertEquals("test@test.com", person1.getEmail());
  }

  @Test
  void testGetSetSkills() {
    assertNotNull(person1.getSkills());
    assertFalse(person1.getSkills().isEmpty());
  }

  @Test
  void whenSkillsEmptyThenTrue() {
    person1.setSkills(new HashSet<>());
    assertTrue(person1.getSkills().isEmpty());
  }

  @Test
  void getTotalCost() {
    assertEquals(600, person1.getTotalCost());
  }

  @Test
  void whenNameSurnameEmailPresentThenValid() {
    assertTrue(person1.isValid());
  }

  @Test
  void whenNameOrSurnameOrEmailAbsentThenInvalid() {
    person1.setName("");
    assertFalse(person1.isValid());
  }

  @Test
  void addSkill() {
    Skill skill3 = new Skill("Java Core", SkillDomain.PROGRAMMING, SkillLevel.ADVANCED);

    assertFalse(person1.getSkills().contains(skill3));
    assertFalse(skill3.getPersons().contains(person1));

    person1.addSkill(skill3);

    assertTrue(person1.getSkills().contains(skill3));
    assertTrue(skill3.getPersons().contains(person1));
  }

  @Test
  void removeSkill() {
    assertTrue(person1.getSkills().contains(skill1));
    assertTrue(skill1.getPersons().contains(person1));

    person1.removeSkill(skill1);

    assertFalse(person1.getSkills().contains(skill1));
    assertFalse(skill1.getPersons().contains(person1));
  }

  @Test
  void testCalculateTotalCost() {

    int totalCostBeforeCalculation = person1.getTotalCost();

    person1.calculateTotalCost();

    int totalCostAfterCalculation = person1.getTotalCost();
    assertEquals(totalCostBeforeCalculation, totalCostAfterCalculation);
  }
}
