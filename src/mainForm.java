import model.Die;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.TimerTask;


public class mainForm extends JFrame {

    private static final String VERSION = "0.10.3";
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
    private JLabel infoView;
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
    private final Die dieA = new Die();
    private final Die dieB = new Die();
    private final Player player = new Player();
    private final boolean firstDiceRolled = false;
    private final boolean infoScreenBusy = false;
    private boolean firstStart = false;
    private boolean betPlaced = false;
    private boolean pointTurn = false;
    private final boolean wonOrLost = false;
    private final boolean diceRolled = false;
    private final boolean calculationBusy = false;
    private final boolean blinked = false;
    private int startingCash = 0;
    java.util.Timer mainTimer = new java.util.Timer();


    Timer timer;
    Timer infoTimer;
    Timer gameTimer;

    public mainForm() {
        versionLabel.setText(VERSION);
        setTexture(infoView, "infoScreenLabel", "RDTB1", "infoScreen");
        rollDiceButton.addActionListener(e -> {
            disableRollDiceButton();
            disableBetting();
            if (firstStart) {
                rollBothDice(3, 500);
            }
            else {
                setTexture(infoView, "infoScreenLabel",
                        "LG", "infoScreen", 3, 1000);
                runFirstStart();
            }
        });

        betAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                placeBetButton.setEnabled(isBlank(betAmountField)
                        && isAlpha(betAmountField));
            }
            @Override
            public void keyTyped(KeyEvent e) {
                placeBetButton.setEnabled(isBlank(betAmountField)
                        && isAlpha(betAmountField));
            }
        });

        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                betPlaced = true;
                disableBetting();
                syncCashWithBet();
                currentBetField.setText(String.valueOf(player.getCurrentBet()));
                betAmountField.setText("good luck");
                setTexture(infoView, "infoScreenLabel",
                        "BP1", "infoScreen");
                readyNormalTurn();
            }
        });

        backbutton.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                backbutton.setText(backbutton.getText());
                backbutton.setRolloverEnabled(false);
                timer = new Timer(90, this);
                timer.setRepeats(false);
                timer.start();
                if (i == 4) {
                    i = 0;
                }
                if (i == 0) {
                    backbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\backButton\\backBtnHover.png"));
                }
                if (i == 1) {
                    backbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\backButton\\backBtnHoverNoUline.png"));
                }
                if (i == 2) {
                    backbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\backButton\\backBtnHover.png"));
                }
                if (i == 3) {
                    backbutton.setRolloverEnabled(true);
                    backbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\backButton\\backBtn.png"));
                    switchPanel(titleScreen);
                    timer.stop();
                }
                i++;
            }
        });

        startingCashField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                continueButtonST.setEnabled(isAlpha(startingCashField)
                        && isBlank(startingCashField));
            }
        });

        continueButtonST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableBetting();
                player.setCurrentCash(Integer.parseInt(startingCashField.getText()));
                startingCash = Integer.parseInt(startingCashField.getText());
                cashField.setText(String.valueOf(player.getCurrentCash()));
                continueButtonST.setRolloverEnabled(false);
                setTexture(continueButtonST, "continueBtn", "Clicked",
                        "continueButton", 2, 90);
                mainTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        continueButtonST.setRolloverEnabled(true);
                        continueButtonST.setIcon(new ImageIcon("src\\ui_resources\\buttons\\continueButton\\continueBtn.png"));
                        switchPanel(gameScreen);
                    }
                }, 180);
            }
        });

        settingbutton.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                settingbutton.setRolloverEnabled(false);
                timer = new Timer(90, this);
                timer.setRepeats(false);
                timer.start();
                if (i == 4) {
                    i = 0;
                }
                if (i == 0) {
                    settingbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\settingsButton\\settingsBtnRollover.png"));
                }
                if (i == 1) {
                    settingbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\settingsButton\\settingsBtnClicked2.png"));
                }
                if (i == 2) {
                    settingbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\settingsButton\\settingsBtnRollover.png"));
                }
                if (i == 3) {
                    settingbutton.setRolloverEnabled(true);
                    settingbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\settingsButton\\settingsBtn.png"));
                    switchPanel(settingsScreen);
                    timer.stop();
                }
                i++;
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                continueButtonST.setEnabled(false);
                newGameButton.setRolloverEnabled(false);
                timer = new Timer(90, this);
                timer.setRepeats(false);
                timer.start();
                if (i == 4) {
                    i = 0;
                }
                if (i == 0) {
                    newGameButton.setIcon(new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnClicked1.png"));
                }
                if (i == 1) {
                    newGameButton.setIcon(new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnClicked2.png"));
                }
                if (i == 2) {
                    newGameButton.setIcon(new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnClicked1.png"));
                }
                if (i == 3) {
                    newGameButton.setRolloverEnabled(true);
                    newGameButton.setIcon(new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtn.png"));
                    switchPanel(setBankScreen);
                    timer.stop();
                }
                i++;
            }
        });
    }

    private void readyNormalTurn() {
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                rollDiceButton.setEnabled(true);
                betAmountField.setText("");
                setTexture(infoView, "infoScreenLabel",
                        "WFR1", "infoScreen");
            }
        }, 2000);
    }

    private void runFirstStart() {
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                setTexture(infoView, "infoScreenLabel", "PYB", "infoScreen");
                firstStart = true;
                betAmountField.setEditable(true);
                rollDiceButton.setRolloverEnabled(true);
            }
        }, 3000);
    }

    private void syncCashWithBet() {
        player.setCurrentBet(Integer.parseInt(betAmountField.getText()));
        player.setCurrentCash(player.getCurrentCash() - player.getCurrentBet());
        cashField.setText(String.valueOf(player.getCurrentCash()));
    }

    private void setTexture(final JComponent theComponent, final String theComponentName, final String theComponentTexture,
                            final String theFolderName, final int theTextureStates, final int theDelay) {
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
                        setTexture(theComponent, theComponentName,
                                theComponentTexture, theFolderName);
                    } else {
                        setTexture(theComponent, theComponentName,
                                theComponentTexture + finalAppliedStates, theFolderName);
                    }
                }
            },theDelay * appliedStates);
            appliedStates++;
        }
    }

    private void setTexture(JComponent theComponent, String theComponentName, String theComponentTexture, String theFolderName) {
        if (theComponent instanceof JButton jButton) {
            jButton.setIcon(new ImageIcon("src/ui_resources/buttons/" + theFolderName
                    + "/" + theComponentName + theComponentTexture + ".png"));
        }
        else if (theComponent instanceof JLabel jLabel) {
            jLabel.setIcon(new ImageIcon("src/ui_resources/labels/" + theFolderName
                    + "/" + theComponentName + theComponentTexture + ".png"));
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
        setTexture(infoView, "infoScreenLabel", "WON", "infoScreen");
        System.out.println("won due to 7/11");
        player.setCurrentCash(player.getCurrentCash() + (player.getCurrentBet() * 2));
        cashField.setText(String.valueOf(player.getCurrentCash()));
        syncLossProfitField();
        currentBetField.setText("");
        pointField.setText("");
    }

    private void processRoundLost() {
        setTexture(infoView, "infoScreenLabel", "Lost", "infoScreen");
        System.out.println("lost due to 2/3/12");
        syncLossProfitField();
        currentBetField.setText("");
        pointField.setText("");
    }

    private void syncLossProfitField() {
        lossProfitField.setText(String.valueOf((player.getCurrentCash()) - startingCash));
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
        if (!pointTurn) {
            mainTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    betPlaced = false;
                    System.out.println("true field");
                    betAmountField.setEditable(true);
                    currentBetField.setText("");
                    currentRollField.setText("");
                    setTexture(infoView, "infoScreenLabel", "PYB", "infoScreen");
                }
            }, 3000);
        }
        else {
            rollDiceButton.setEnabled(true);
            setTexture(infoView, "infoScreenLabel", "WFRP", "infoScreen");
        }
        System.out.println("finished rolling");
    }

    private void resetRollDiceButton() {
        rollDiceButton.setRolloverEnabled(true);
        rollDiceButton.setIcon(new ImageIcon("src/ui_resources/buttons/rollDiceButton/rollDiceBtn.png"));
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
            setTexture(die1, "die", "Side" + randomA,
                    "dieIcons", 1, theDelay * i);
            setTexture(die2, "die", "Side" + randomB,
                    "dieIcons", 1, theDelay * i);
        }
    }

    private void disableBetting() {
        placeBetButton.setEnabled(false);
        betAmountField.setEditable(false);
    }

    private void syncDiceIconsWithRoll() {
        setTexture(die1, "die", "Side" + dieA.getMySide(),
                "dieIcons");
        setTexture(die2, "die", "Side" + dieB.getMySide(),
                "dieIcons");
    }

    private void rollBothDice(final int theTimesToRoll, final int theDelay) {
        int delay = theTimesToRoll * theDelay;
        randomizeDiceIcons(theTimesToRoll, theDelay);
        setTexture(infoView, "infoScreenLabel",
                "RD", "infoScreen", theTimesToRoll, theDelay);
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dieA.rollDice();
                dieB.rollDice();
                setTexture(infoView, "infoScreenLabel",
                        "PYB", "infoScreen");
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
