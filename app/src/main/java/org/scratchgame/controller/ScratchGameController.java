package org.scratchgame.controller;

import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import org.scratchgame.dto.GameBonusSymbolDTO;
import org.scratchgame.dto.GameConfigDTO;
import org.scratchgame.dto.GameProbabilityDTO;
import org.scratchgame.dto.GameStandardSymbolProbabilityDTO;
import org.scratchgame.dto.GameSymbolDTO;
import org.scratchgame.dto.GameWinCombinationDTO;
import org.scratchgame.dto.ResultDTO;

public class ScratchGameController {
    public String[][] generateMatrix(GameConfigDTO config) {
        Random random = new Random();
        String[][] matrix = new String[config.getRows()][config.getColumns()];
        GameProbabilityDTO probabilities = config.getProbabilities();
    
        for (GameStandardSymbolProbabilityDTO standardSymbol : probabilities.getStandardSymbols()) {
            int column = standardSymbol.getColumn();
            int row = standardSymbol.getRow();
            Map<String, Integer> symbolsProbabilityMap = standardSymbol.getSymbols();
    
            List<String> symbolsList = new ArrayList<>();
            symbolsProbabilityMap.forEach((symbol, probability) -> {
                for (int i = 0; i < probability; i++) {
                    symbolsList.add(symbol);
                }
            });
    
            if (!symbolsList.isEmpty()) {
                int randomIndex = random.nextInt(symbolsList.size());
                matrix[row][column] = symbolsList.get(randomIndex);
            } else {
                throw new IllegalStateException("No symbols found for cell at row " + row + " and column " + column);
            }
        }
    
        return matrix;
    }

    public ResultDTO playGame(GameConfigDTO config, int bettingAmount) {
        String[][] matrix = generateMatrix(config);

        Map<String, String[]> appliedWinningCombinations = new HashMap<>();
        ArrayList<String> appliedBonusSymbol = new ArrayList<String>();

        double reward = checkWinningCombinationsAndCaptureResults(matrix, config, appliedWinningCombinations, appliedBonusSymbol);

        return new ResultDTO(matrix, reward * bettingAmount, appliedWinningCombinations, appliedBonusSymbol);
    }

    public double checkWinningCombinationsAndCaptureResults(String[][] matrix, GameConfigDTO config,
            Map<String, String[]> appliedWinningCombinations, List<String> appliedBonusSymbol) {
        double totalReward = 0;
        double totalBonus = 1.0;
        double totalAddition = 0;

        Map<String, GameWinCombinationDTO> winCombinations = config.getWinCombinations();

        for (String symbol : config.getSymbols().keySet()) {

            GameSymbolDTO currentSymbol = config.getSymbols().get(symbol);

            if (currentSymbol.getType().equals("standard")) {
                double sameSymbolMaxReward = 0;
                String sameSymbolWinningCombination = "";

                for (GameWinCombinationDTO combination : winCombinations.values()) {

                    if (combination.getWhen().equals("same_symbols")) {
                        Integer count = countSymbolOccurrences(matrix, symbol);

                        if (count != null && count >= combination.getCount()) {
                            double rewardForSymbol = combination.getRewardMultiplier()
                                    * currentSymbol.getRewardMultiplier();
                            if (rewardForSymbol >= sameSymbolMaxReward) {
                            
                            sameSymbolMaxReward = rewardForSymbol;
                            sameSymbolWinningCombination = combination.getGroup();
                            }
                        }
                    } else if (combination.getWhen().equals("linear_symbols")) {

                        boolean isWinningCombination = checkCoveredAreas(matrix, symbol, combination.getCoveredAreas());

                        if (isWinningCombination) {
                            double rewardForSymbol = combination.getRewardMultiplier()
                                    * currentSymbol.getRewardMultiplier();
                            totalReward += rewardForSymbol;
                            appliedWinningCombinations.put(symbol, new String[] { combination.getGroup() });
                        }
                    }
                }

                totalReward += sameSymbolMaxReward;
                if (sameSymbolWinningCombination != "") appliedWinningCombinations.put(symbol, new String[] { sameSymbolWinningCombination });

            } else if (currentSymbol.getType().equals("bonus")) {
                String impact = currentSymbol.getImpact();
                Integer count = countSymbolOccurrences(matrix, symbol);

                if (count != null && count > 0) {
                    if (impact.equals("multiply_reward")) {
                        totalBonus *= currentSymbol.getRewardMultiplier() * count;
                        appliedBonusSymbol.add(symbol);
                        break;
                    } else if (impact.equals("extra_bonus")) {
                        totalAddition += currentSymbol.getExtra() * count;
                        appliedBonusSymbol.add(symbol);
                        break;
                    }
                }
            }
        }
        return totalReward * totalBonus + totalAddition;
    }

    public int countSymbolOccurrences(String[][] matrix, String symbol) {
        int count = 0;
        for (String[] row : matrix) {
            for (String cell : row) {
                if (cell.equals(symbol)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean checkCoveredAreas(String[][] matrix, String symbol, List<List<String>> coveredAreas) {
        for (List<String> positions : coveredAreas) {
            boolean isWinningGroup = true;

            for (String position : positions) {
                String[] indices = position.split(":");
                int row = Integer.parseInt(indices[0]);
                int col = Integer.parseInt(indices[1]);

                if (!matrix[row][col].equals(symbol)) {
                    isWinningGroup = false;
                    break;
                }
            }

            if (isWinningGroup) {
                return true;
            }
        }
        return false;
    }
}
