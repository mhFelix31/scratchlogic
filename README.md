# Scratch Game Project

This project implements a Scratch Game based on configurable symbols and win combinations, allowing users to simulate a scratch-off experience. The game mechanics utilize Java 21 and is set up with DevContainers for a consistent development environment.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [How to Run the Project](#how-to-run-the-project)
- [Tests](#tests)
- [Dependencies](#dependencies)

## Features

- Configurable game board dimensions (rows and columns)
- Various symbols with defined rewards
- Probability settings for symbol appearances
- Win combinations including horizontal, vertical, and diagonal checks
- Bonus symbols that multiply rewards or provide extra bonuses
- DevContainers for easy setup and development

## Getting Started

To get started with the Scratch Game project, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd scratch-game
   ```

2. **Open the project in your IDE with DevContainers:**

   This project is configured to use [DevContainers](https://code.visualstudio.com/docs/devcontainers/containers) for a consistent development environment. Open the project folder in Visual Studio Code, and the DevContainer will be set up automatically.

3. **Ensure you have Java 21 installed:**

   This project requires Java 21. You can use a Java version manager like [SDKMAN!](https://sdkman.io/) or [Homebrew](https://brew.sh/) on macOS to install it.

## How to Run the Project

1. **Build the project:**

   Make sure you have Gradle installed. Run the following command in the terminal:

   ```bash
   ./gradlew build
   ```

2. **Run the application:**

   After building, you can run the application using:

   ```bash
   java -jar build/libs/app.jar --config example/config.json --betting-amount 10
   ```

   Ensure you have the configuration JSON file properly set up.

## Tests

The project includes unit tests to ensure the game's logic is functioning correctly. You can run the tests using Gradle:

```bash
./gradlew test
```

## Dependencies

The project uses the following dependencies:

- **JUnit**: For unit testing.
- **Gson**: For JSON parsing.

These dependencies are specified in the `build.gradle.kts` file.

*This project was made for a technical interview and will be deleted soon*