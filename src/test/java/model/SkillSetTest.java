package model;

import com.google.common.collect.Collections2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SkillSetTest {

  SkillSet skillSet;
  Person person1;
  Skill skill1;

  @BeforeEach
  void setup() {
    person1 =
        new Person.PersonBuilder().name("test").surname("test").email("test").build();

    skillSet = new SkillSet(person1);

    skill1 = new Skill.SkillBuilder().name("Java Core").domain(SkillDomain.PROGRAMMING).build();
  }

  @Test
  void testCostSetterGetter() {
    skillSet.setTotalCost(123);

    assertEquals(123, skillSet.getTotalCost());
  }

  @Test
  void testPersonSetterGetter() {
    skillSet.setPerson(person1);

    assertEquals(person1, skillSet.getPerson());
  }

  @Test
  void testSkillsSetterGetter() {
    Map<Skill, SkillLevel> skills = new HashMap<>();
    skills.put(skill1, SkillLevel.LOW);
    skillSet.setSkills(skills);

    assertEquals(skills, skillSet.getSkills());
  }

  @Test
  void testCreateEmptySkillSet() {
    assertEquals(0, skillSet.getSkills().size());
  }

  @Test
  void testAddSkillToPerson() {
    skillSet.setPerson(person1);
    skillSet.addSkill(skill1, SkillLevel.HIGH);

    assertEquals(1, skillSet.getSkills().size());
  }

  @Test
  void testAddAnotherSkillToPerson() {
    skillSet.setPerson(person1);
    Skill skill2 = new Skill.SkillBuilder().name("MySQL").domain(SkillDomain.PROGRAMMING).build();
    skillSet.addSkill(skill1, SkillLevel.LOW);
    skillSet.addSkill(skill2, SkillLevel.GODLIKE);

    assertEquals(2, skillSet.getSkills().size());
  }

  @Test
  void testGetAverageSkillLevel() {
    Skill skill2 = new Skill.SkillBuilder().name("MySQL").domain(SkillDomain.PROGRAMMING).build();
    Skill skill3 = new Skill.SkillBuilder().name("Docker").domain(SkillDomain.DEVOPS).build();
    skillSet.setPerson(person1);
    skillSet.addSkill(skill1, SkillLevel.HIGH);
    skillSet.addSkill(skill2, SkillLevel.MEDIUM);
    skillSet.addSkill(skill3, SkillLevel.LOW);

    assertEquals(SkillLevel.MEDIUM, skillSet.getAverageSkillLevel());
  }

  @Test
  void testSkillSetTotalCostCalculationWhenAddingSkill() {
    skillSet.setPerson(person1);
    skillSet.addSkill(skill1, SkillLevel.GODLIKE);

    assertEquals(300, skillSet.getTotalCost());

    Skill skill2 = new Skill.SkillBuilder().name("MySQL").domain(SkillDomain.PROGRAMMING).build();
    skillSet.addSkill(skill2, SkillLevel.LOW);

    assertEquals(520, skillSet.getTotalCost());

    Skill skill3 = new Skill.SkillBuilder().name("Docker").domain(SkillDomain.DEVOPS).build();
    skillSet.addSkill(skill3, SkillLevel.HIGH);

    System.out.println(skillSet.toString());

    assertEquals(741, skillSet.getTotalCost());
  }

  @Test
  void testSkillSetTotalCostCalculationWhenSetSkills() {
    skillSet.setPerson(person1);

    Skill skill2 = new Skill.SkillBuilder().name("MySQL").domain(SkillDomain.PROGRAMMING).build();
    Skill skill3 = new Skill.SkillBuilder().name("Docker").domain(SkillDomain.DEVOPS).build();
    Map<Skill, SkillLevel> skills =
        new HashMap<>() {
          {
            put(skill1, SkillLevel.GODLIKE);
            put(skill2, SkillLevel.LOW);
            put(skill3, SkillLevel.HIGH);
          }
        };

    skillSet.setSkills(skills);

    assertEquals(741, skillSet.getTotalCost());
  }

  @TestFactory
  Stream<DynamicTest> testSkillSetCostWithPermutationOfThreeSkillsWithGodlikeLevel() {

    Skill skill2 = new Skill.SkillBuilder().name("MySQL").domain(SkillDomain.PROGRAMMING).build();
    Skill skill3 = new Skill.SkillBuilder().name("Docker").domain(SkillDomain.DEVOPS).build();

    List<Skill> skills = Arrays.asList(skill1, skill2, skill3);

    return testAllPermutations(skills);
  }

  @Test
  public void whenSamePersonAndSkillsThenSkillSetsAreEqual() {
    skillSet.setPerson(person1);

    Skill skill2 = new Skill.SkillBuilder().name("MySQL").domain(SkillDomain.PROGRAMMING).build();
    Skill skill3 = new Skill.SkillBuilder().name("Docker").domain(SkillDomain.DEVOPS).build();
    Map<Skill, SkillLevel> skills =
        new HashMap<>() {
          {
            put(skill1, SkillLevel.GODLIKE);
            put(skill2, SkillLevel.LOW);
            put(skill3, SkillLevel.HIGH);
          }
        };

    skillSet.setSkills(skills);

    SkillSet skillSet1 = new SkillSet(person1, skills);
    boolean actual = skillSet.equals(skillSet1);

    assertTrue(actual);
  }

  @Test
  void whenPersonSkillsValidThenSkillSetValid() {
    skillSet.setPerson(person1);
    skillSet.addSkill(skill1, SkillLevel.LOW);
    assertTrue(skillSet.isValid());
  }

  @Test
  void whenPersonNotValidThenSkillSetNotValid() {
    skillSet = new SkillSet(null);

    Map<Skill, SkillLevel> skills =
        new HashMap<>() {
          {
            put(skill1, SkillLevel.GODLIKE);
          }
        };

    skillSet.setSkills(skills);

    assertFalse(skillSet.isValid());
  }

  @Test
  void whenSkillsEmptyThenSkillSetNotValid() {
    skillSet = new SkillSet(null);

    assertFalse(skillSet.isValid());
  }

  @Test
  void whenGetRankThenReturnRank() {
    skillSet.addSkill(skill1, SkillLevel.GODLIKE);

    assertEquals(3, skillSet.getRank());
  }

  private String nameSkillSetPermutationTest(List<Skill> skills) {
    StringBuilder name = new StringBuilder();

    skills.forEach(
        skill -> {
          if (name.isEmpty()) {
            name.append(skill.getName());
          } else name.append("->").append(skill.getName());
        });

    return name.toString();
  }

  private void testPermutation(List<Skill> permutation) {

    skillSet = new SkillSet(person1);
    for (Skill skill : permutation) {
      skillSet.addSkill(skill, SkillLevel.GODLIKE);
    }

    double expected = 855;
    double actual = skillSet.getTotalCost();

    assertEquals(expected, actual);
  }

  private Stream<DynamicTest> testAllPermutations(List<Skill> list) {
    return Collections2.permutations(list).stream()
        .map(
            permutation ->
                DynamicTest.dynamicTest(
                    nameSkillSetPermutationTest(permutation), () -> testPermutation(permutation)));
  }
}
