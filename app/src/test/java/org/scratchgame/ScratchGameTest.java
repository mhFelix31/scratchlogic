package org.scratchgame;

import org.junit.Test;
import org.scratchgame.controller.ScratchGameController;
import org.scratchgame.dto.GameBonusSymbolDTO;
import org.scratchgame.dto.GameConfigDTO;
import org.scratchgame.dto.GameProbabilityDTO;
import org.scratchgame.dto.GameStandardSymbolProbabilityDTO;
import org.scratchgame.dto.GameSymbolDTO;
import org.scratchgame.dto.GameWinCombinationDTO;

import static org.junit.Assert.*;

import org.junit.Before;

import java.util.*;

public class ScratchGameTest {

    private ScratchGameController scratchGame;
    private GameConfigDTO config;

    @Before
    public void setUp() {
        scratchGame = new ScratchGameController();

        config = new GameConfigDTO();
        config.setRows(3);
        config.setColumns(3);
        config.setSymbols(new HashMap<String, GameSymbolDTO>() {{
            put("A", new GameSymbolDTO(5, "standard", null, null));
            put("B", new GameSymbolDTO(3, "standard", null, null));
            put("C", new GameSymbolDTO(2.5, "standard", null, null));
            put("D", new GameSymbolDTO(2, "standard", null, null));
            put("10x", new GameSymbolDTO(10, "bonus", null, "multiply_reward"));
            put("5x", new GameSymbolDTO(5, "bonus", null, "multiply_reward"));
        }});

        GameStandardSymbolProbabilityDTO standard1 = new GameStandardSymbolProbabilityDTO();
        standard1.setColumn(0);
        standard1.setRow(0);
        standard1.setSymbols(new HashMap<String, Integer>() {{
            put("A", 1);
            put("B", 2);
        }});

        GameStandardSymbolProbabilityDTO standard2 = new GameStandardSymbolProbabilityDTO();
        standard2.setColumn(0);
        standard2.setRow(1);
        standard2.setSymbols(new HashMap<String, Integer>() {{
            put("C", 3);
            put("D", 1);
        }});

        GameBonusSymbolDTO bonusSymbols = new GameBonusSymbolDTO();
        bonusSymbols.setSymbols(new HashMap<String, Integer>() {{
            put("10x", 1);
            put("5x", 2);
        }});

        GameProbabilityDTO probabilityDTO = new GameProbabilityDTO();
        probabilityDTO.setStandardSymbols(Arrays.asList(standard1, standard2));
        probabilityDTO.setBonusSymbols(bonusSymbols);

        config.setProbabilities(probabilityDTO);
    }

    @Test
    public void testGenerateMatrix() {
        String[][] matrix = scratchGame.generateMatrix(config);
        assertNotNull(matrix);
        assertEquals(3, matrix.length);
        assertEquals(3, matrix[0].length);

        boolean containsSymbol = false;
        for (String[] row : matrix) {
            for (String cell : row) {
                if (cell != null) {
                    containsSymbol = true;
                    break;
                }
            }
        }

        assertTrue("The matrix should contain generated symbols", containsSymbol);
    }

    @Test
    public void testCountSymbolOccurrences() {
        String[][] matrix = {
            {"A", "A", "B"},
            {"A", "A", "B"},
            {"C", "C", "B"}
        };

        int countA = scratchGame.countSymbolOccurrences(matrix, "A");
        int countB = scratchGame.countSymbolOccurrences(matrix, "B");
        int countC = scratchGame.countSymbolOccurrences(matrix, "C");

        assertEquals(4, countA);
        assertEquals(3, countB);
        assertEquals(2, countC);
    }

    @Test
    public void testWinningCombinations() {
        GameWinCombinationDTO combo3x = new GameWinCombinationDTO("same_symbol_3_times", "same_symbols", 3, 1, null);
        GameWinCombinationDTO combo6x = new GameWinCombinationDTO("same_symbol_6_times", "same_symbols", 6, 2, null);
        Map<String, GameWinCombinationDTO> winCombinations = new HashMap<>();
        winCombinations.put("same_symbol_3_times", combo3x);
        winCombinations.put("same_symbol_6_times", combo6x);

        config.setWinCombinations(winCombinations);

        String[][] matrix = {
            {"A", "A", "A"},
            {"B", "B", "A"},
            {"A", "A", "C"}
        };

        Map<String, String[]> appliedWinningCombinations = new HashMap<>();
        ArrayList<String> appliedBonusSymbol = new ArrayList<String>();
        double totalReward = scratchGame.checkWinningCombinationsAndCaptureResults(matrix, config, appliedWinningCombinations, appliedBonusSymbol);
        
        assertEquals(10, totalReward, 0.0);
        assertEquals(0, appliedBonusSymbol.size());
    }

    @Test
    public void testWinningCombinationsWithMultiplier() {
        GameWinCombinationDTO combo3x = new GameWinCombinationDTO("same_symbol_3_times", "same_symbols", 3, 1, null);
        GameWinCombinationDTO combo6x = new GameWinCombinationDTO("same_symbol_6_times", "same_symbols", 6, 2, null);
        GameWinCombinationDTO horizontalLine = new GameWinCombinationDTO("horizontally_linear_symbols", "linear_symbols", null, 2, Arrays.asList(Arrays.asList("0:0","0:1","0:2"),Arrays.asList("1:0","1:1","1:2"),Arrays.asList("2:0","2:1","2:2")));
        Map<String, GameWinCombinationDTO> winCombinations = new HashMap<>();
        winCombinations.put("same_symbol_3_times", combo3x);
        winCombinations.put("same_symbol_6_times", combo6x);
        winCombinations.put("horizontally_linear_symbols", horizontalLine);

        config.setWinCombinations(winCombinations);

        String[][] matrix = {
            {"A", "A", "A"},
            {"B", "B", "10x"},
            {"A", "A", "A"}
        };

        Map<String, String[]> appliedWinningCombinations = new HashMap<>();
        ArrayList<String> appliedBonusSymbol = new ArrayList<String>();
        double totalReward = scratchGame.checkWinningCombinationsAndCaptureResults(matrix, config, appliedWinningCombinations, appliedBonusSymbol);
        
        assertEquals(200, totalReward, 0.0);
        assertTrue("Combinations for symbol 'A' should be applied", appliedWinningCombinations.containsKey("A"));
        assertArrayEquals(new String[]{"same_symbol_6_times"}, appliedWinningCombinations.get("A"));

        assertEquals("10x", appliedBonusSymbol.getFirst());
    }

    @Test
    public void testCheckNotWinningCombinations() {
        GameWinCombinationDTO combo3x = new GameWinCombinationDTO("same_symbol_3_times", "same_symbols", 3, 1, null);
        GameWinCombinationDTO combo6x = new GameWinCombinationDTO("same_symbol_6_times", "same_symbols", 6, 2, null);
        Map<String, GameWinCombinationDTO> winCombinations = new HashMap<>();
        winCombinations.put("same_symbol_3_times", combo3x);
        winCombinations.put("same_symbol_6_times", combo6x);

        config.setWinCombinations(winCombinations);

        String[][] matrix = {
            {"A", "A", "F"},
            {"B", "B", "10x"},
            {"C", "D", "C"}
        };

        Map<String, String[]> appliedWinningCombinations = new HashMap<>();
        ArrayList<String> appliedBonusSymbol = new ArrayList<String>();
        double totalReward = scratchGame.checkWinningCombinationsAndCaptureResults(matrix, config, appliedWinningCombinations, appliedBonusSymbol);
        
        assertEquals(0, totalReward, 0.0);
    }
}
