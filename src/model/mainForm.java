package model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.Random;
import java.util.TimerTask;

import res.R;


/**
 * mainForm class is the core of the application.
 *
 * @author Arshdeep Saran
 * @date 3/15/2023
 * @version 1.0.0-rc.3
 *
 */
public class mainForm extends JFrame {

    /** Stores the version of the game */
    public static final String VERSION = "1.0.0-rc.3";

    /** Random object for dice rolling */
    public final Random rand = new Random();

    private JPanel mainPanel;
    private JPanel titleScreen;
    private JPanel settingsScreen;
    private JLabel titleText;
    private JLabel settingsText;
    private JButton settingbutton;
    private JButton backbutton;
    private JButton newGameButton;
    private JButton continueButton;
    private JLabel versionLabel;
    private JPanel gameScreen;
    private JPanel setBankScreen;
    private JTextField startingCashField;
    private JButton continueButtonST;
    private JLabel startingCashLabel;
    private JLabel die1;
    private JLabel die2;
    private JButton rollDiceButton;
    private JTextField currentRollField;
    private JLabel cashLabel;
    private JTextField cashField;
    private JLabel lossProfitLabel;
    private JTextField lossProfitField;
    private JTextField betAmountField;
    private JButton placeBetButton;
    private JLabel infoScreen;
    private JPanel betPanel;
    private JPanel moneyPanel;
    private JPanel dicePanel;
    private JPanel infoPanel;
    private JTextField currentBetField;
    private JLabel pointLabel;
    private JTextField pointField;
    private JLabel currentRollLabel;
    private JLabel betAmountLabel;
    private JButton restartButton;
    private JButton exitButton;
    private JPanel gameOverScreen;
    private JButton plusOneButton;
    private JButton plusFiveButton;
    private JButton plusTenButton;
    private JButton plusTwentyButton;
    private JButton plusHundredButton;
    private JButton plusFiftyButton;
    private JPanel rulesScreen;
    private JPanel howToPlayPanel;
    private JLabel rulesDisplay;
    private final Die dieA = new Die();
    private final Die dieB = new Die();
    private final Player player = new Player();
    private boolean firstStart = false;
    private boolean betPlaced = false;
    private boolean pointTurn = false;
    private boolean gameOver = false;
    private int startingCash = 0;
    private final JMenuBar jMenuBar = new JMenuBar();
    private final JMenu jMenu = new JMenu("Menu");
    private final JMenuItem rules = new JMenuItem("Rules");
    private final JMenuItem restart = new JMenuItem("Restart");
    private final JMenuItem backToGame = new JMenuItem("Back");
    private final JMenuItem exit = new JMenuItem("Exit");
    private final Timer mainTimer = new Timer();

