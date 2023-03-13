package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.Random;
import java.util.TimerTask;
import res.R;


public class mainForm extends JFrame {

    private static final String VERSION = "0.12.2";
    private final Random rand = new Random();
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
    private JLabel plus1Button;
    private JLabel plus5Button;
    private JLabel plus10Button;
    private JLabel plus20Button;
    private JLabel plus50Button;
    private JPanel betPanel;
    private JPanel moneyPanel;
    private JPanel dicePanel;
    private JPanel infoPanel;
    private JTextField currentBetField;
    private JLabel plus100Button;
    private JLabel pointLabel;
    private JTextField pointField;
    private JLabel currentRollLabel;
    private JLabel betAmountLabel;
    private JButton restartButton;
    private JButton exitButton;
    private JPanel gameOverScreen;
    private final Die dieA = new Die();
    private final Die dieB = new Die();
    private final Player player = new Player();
    private final boolean firstDiceRolled = false;
    private final boolean infoScreenBusy = false;
    private boolean firstStart = false;
    private boolean betPlaced = false;
    private boolean pointTurn = false;
    private boolean gameOver = false;
    private int startingCash = 0;
    Timer mainTimer = new Timer();

    public mainForm() {
        System.out.println(infoScreen.getName());
        versionLabel.setText(VERSION);
        setTexture(infoScreen, R.infoScreenTextures.ROLL_DICE_TO_BEGIN_1);
        rollDiceButton.addActionListener(e -> {
            disableRollDiceButton();
            disableBetting();
            if (firstStart) {
                rollBothDice(3, R.time.HALF_SECOND);
            }
            else {
                setTexture(infoScreen, R.infoScreenTextures.LOADING_GAME,
                        3, R.time.HALF_SECOND);
                runFirstStart();
            }
        });

        betAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (isBlank(betAmountField) && isAlpha(betAmountField)) {
                    placeBetButton.setEnabled(Integer.parseInt(betAmountField.getText()) <= player.getCurrentCash());
                }
                else {
                    placeBetButton.setEnabled(false);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if (isBlank(betAmountField) && isAlpha(betAmountField)) {
                    placeBetButton.setEnabled(Integer.parseInt(betAmountField.getText()) <= player.getCurrentCash());
                }
                else {
                    placeBetButton.setEnabled(false);
                }
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
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY, gameScreen);
        });

        startingCashField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                continueButtonST.setEnabled(isAlpha(startingCashField)
                        && isBlank(startingCashField));
            }
        });

        continueButtonST.addActionListener(e -> {
            disableBetting();
            player.setCurrentCash(Integer.parseInt(startingCashField.getText()));
            startingCash = Integer.parseInt(startingCashField.getText());
            cashField.setText(String.valueOf(player.getCurrentCash()));
            animateButtonAndSwitch(continueButtonST, R.buttonTextures.CLICKED,
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY, gameScreen);
        });

        settingbutton.addActionListener(e -> {
            settingbutton.setRolloverEnabled(false);

            animateButtonAndSwitch(settingbutton, R.buttonTextures.CLICKED,
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY, settingsScreen);
        });

        newGameButton.addActionListener(e -> {
            animateButtonAndSwitch(newGameButton, R.buttonTextures.CLICKED,
                    R.buttonData.CLICKED_TEXTURE_STATES, R.buttonData.CLICKED_ANIMATION_DELAY, setBankScreen);
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                switchPanel(setBankScreen);
            }
        });
    }

    private void resetGame() {
        player.reset();
        gameOver = false;
        firstStart = false;
        setTexture(infoScreen, R.infoScreenTextures.ROLL_DICE_TO_BEGIN_1);
        resetDice();
        clearAllFields();
    }

    private void resetDice() {
        dieA.setSide(0);
        dieB.setSide(0);
        syncDiceIconsWithRoll();
    }

    private void clearAllFields() {
        startingCashField.setText(R.messages.BLANK);
        currentBetField.setText(R.messages.BLANK);
        currentRollField.setText(R.messages.BLANK);
        cashField.setText(R.messages.BLANK);
        lossProfitField.setText(R.messages.BLANK);
    }

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

    private void runFirstStart() {
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setTexture(infoScreen, R.infoScreenTextures.PLACE_YOUR_BET);
                firstStart = true;
                betAmountField.setEditable(true);
                rollDiceButton.setRolloverEnabled(true);
            }
        }, R.time.THREE_SECONDS);
    }

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

    private void disableRollDiceButton() {
        rollDiceButton.setEnabled(false);
        rollDiceButton.setRolloverEnabled(false);
    }

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
    
    private boolean isBlank(JTextField theTextField) {
        return !theTextField.getText().isBlank()
                && !theTextField.getText().isEmpty();
    }

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

    private void processNormalRoll() {
        if (player.getCurrentRoll() == 7
                || player.getCurrentRoll() == 11) {
            processRoundWIN();
        }
        else {
            processRoundLost();
        }
    }

    private void processRoundWIN() {
        setTexture(infoScreen, R.infoScreenTextures.WON_ROUND);
        syncCash();
        syncLossProfitField();
        resetBetAndPointFields();
    }

    private void processRoundLost() {
        setTexture(infoScreen, R.infoScreenTextures.LOST_ROUND);
        syncLossProfitField();
        resetBetAndPointFields();
    }

    private void syncLossProfitField() {
        if (player.getCurrentCash() >= startingCash) {
            lossProfitField.setText(String.valueOf((player.getCurrentCash()) - startingCash));
        }
        else {
            gameOver = true;
        }
        if ((player.getCurrentCash() - startingCash) < 0) {
            lossProfitField.setBackground(new Color(255,102,102));
        }
        else {
            lossProfitField.setBackground(new Color(178,255,102));
        }
    }

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

    private void resetRollDiceButton() {
        rollDiceButton.setRolloverEnabled(true);
        rollDiceButton.setIcon(new ImageIcon("src/ui_resources/buttons/rollDiceButton/rollDiceButton.png"));
    }

    private void syncPlayerWithCurrentRoll() {
        player.setCurrentRoll(dieA.diceTotal(dieB));
        currentRollField.setText(String.valueOf(player.getCurrentRoll()));
    }

    private void syncPlayerWithPoint() {
        player.setMyPoint(player.getCurrentRoll());
        pointField.setText(String.valueOf(player.getMyPoint()));
    }

    private void randomizeDiceIcons(final int theTimesToRoll, final int theDelay) {
        for (int i = 0; i != theTimesToRoll; i++) {
            int randomA = rand.nextInt(6 - 1 + 1) + 1;
            int randomB = rand.nextInt(6 - 1 + 1) + 1;
            setTexture(die1, "Side" + randomA,  1, theDelay * i);
            setTexture(die2, "Side" + randomB, 1, theDelay * i);
        }
    }

    private void resetBetAndPointFields() {
        currentBetField.setText(R.messages.BLANK);
        pointField.setText(R.messages.BLANK);
    }

    private void syncCash() {
        player.setCurrentCash(player.getCurrentCash() + (player.getCurrentBet() * 2));
        cashField.setText(String.valueOf(player.getCurrentCash()));
    }

    private void disableBetting() {
        placeBetButton.setEnabled(false);
        betAmountField.setEditable(false);
    }

    private void syncDiceIconsWithRoll() {
        setTexture(die1, "Side" + dieA.getMySide());
        setTexture(die2, "Side" + dieB.getMySide());
    }

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
        mf.setTitle(VERSION);
        mf.setContentPane(mf.mainPanel);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);
        mf.pack();
    }

}
