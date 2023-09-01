package model;

public enum SkillLevel {
  NONE(0),
  LOW(1),
  MEDIUM(2),
  HIGH(3),
  ADVANCED(4),
  GODLIKE(5);

  private final int levelValue;

  SkillLevel(int value) {
    this.levelValue = value;
  }

  public int getLevelValue() {
    return levelValue;
  }
}
