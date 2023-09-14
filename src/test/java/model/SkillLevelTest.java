package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SkillLevelTest {

    @Test
    void getSkillLevelByValue() {
        assertEquals(SkillLevel.GODLIKE, SkillLevel.getSkillLevelByValue(5));
      }

    @Test
    void getSkillLevelByName() {
        assertEquals(SkillLevel.GODLIKE, SkillLevel.getSkillLevelByName("godlike"));
      }
}