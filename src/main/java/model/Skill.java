package model;

import validator.SkillValidator;
import validator.Validator;

import java.util.Objects;

public class Skill {

  private final String name;
  private final SkillDomain domain;

  private Skill(SkillBuilder skillBuilder) {
    name = skillBuilder.name;
    domain = skillBuilder.domain;
  }

  public String getName() {
    return name;
  }

  public SkillDomain getDomain() {
    return domain;
  }

  public boolean isValid() {
    Validator validator = new SkillValidator();
    return validator.isValid(this);
  }

  @Override
  public String toString() {
    return "Skill{" +
            "name='" + name + '\'' +
            ", domain=" + domain +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Skill skill = (Skill) o;
    return Objects.equals(name, skill.name) && domain == skill.domain;
  }

  public static class SkillBuilder {

    private String name;
    private SkillDomain domain;

    public SkillBuilder() {}

    public SkillBuilder name(String name) {
      this.name = Objects.requireNonNullElse(name, "");
      return this;
    }

    public SkillBuilder domain(SkillDomain skillDomain) {
      this.domain = Objects.requireNonNullElse(skillDomain, SkillDomain.NONE);
      return this;
    }

    public Skill build() {
      return new Skill(this);
    }
  }
}
