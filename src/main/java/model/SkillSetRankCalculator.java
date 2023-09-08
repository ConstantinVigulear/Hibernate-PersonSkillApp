package model;

public class SkillSetRankCalculator implements RankCalculator{

    @Override
    public <T> int calculateRank(T skillSet) {
        return (int) ((SkillSet)skillSet).getTotalCost() / 100;
    }
}
