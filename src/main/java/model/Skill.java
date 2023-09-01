package model;

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
