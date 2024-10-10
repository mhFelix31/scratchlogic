package org.scratchgame.dto;

import java.util.List;

public class GameWinCombinationDTO {
    private double reward_multiplier;
    private String when;
    private Integer count;  // Optional, depends on the type of win combination
    private String group;
    private List<List<String>> covered_areas;  // Optional for linear symbol combinations

    public GameWinCombinationDTO(String group, String when, Integer count, double reward_multiplier, List<List<String>> covered_areas) {
        this.reward_multiplier = reward_multiplier;
        this.when = when;
        this.count = count;
        this.group = group;        
        this.covered_areas = covered_areas;
    }

    // Getters and Setters
    public double getRewardMultiplier() {
        return reward_multiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.reward_multiplier = rewardMultiplier;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<List<String>> getCoveredAreas() {
        return covered_areas;
    }

    public void setCoveredAreas(List<List<String>> coveredAreas) {
        this.covered_areas = coveredAreas;
    }
}
