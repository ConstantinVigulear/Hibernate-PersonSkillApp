package model;

import validator.SkillSetValidator;
import validator.Validator;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SkillSet implements Cost {
  private Person person;
  private Map<Skill, SkillLevel> skills;
  private int totalCost;

  public SkillSet() {
    this.person = new Person.PersonBuilder().name("").surname("").email("").build();
    this.skills = new HashMap<>();
    calculateTotalCost();
  }

  public SkillSet(Person person) {
    this.person =
        Objects.requireNonNullElse(
            person, new Person.PersonBuilder().name("").surname("").email("").build());
    this.skills = new HashMap<>();
    calculateTotalCost();
  }

  public SkillSet(Person person, Map<Skill, SkillLevel> skills) {
    this.person =
        Objects.requireNonNullElse(
            person, new Person.PersonBuilder().name("").surname("").email("").build());
    this.skills = Objects.requireNonNullElse(skills, new HashMap<>());
    calculateTotalCost();
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(int totalCost) {
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
    if (isSkillValidBeforeAdding(skill, skillLevel)) {
      skills.put(skill, skillLevel);
      totalCost += (int) getSkillCost(skill, skillLevel);
    }
  }

  public SkillLevel getAverageSkillLevel() {

    int numberOfSkills = getNumberOfSkills();
    int sumOfLevels = getSumOfSkillLevels();

    int avg = sumOfLevels / numberOfSkills;

    return SkillLevel.getSkillLevelByValue(avg);
  }

  public boolean isValid() {
    Validator validator = new SkillSetValidator();
    return validator.isValid(this);
  }

  public int getRank() {
    RankCalculator rankCalculator = new SkillSetRankCalculator();
    return rankCalculator.calculateRank(this);
  }

  @Override
  public String toString() {
    return "SkillSet{"
        + "person="
        + person
        + ", skills="
        + skills
        + ", totalCost="
        + totalCost
        + '}';
  }

  @Override
  public void calculateTotalCost() {
    skills.keySet().forEach(skill -> totalCost += (int) getSkillCost(skill, skills.get(skill)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SkillSet skillSet = (SkillSet) o;
    return Objects.equals(person, skillSet.person);
  }

  private boolean isSkillValidBeforeAdding(Skill skill, SkillLevel skillLevel) {
    return !skills.containsKey(skill) && skill != null && skillLevel != null;
  }

  private double getSkillCost(Skill skill, SkillLevel level) {
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
}
