package org.scratchgame;

import org.scratchgame.controller.GameCLIController;

public class App {

    public static void main(String[] args) {
        try {
            GameCLIController gameManager = new GameCLIController(args);
            gameManager.loadConfig();
            gameManager.startGame();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Expected usage: --config <configFilePath> --betting-amount <amount>");
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
        }
    }
}