    /**
     * Constructs action listeners.
     */
    public mainForm() {
        settingbutton.setEnabled(false);
        continueButton.setEnabled(false);
        createMenuBar();
        jMenuBar.setVisible(false);
        lossProfitField.setBackground(new Color(184,207,229));
        versionLabel.setText(VERSION);
        setTexture(infoScreen, R.infoScreenTextures.ROLL_DICE_TO_BEGIN_1);

        rollDiceButton.addActionListener(e -> {
            disableRollDiceButton();
            disableBetting();
            if (firstStart) {
                rollBothDice(3, R.time.HALF_SECOND);
            }
            else {
                jMenuBar.setVisible(true);
                pack();
                setTexture(infoScreen, R.infoScreenTextures.LOADING_GAME,
                        3, R.time.HALF_SECOND);
                runFirstStart();
            }
        });

        betAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkBetAmountField();
            }
            @Override
            public void keyTyped(KeyEvent e) {
                checkBetAmountField();
            }
        });

        placeBetButton.addActionListener(e -> {
            betPlaced = true;
            disableBetting();
            syncCashWithBet();
        });

        backbutton.addActionListener(e -> {
            backbutton.setText(backbutton.getText());
            backbutton.setRolloverEnabled(false);
            setTexture(backbutton, R.buttonTextures.CLICKED,
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY);
            animateButtonAndSwitch(backbutton, R.buttonTextures.CLICKED,
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY, titleScreen);
        });

        startingCashField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                continueButtonST.setEnabled(isAlpha(startingCashField)
                        && isBlank(startingCashField) && isPositive(startingCashField));
            }
        });

        continueButtonST.addActionListener(e -> {
            startingCashField.setEnabled(false);
            backToGame.setEnabled(false);
            disableBetting();
            player.setCurrentCash(Integer.parseInt(startingCashField.getText()));
            startingCash = Integer.parseInt(startingCashField.getText());
            cashField.setText(String.valueOf(player.getCurrentCash()));
            setTexture(continueButtonST, R.buttonTextures.CLICKED, R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY);
            mainTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    displayTutorial();
                }
            }, R.buttonData.CLICKED_ANIMATION_DELAY * R.buttonData.CLICKED_TEXTURE_STATES);
        });

        settingbutton.addActionListener(e -> {
            settingbutton.setRolloverEnabled(false);
            animateButtonAndSwitch(settingbutton, R.buttonTextures.CLICKED,
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY, settingsScreen);
        });

        newGameButton.addActionListener(e -> {
            continueButtonST.setEnabled(false);
            animateButtonAndSwitch(newGameButton, R.buttonTextures.CLICKED,
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY, setBankScreen);
        });

        restartButton.addActionListener(e -> {
            resetGame();
            continueButtonST.setEnabled(false);
            switchPanel(setBankScreen);
        });

        backToGame.addActionListener(e -> {
            backToGame.setEnabled(false);
            rules.setEnabled(true);
            switchPanel(gameScreen);
        });

        rules.addActionListener(e -> {
            rulesDisplay.setIcon(new ImageIcon("src/ui_resources/labels/rules/rules.png"));
            switchPanel(rulesScreen);
            rules.setEnabled(false);
            backToGame.setEnabled(true);
        });

        restartButton.addActionListener(e -> {
            resetGame();
            continueButtonST.setEnabled(false);
            switchPanel(setBankScreen);
        });

        restart.addActionListener(e -> {
            resetGame();
            switchPanel(setBankScreen);
        });

        plusOneButton.addActionListener(e -> {
            plusBetAmount(1);
            checkBetAmountField();
        });

        plusFiveButton.addActionListener(e -> {
            plusBetAmount(5);
            checkBetAmountField();
        });

        plusTenButton.addActionListener(e -> {
            plusBetAmount(10);
            checkBetAmountField();
        });

        plusTwentyButton.addActionListener(e -> {
            plusBetAmount(20);
            checkBetAmountField();
        });

        plusFiftyButton.addActionListener(e -> {
            plusBetAmount(50);
            checkBetAmountField();
        });

        plusHundredButton.addActionListener(e -> {
            plusBetAmount(100);
            checkBetAmountField();
        });


        exitButton.addActionListener(e -> dispose());
        exit.addActionListener(e -> dispose());
    }


    /**
     * Increases the bet amount in betAmountField.
     * @param theAmountToIncrease int
     */
    private void plusBetAmount(int theAmountToIncrease) {
        if (isBlank(betAmountField)) {
            betAmountField.setText(String.valueOf(Integer.parseInt(betAmountField.getText()) + theAmountToIncrease));
        } else {
            betAmountField.setText("0");
            betAmountField.setText(String.valueOf(Integer.parseInt(betAmountField.getText()) + theAmountToIncrease));
        }
    }

    /**
     * Switches to the rules panel and changes the rules display
     * with different textures using a delay.
     */
    private void displayTutorial() {
        switchPanel(rulesScreen);
        setTexture(rulesDisplay, "", 4, 2000);
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                switchPanel(gameScreen);
                pack();
            }
        },8000);
    }

    /**
     * Checks if the betAmountField is blank or empty,
     * and if the bet amount is less than available cash.
     */
    private void checkBetAmountField() {
        if (isBlank(betAmountField) && isAlpha(betAmountField)) {
            placeBetButton.setEnabled(Integer.parseInt(betAmountField.getText()) <= player.getCurrentCash());
        }
        else {
            placeBetButton.setEnabled(false);
        }
    }

    /**
     * Creates the menu bar and adds items to it.
     */
    private void createMenuBar() {
        setJMenuBar(jMenuBar);
        jMenuBar.add(jMenu);
        jMenu.add(rules);
        jMenu.add(restart);
        jMenu.add(backToGame);
        jMenu.add(exit);
        backToGame.setEnabled(false);
        rules.setEnabled(true);
    }

    /**
     * Resets the game.
     */
    private void resetGame() {
        player.reset();
        gameOver = false;
        firstStart = false;
        setTexture(infoScreen, R.infoScreenTextures.ROLL_DICE_TO_BEGIN_1);
        resetDice();
        continueButtonST.setIcon(new ImageIcon("src/ui_resources/buttons/continueButton/continueButton.png"));
        continueButtonST.setRolloverEnabled(true);
        startingCashField.setEnabled(true);
        startingCashField.setEditable(true);
        rollDiceButton.setEnabled(true);
        clearAllFields();
    }

    /**
     * Resets the dice to default side and texture.
     */
    private void resetDice() {
        dieA.setSide(1);
        dieB.setSide(1);
        syncDiceIconsWithRoll();
    }

    /**
     * Sets all fields to blank.
     */
    private void clearAllFields() {
        startingCashField.setText(R.messages.BLANK);
        currentBetField.setText(R.messages.BLANK);
        currentRollField.setText(R.messages.BLANK);
        cashField.setText(R.messages.BLANK);
        lossProfitField.setText(R.messages.BLANK);
        lossProfitField.setBackground(new Color(184,207,229));
    }

    /**
     * Animates the button and switches to the desired panel.
     * @param theButton JButton
     * @param theButtonName Name of the button.
     * @param theTextureStates Amount of states of the button's texture.
     * @param theDelay
     * @param thePanel
     */
    private void animateButtonAndSwitch(final JButton theButton, final String theButtonName,
                                        final int theTextureStates, final int theDelay, JPanel thePanel) {
        theButton.setRolloverEnabled(false);
        setTexture(theButton, theButtonName, theTextureStates, theDelay);
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                theButton.setRolloverEnabled(true);
                setTexture(theButton, R.buttonTextures.NONE);
                switchPanel(thePanel);
            }
        }, (long) theDelay * theTextureStates);
    }

    /**
     * Prepare for the next non-point turn.
     */
    private void readyNormalTurn() {
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                rollDiceButton.setEnabled(true);
                betAmountField.setText(R.messages.BLANK);
                setTexture(infoScreen,
                        R.infoScreenTextures.WAITING_FOR_ROLL);
            }
        }, R.time.TWO_SECONDS);
    }

    /**
     * Sets the game for play.
     */
    private void runFirstStart() {
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setTexture(infoScreen, R.infoScreenTextures.PLACE_YOUR_BET);
                firstStart = true;
                enableBettingButtons();
                betAmountField.setEditable(true);
                rollDiceButton.setRolloverEnabled(true);
            }
        }, R.time.THREE_SECONDS);
    }

    /**
     * Syncs the player's cash with fields.
     */
    private void syncCashWithBet() {
        if (Integer.parseInt(betAmountField.getText()) <= player.getCurrentCash()) {
            player.setCurrentBet(Integer.parseInt(betAmountField.getText()));
            player.setCurrentCash(player.getCurrentCash() - player.getCurrentBet());
            cashField.setText(String.valueOf(player.getCurrentCash()));
            currentBetField.setText(String.valueOf(player.getCurrentBet()));
            betAmountField.setText(R.messages.GOOD_LUCK);
            setTexture(infoScreen,
                    R.infoScreenTextures.BET_PLACED_1);
            readyNormalTurn();
        }
    }

    /**
     * Sets the icon of a component using multiple texture types and a delay in between.
     * @param theComponent JComponent.
     * @param theComponentTexture The texture type.
     * @param theTextureStates The amount of states of the texture.
     * @param theDelay int
     */
    private void setTexture(final JComponent theComponent,  final String theComponentTexture,
                            final int theTextureStates, final int theDelay) {
        int appliedStates = 0;
        while (!(appliedStates > theTextureStates) && appliedStates != theTextureStates) {
            int finalAppliedStates = appliedStates + 1;
            if (theTextureStates == 1) {
                appliedStates++;
            }
            mainTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (theTextureStates == 1) {
                        setTexture(theComponent,
                                theComponentTexture);
                    } else {
                        setTexture(theComponent,
                                theComponentTexture + finalAppliedStates);
                    }
                }
            },theDelay * appliedStates);
            appliedStates++;
        }
    }

    /**
     * Changes icon of a component.
     * @param theComponent JComponent
     * @param theComponentTexture The texture to change to.
     */
    private void setTexture(JComponent theComponent, String theComponentTexture) {
        if (theComponent instanceof JButton jButton) {
            jButton.setIcon(new ImageIcon(R.locations.SOURCE_BUTTONS + theComponent.getName()
                    + R.locations.FORWARD_SLASH + theComponent.getName() + theComponentTexture + R.extensions.PNG));
        }
        else if (theComponent instanceof JLabel jLabel) {
            jLabel.setIcon(new ImageIcon(R.locations.SOURCE_LABELS + theComponent.getName()
                    + R.locations.FORWARD_SLASH + theComponent.getName() + theComponentTexture + R.extensions.PNG));
        }
    }

    /**
     * Disables roll dice button.
     */
    private void disableRollDiceButton() {
        rollDiceButton.setEnabled(false);
        rollDiceButton.setRolloverEnabled(false);
    }

    /**
     * Checks if field contains alphabetical characters.
     * @param theTextField JTextField
     * @return boolean
     */
    private boolean isAlpha(JTextField theTextField) {
        String input;
        input = theTextField.getText();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isAlphabetic(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if field contains values greater than zero.
     * @param theTextField JTextField
     * @return boolean.
     */
    private boolean isPositive(JTextField theTextField) {
        int input;
        input = Integer.parseInt(theTextField.getText());
        return (input > 0);
    }

    /**
     * Checks if field is empty or blank.
     * @param theTextField JTextField
     * @return boolean
     */
    private boolean isBlank(JTextField theTextField) {
        return !theTextField.getText().isBlank()
                && !theTextField.getText().isEmpty();
    }

    /**
     * Checks if a point turn needs to be run.
     */
    private void checkForPointRoll() {
        if (!pointTurn) {
            if (player.getCurrentRoll() == 4
                    || player.getCurrentRoll() == 5
                    || player.getCurrentRoll() == 6
                    || player.getCurrentRoll() == 8
                    || player.getCurrentRoll() == 9
                    || player.getCurrentRoll() == 10) {
                processPointRoll();
                syncPlayerWithPoint();
            }
            else {
                processNormalRoll();
            }
        }
        else {
            processPointRoll();
        }
    }

    /**
     * Checks the game to win or lose if current turn is a point turn.
     */
    private void processPointRoll() {
        if (player.getCurrentRoll() == 7) {
            processRoundLost();
            pointTurn = false;
        }
        else if (player.getCurrentRoll() == player.getMyPoint()) {
            processRoundWIN();
            pointTurn = false;
        }
        else {
            pointTurn = true;
            disableBetting();
        }
    }

    /**
     * Checks the game to win or lose if current turn is a normal turn.
     */
    private void processNormalRoll() {
        if (player.getCurrentRoll() == 7
                || player.getCurrentRoll() == 11) {
            processRoundWIN();
        }
        else {
            processRoundLost();
        }
    }

    /**
     * Wins the current round.
     */
    private void processRoundWIN() {
        setTexture(infoScreen, R.infoScreenTextures.WON_ROUND);
        syncCash();
        syncLossProfitField();
        resetBetAndPointFields();
    }

    /**
     * Loses the current round.
     */
    private void processRoundLost() {
        setTexture(infoScreen, R.infoScreenTextures.LOST_ROUND);
        syncLossProfitField();
        resetBetAndPointFields();
    }

    /**
     * Syncs the profit field with player's profit and controls if
     * game has been won or lost according to read data.
     */
    private void syncLossProfitField() {
        lossProfitField.setText(String.valueOf((player.getCurrentCash()) - startingCash));
        if (Integer.parseInt(lossProfitField.getText()) == (startingCash * -1)) {
            gameOver = true;
        }
        if ((player.getCurrentCash() - startingCash) < 0) {
            lossProfitField.setBackground(new Color(255,102,102));
        }
        else {
            lossProfitField.setBackground(new Color(178,255,102));
        }
    }

    /**
     * Advances the game to either a point or normal turn state.
     * Changes components depending on which one it is.
     */
    private void processCurrentState() {
        syncPlayerWithCurrentRoll();
        syncDiceIconsWithRoll();
        resetRollDiceButton();
        checkForPointRoll();
        if (!gameOver) {
            if (!pointTurn) {
                mainTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        betPlaced = false;
                        enableBettingButtons();
                        betAmountField.setEditable(true);
                        currentBetField.setText(R.messages.BLANK);
                        currentRollField.setText(R.messages.BLANK);
                        setTexture(infoScreen, R.infoScreenTextures.PLACE_YOUR_BET);
                    }
                }, R.time.THREE_SECONDS);
            } else {
                rollDiceButton.setEnabled(true);
                setTexture(infoScreen, R.infoScreenTextures.WAITING_FOR_ROLL_POINT);
            }
        }
        else {
            mainTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    switchPanel(gameOverScreen);
                }
            }, R.time.THREE_SECONDS);
        }
    }

    /**
     * Resets the rollDiceButton to default.
     */
    private void resetRollDiceButton() {
        rollDiceButton.setRolloverEnabled(true);
        rollDiceButton.setIcon(new ImageIcon("src/ui_resources/buttons/rollDiceButton/rollDiceButton.png"));
    }

    /**
     * Sets the player's roll to the total of both dice.
     */
    private void syncPlayerWithCurrentRoll() {
        player.setCurrentRoll(dieA.getMySide() + dieB.getMySide());
        currentRollField.setText(String.valueOf(player.getCurrentRoll()));
    }

    /**
     * Syncs the player's point with fields.
     */
    private void syncPlayerWithPoint() {
        player.setMyPoint(player.getCurrentRoll());
        pointField.setText(String.valueOf(player.getMyPoint()));
    }

    /**
     * Randomly changes the texture of dice displays.
     * @param theTimesToRoll int
     * @param theDelay int
     */
    private void randomizeDiceIcons(final int theTimesToRoll, final int theDelay) {
        for (int i = 0; i != theTimesToRoll; i++) {
            int randomA = rand.nextInt(6 - 1 + 1) + 1;
            int randomB = rand.nextInt(6 - 1 + 1) + 1;
            setTexture(die1, "Side" + randomA,  1, theDelay * i);
            setTexture(die2, "Side" + randomB, 1, theDelay * i);
        }
    }

    /**
     * Resets bet and point fields to default.
     */
    private void resetBetAndPointFields() {
        player.setMyPoint(0);
        player.setCurrentBet(0);
        currentBetField.setText(R.messages.BLANK);
        pointField.setText(R.messages.BLANK);
    }

    /**
     * Syncs player's cash with fields/
     */
    private void syncCash() {
        player.setCurrentCash(player.getCurrentCash() + (player.getCurrentBet() * 2));
        cashField.setText(String.valueOf(player.getCurrentCash()));
    }

    /**
     * Disables the betting entirely.
     */
    private void disableBetting() {
        disableBettingButtons();
        placeBetButton.setEnabled(false);
        betAmountField.setEditable(false);
    }

    /**
     * Disables all plus betting buttons.
     */
    private void disableBettingButtons() {
        plusOneButton.setEnabled(false);
        plusFiveButton.setEnabled(false);
        plusTenButton.setEnabled(false);
        plusTwentyButton.setEnabled(false);
        plusFiftyButton.setEnabled(false);
        plusHundredButton.setEnabled(false);
    }

    /**
     * Enables all plus betting buttons.
     */
    private void enableBettingButtons() {
        plusOneButton.setEnabled(true);
        plusFiveButton.setEnabled(true);
        plusTenButton.setEnabled(true);
        plusTwentyButton.setEnabled(true);
        plusFiftyButton.setEnabled(true);
        plusHundredButton.setEnabled(true);
    }

    /**
     * Syncs the texture of dice to their respected roll value.
     */
    private void syncDiceIconsWithRoll() {
        setTexture(die1, "Side" + dieA.getMySide());
        setTexture(die2, "Side" + dieB.getMySide());
    }

    /**
     * Rolls both dice and advances the game's turn.
     * @param theTimesToRoll int
     * @param theDelay int
     */
    private void rollBothDice(final int theTimesToRoll, final int theDelay) {
        int delay = theTimesToRoll * theDelay;
        randomizeDiceIcons(theTimesToRoll, theDelay);
        setTexture(infoScreen, R.infoScreenTextures.ROLL_DICE, theTimesToRoll, theDelay);
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dieA.rollDice();
                dieB.rollDice();
                processCurrentState();
            }
        }, delay);
    }

    /**
     * Switches the panel to desired panel.
     * @param thePanel JPanel
     */
    private void switchPanel(JPanel thePanel) {
        mainPanel.removeAll();
        mainPanel.add(thePanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sHeight = screenSize.height/2;
        int sWidth = screenSize.width/2;
        Dimension screenDimension = new Dimension(sWidth,sHeight);
        mainForm mf = new mainForm();
        mf.setTitle(mainForm.VERSION);
        mf.setContentPane(mf.mainPanel);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);
        mf.pack();
    }
}
