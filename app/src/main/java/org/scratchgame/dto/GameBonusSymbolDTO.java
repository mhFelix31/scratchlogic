package org.scratchgame.dto;

import java.util.Map;

public class GameBonusSymbolDTO {
    private Map<String, Integer> symbols;

    // Getters and Setters
    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }
}
