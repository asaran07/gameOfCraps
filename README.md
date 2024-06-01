# Game of Craps

https://github.com/asaran07/gameOfCraps/assets/123274028/21e3e3be-31ec-4a9d-8089-4b5b082482f8

## Overview

Game of Craps is a Java-based dice game with a graphical user interface (GUI). The game simulates the classic casino game of Craps, where you can place bets, roll dice, and view the game status.

<img width="400" alt="gameOfCrapsSnapshot" src="https://github.com/asaran07/gameOfCraps/assets/123274028/a35463dd-d2ea-4470-8213-d31f12298275">

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Interactive GUI**: Intuitive and visually appealing user interface, focusing on minimal bugs.
- **Game Mechanics**: Implements the core rules of Craps.
- **Betting System**: Allows players to place and manage bets.
- **Multiple Screens**: Includes title screen, settings, game screen etc.
- **Responsive UI Elements**: Buttons and fields respond to user actions.
- **In-game Menu**: A small menu bar with options to view rules, restart, or exit.
- **Game Over State**: Handle the end-of-game scenario.

## Installation

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/your-username/game-of-craps.git
    ```

2. **Navigate to the Project Directory**:
    ```bash
    cd game-of-craps
    ```

3. **Compile the Code**:
    Use your preferred Java IDE (e.g., IntelliJ IDEA) to compile the project.

## Usage

1. **Run the Application**:
    Launch the main class `mainForm` to start the game.
    
    ```java
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sHeight = screenSize.height / 2;
        int sWidth = screenSize.width / 2;
        Dimension screenDimension = new Dimension(sWidth, sHeight);
        mainForm mf = new mainForm();
        mf.setTitle(mainForm.VERSION);
        mf.setContentPane(mf.mainPanel);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);
        mf.pack();
    }
    ```

2. **Gameplay**:
    - **Start a New Game**: Click on the 'New Game' button.
    - **Enter starting cash**: Click on the field and enter the amount of cash to start with.
    - **Place Bets**: Enter your bet amount and place the bet. Both the use of buttons, and typing the value manually works.
    - **Roll Dice**: Click on the 'Roll Dice' button to roll the dice.
    - **Follow Instructions**: The game will guide you through the rules and game states.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch.
3. Make your changes.
4. Submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
