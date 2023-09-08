package comparator;

import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillSetComparatorTest {

  @Test
  void compare() {

    Person person =
        new Person.PersonBuilder()
            .name("Nana")
            .surname("Arita")
            .email("nana.arita@gmail.com")
            .build();

    SkillSet skillSet1 = new SkillSet(person);
    SkillSet skillSet2 = new SkillSet(person);

    Skill skill1 =
        new Skill.SkillBuilder().name("Java Core").domain(SkillDomain.PROGRAMMING).build();
    Skill skill2 = new Skill.SkillBuilder().name("MySQL").domain(SkillDomain.PROGRAMMING).build();

    skillSet1.addSkill(skill1, SkillLevel.GODLIKE);
    skillSet2.addSkill(skill2, SkillLevel.LOW);

    List<SkillSet> skillSets =
        new ArrayList<>() {
          {
            add(skillSet1);
            add(skillSet2);
          }
        };

    List<SkillSet> sortedSkillSets = new ArrayList<>(skillSets);
    sortedSkillSets.sort(new SkillSetComparator());

    assertTrue(skillSets.get(0).getTotalCost() != sortedSkillSets.get(0).getTotalCost());
  }
}
