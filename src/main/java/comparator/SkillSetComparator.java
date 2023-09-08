package comparator;

import model.SkillSet;

import java.util.Comparator;

public class SkillSetComparator implements Comparator<SkillSet> {
  @Override
  public int compare(SkillSet o1, SkillSet o2) {
    return o1.getRank() - o2.getRank();
  }
}
