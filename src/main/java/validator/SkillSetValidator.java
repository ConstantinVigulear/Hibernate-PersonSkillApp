package validator;

import model.SkillSet;

public class SkillSetValidator implements Validator {
  @Override
  public <T> boolean isValid(T object) {
    SkillSet skillSet = (SkillSet) object;
    return skillSet.getPerson().isValid() && !skillSet.getSkills().isEmpty();
  }
}
