package org.scratchgame.dto;

import java.util.List;

public class GameProbabilityDTO {
    private List<GameStandardSymbolProbabilityDTO> standard_symbols;
    private GameBonusSymbolDTO bonus_symbols;

    // Getters e Setters
    public List<GameStandardSymbolProbabilityDTO> getStandardSymbols() {
        return standard_symbols;
    }

    public void setStandardSymbols(List<GameStandardSymbolProbabilityDTO> standard_symbols) {
        this.standard_symbols = standard_symbols;
    }

    public GameBonusSymbolDTO getBonusSymbols() {
        return bonus_symbols;
    }

    public void setBonusSymbols(GameBonusSymbolDTO bonus_symbols) {
        this.bonus_symbols = bonus_symbols;
    }
}
