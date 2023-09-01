package model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SkillSet {
  private Person person;
  private Map<Skill, SkillLevel> skills;
  private double cost;

  public SkillSet(Person person) {
    this.person = Objects.requireNonNull(person);
    skills = new HashMap<>();
    cost = 0.0;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    if (cost >= 0) this.cost = cost;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = Objects.requireNonNull(person);
  }

  public Map<Skill, SkillLevel> getSkills() {
    return skills;
  }

  public void setSkills(Map<Skill, SkillLevel> skills) {
    this.skills = Objects.requireNonNull(skills);
  }

  public void addSkill(Skill skill, SkillLevel skillLevel) {
    if (skill != null && skillLevel != null) {
      evaluateCost(skill, skillLevel);
      skills.put(skill, skillLevel);
    }
  }

  private void evaluateCost(Skill skill, SkillLevel level) {

    double skillSetCost =
        cost + skill.getDomain().getPrice() * (1 + (double) level.getLevelValue() / 10);
    setCost(skillSetCost);
  }

  public SkillLevel getAverageSkillLevel() {

    int numberOfSkills = getNumberOfSkills();
    int sumOfLevels = getSumOfSkillLevels();

    int avg = sumOfLevels / numberOfSkills;

    return findSkillLevelByValue(avg);
  }

  private int getNumberOfSkills() {
    AtomicInteger numberOfSkills = new AtomicInteger();
    skills
        .keySet()
        .forEach(
            skill -> {
              numberOfSkills.addAndGet(1);
            });
    return numberOfSkills.intValue();
  }

  private int getSumOfSkillLevels() {
    AtomicInteger sumOfLevels = new AtomicInteger();
    skills
        .keySet()
        .forEach(
            skill -> {
              SkillLevel level = skills.get(skill);
              sumOfLevels.addAndGet(level.getLevelValue());
            });
    return sumOfLevels.intValue();
  }

  private SkillLevel findSkillLevelByValue(int levelValue) {
    return Arrays.stream(SkillLevel.values())
        .filter(level -> level.getLevelValue() == levelValue)
        .findAny()
        .orElse(SkillLevel.NONE);
  }
}
