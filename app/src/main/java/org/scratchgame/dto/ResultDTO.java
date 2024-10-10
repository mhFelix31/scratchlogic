package org.scratchgame.dto;

import java.util.Map;
import java.util.ArrayList;

public class ResultDTO {
    private String[][] matrix;
    private double reward;
    private Map<String, String[]> appliedWinningCombinations;
    private ArrayList<String> appliedBonusSymbol;

    public ResultDTO(String[][] matrix, double reward, Map<String, String[]> appliedWinningCombinations, ArrayList<String> appliedBonusSymbol) {
        this.matrix = matrix;
        this.reward = reward;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
    }

    // Getters e Setters
    public String[][] getMatrix() {
        return matrix;
    }

    public double getReward() {
        return reward;
    }

    public Map<String, String[]> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public ArrayList<String> getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }
}
