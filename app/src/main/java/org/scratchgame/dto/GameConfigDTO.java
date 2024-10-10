package org.scratchgame.dto;

import java.util.Map;

public class GameConfigDTO {

    private int columns;
    private int rows;
    private Map<String, GameSymbolDTO> symbols;
    private GameProbabilityDTO probabilities;
    private Map<String, GameWinCombinationDTO> win_combinations;

    // Getters and Setters
    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<String, GameSymbolDTO> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, GameSymbolDTO> symbols) {
        this.symbols = symbols;
    }

    public GameProbabilityDTO getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(GameProbabilityDTO probabilities) {
        this.probabilities = probabilities;
    }

    public Map<String, GameWinCombinationDTO> getWinCombinations() {
        return win_combinations;
    }

    public void setWinCombinations(Map<String, GameWinCombinationDTO> winCombinations) {
        this.win_combinations = winCombinations;
    }
}
