package services;

import model.Skill;
import model.SkillLevel;
import model.SkillSet;

public interface Cost {
    void applyCalculation(SkillSet skillSet, Skill skill, SkillLevel skillLevel);
}
