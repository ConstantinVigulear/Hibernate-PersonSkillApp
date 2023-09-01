package model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SkillSet implements Cost {
  private Person person;
  private Map<Skill, SkillLevel> skills;
  private double totalCost;

  public SkillSet(Person person) {
    this.person = Objects.requireNonNull(person);
    skills = new HashMap<>();
    totalCost = 0.0;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(double totalCost) {
    if (totalCost >= 0) this.totalCost = totalCost;
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
    calculateTotalCost();
  }

  public void addSkill(Skill skill, SkillLevel skillLevel) {
    if (skill != null && skillLevel != null) {
      skills.put(skill, skillLevel);
      totalCost += evaluateSkill(skill, skillLevel);
    }
  }

  @Override
  public void calculateTotalCost() {
    skills.keySet().forEach(skill -> totalCost += evaluateSkill(skill, skills.get(skill)));
  }

  public SkillLevel getAverageSkillLevel() {

    int numberOfSkills = getNumberOfSkills();
    int sumOfLevels = getSumOfSkillLevels();

    int avg = sumOfLevels / numberOfSkills;

    return findSkillLevelByValue(avg);
  }

  private double evaluateSkill(Skill skill, SkillLevel level) {
    return skill.getDomain().getPrice() * (1 + (double) level.getLevelValue() / 10);
  }

  private int getNumberOfSkills() {
    AtomicInteger numberOfSkills = new AtomicInteger();
    skills.keySet().forEach(skill -> numberOfSkills.addAndGet(1));
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
