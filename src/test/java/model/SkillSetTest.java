package model;

import com.google.common.collect.Collections2;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import services.Cost;
import services.CostBasedOnLevel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkillSetTest {

  SkillSet skillSet;
  Person person1;
  Skill skill1;
  Skill skill2;
  Skill skill3;

  Cost cost;

  @BeforeEach
  void setup() {
    skillSet = new SkillSet();
    person1 = new Person("Mayu", "Arita", "mayu.arite@gmail.com");
    skill1 = new Skill("Java Core", SkillDomain.PROGRAMMING);
  }

  @Test
  void testCostSetterGetter() {
    cost = new CostBasedOnLevel(123);
    skillSet.setCost(cost);

    assertEquals(123, ((CostBasedOnLevel) (skillSet.getCost())).getValue());
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
    skill2 = new Skill("MySQL", SkillDomain.PROGRAMMING);
    skillSet.addSkill(skill1, SkillLevel.LOW);
    skillSet.addSkill(skill2, SkillLevel.GODLIKE);

    assertEquals(2, skillSet.getSkills().size());
  }

  @Test
  void testGetAverageSkillLevel() {
    skill2 = new Skill("MySQL", SkillDomain.PROGRAMMING);
    skill3 = new Skill("Docker", SkillDomain.DEVOPS);
    skillSet.setPerson(person1);
    skillSet.addSkill(skill1, SkillLevel.HIGH);
    skillSet.addSkill(skill2, SkillLevel.MEDIUM);
    skillSet.addSkill(skill3, SkillLevel.LOW);

    assertEquals(SkillLevel.MEDIUM, skillSet.getAverageSkillLevel());
  }

  @Test
  void testSkillSetCostCalculation() {
    skillSet.setPerson(person1);
    skillSet.addSkill(skill1, SkillLevel.GODLIKE);

    // 200 + 1.5
    assertEquals(300, ((CostBasedOnLevel) skillSet.getCost()).getValue());


    // 300 + 200*(1+0.1) =
    skill2 = new Skill("MySQL", SkillDomain.PROGRAMMING);
    skillSet.addSkill(skill2, SkillLevel.LOW);

    assertEquals(520, ((CostBasedOnLevel) skillSet.getCost()).getValue());

    skill3 = new Skill("Docker", SkillDomain.DEVOPS);
    skillSet.addSkill(skill3, SkillLevel.HIGH);

    assertEquals(741, ((CostBasedOnLevel) skillSet.getCost()).getValue());
  }

  @TestFactory
  Stream<DynamicTest> testSkillSetCostWithPermutationOfThreeSkillsWithGodlikeLevel() {
    skill2 = new Skill("MySQL", SkillDomain.PROGRAMMING);
    skill3 = new Skill("Docker", SkillDomain.DEVOPS);

    double expected = 855;

    List<Skill> list = Arrays.asList(skill1, skill2, skill3);

    return Collections2.permutations(list).stream()
        .map(
            permutation ->
                DynamicTest.dynamicTest(
                    permutation.toString(),
                    () -> {
                      skillSet = new SkillSet();
                      for (Skill skill : permutation) {
                        skillSet.addSkill(skill, SkillLevel.GODLIKE);
                      }
                      double actual = ((CostBasedOnLevel) skillSet.getCost()).getValue();

                      assertEquals(expected, actual);
                    }));
  }
}
