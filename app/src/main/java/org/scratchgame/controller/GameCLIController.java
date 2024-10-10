package org.scratchgame.controller;

import org.scratchgame.dto.GameConfigDTO;
import org.scratchgame.dto.ResultDTO;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GameCLIController {
    private String configFilePath;
    private int bettingAmount;
    private GameConfigDTO gameConfig;

    public GameCLIController(String[] args) throws IllegalArgumentException {
        parseArguments(args);
    }

    private void parseArguments(String[] args) throws IllegalArgumentException {
        if (args.length < 4) {
            throw new IllegalArgumentException("Insufficient parameters. Use: --config <configFilePath> --betting-amount <amount>");
        }

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config":
                    if (i + 1 < args.length) {
                        this.configFilePath = args[++i];
                        if (!isValidFilePath(this.configFilePath)) {
                            throw new IllegalArgumentException("The configuration file path is invalid or the file does not exist.");
                        }
                    } else {
                        throw new IllegalArgumentException("The configuration file path was not provided.");
                    }
                    break;
                case "--betting-amount":
                    if (i + 1 < args.length) {
                        try {
                            this.bettingAmount = Integer.parseInt(args[++i]);
                            if (this.bettingAmount <= 0) {
                                throw new IllegalArgumentException("The betting amount must be greater than 0.");
                            }
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("The betting amount must be a valid integer.");
                        }
                    } else {
                        throw new IllegalArgumentException("The betting amount was not provided.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown argument: " + args[i]);
            }
        }
    }

    private boolean isValidFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public void loadConfig() throws Exception {
        try {
            String configContent = new String(Files.readAllBytes(Paths.get(configFilePath)));
            Gson gson = new Gson();
            this.gameConfig = gson.fromJson(configContent, GameConfigDTO.class);
        } catch (JsonSyntaxException e) {
            throw new Exception("Error parsing the configuration JSON file." + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error loading the configuration file: " + e.getMessage());
        }
    }

    public void startGame() {
        try {
            ScratchGameController controller = new ScratchGameController();

            ResultDTO resultDTO = controller.playGame(this.gameConfig, bettingAmount);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(resultDTO);

            System.out.println(jsonOutput);

        } catch (Exception e) {
            System.err.println("Error starting the game: " + e.getMessage());
        }
    }
}