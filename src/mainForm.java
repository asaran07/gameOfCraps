import model.Die;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class mainForm extends JFrame {

    private static final String VERSION = "0.9.6";
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
    private JLabel die1img;
    private JLabel die2img;
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
    private Player player = new Player();
    private boolean firstDiceRolled = false;
    private boolean infoScreenBusy = false;
    private boolean firstStart = false;
    private boolean betPlaced = false;
    private boolean pointTurn = false;
    private boolean wonOrLost = false;
    private boolean diceRolled = false;
    private boolean calculationBusy = false;

    Timer timer;
    Timer infoTimer;
    Timer gameTimer;

    public mainForm() {

        versionLabel.setText(VERSION);
        betAmountField.setEditable(false);
        continueButtonST.setEnabled(false);
        placeBetButton.setEnabled(false);

        infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelRDTB1.png"));

        ActionListener diceRolledListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("heard roll");

                gameTimer = new Timer(2000, this);
                gameTimer.setRepeats(false);

                if (diceRolled) {
                    System.out.println("gameTimer start");
                    gameTimer.start();
                }

                calculationBusy = true;

                if (wonOrLost) {
                    infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelPYB.png"));
                    wonOrLost = false;
                }

                if (diceRolled) {
                    if (!pointTurn) {
                        rollDiceButton.setEnabled(false);
                    }

                    if (pointTurn) {
                        System.out.println("running point turn");
                        pointField.setText(String.valueOf(player.getMyPoint()));
                        if (!infoScreenBusy) {
                            infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelWFRP.png"));
                        }

                        if (player.getCurrentRoll() == 7) {
                            System.out.println("point turn lost");
                            player.setLose(true);
                            pointTurn = false;
                            betAmountField.setEditable(true);
                        }

                        else if (player.getCurrentRoll() == player.getMyPoint()) {
                            System.out.println("point turn won");
                            player.setWin(true);
                            pointTurn = false;
                            betAmountField.setEditable(true);
                        }
                    }

                    else {
                        if (player.getCurrentRoll() == 7
                                || player.getCurrentRoll() == 11) {
                            System.out.println("won due to 7/11");
                            player.setWin(true);
                            player.setCurrentRoll(0);
                            infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelWON.png"));
                        }

                        if (player.getCurrentRoll() == 2
                                || player.getCurrentRoll() == 3
                                || player.getCurrentRoll() == 12) {
                            System.out.println("lost due to 2/3/12");
                            player.setLose(true);
                            player.setCurrentRoll(0);
                            infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelLost.png"));
                        }
                    }

                    if (player.isLose()) {
                        System.out.println("running lose function");
                        wonOrLost = true;
                        player.setCurrentCash(player.getCurrentCash() - player.getCurrentBet());
                        cashField.setText(String.valueOf(player.getCurrentCash()));
                        player.setLose(false);
                        currentBetField.setText("");
                        diceRolled = false;
                        System.out.println("gameTimer stop");
                        gameTimer.stop();
                    } else if (player.isWin()) {
                        System.out.println("running won function");
                        wonOrLost = true;
                        player.setCurrentCash(player.getCurrentCash() + (player.getCurrentBet() * 2));
                        cashField.setText(String.valueOf(player.getCurrentCash()));
                        player.setWin(false);
                        currentBetField.setText("");
                        diceRolled = false;
                        System.out.println("gameTimer stop");
                        gameTimer.stop();
                    }

                }

                calculationBusy = false;
            }
        };

        rollDiceButton.addActionListener(diceRolledListener);

        rollDiceButton.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                timer = new Timer(400, this);
                timer.setRepeats(false);
                timer.start();

                if (i == 4) {
                    i = 0;
                }

                firstStart = true;
                rollDiceButton.setRolloverEnabled(false);

                disableBetting();

                if (firstDiceRolled) {
                    if (i == 0) {
                        infoScreenBusy = true;
                        randomizeDiceIcons();
                        setInfoViewTo("RD1");
                    }

                    if (i == 1) {
                        randomizeDiceIcons();
                        setInfoViewTo("RD2");
                    }

                    if (i == 2) {
                        randomizeDiceIcons();
                        setInfoViewTo("RD3");
                    }

                    if (i == 3) {
                        processGameAfterRollClicked();
                        timer.stop();
                    }
                    else {
                        betAmountField.setEditable(true);
                    }
                }
                else {
                    setInfoViewTo("LG1");
                }
                i++;
            }
        });

        betAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!betAmountField.getText().isBlank()
                        || !betAmountField.getText().isEmpty()) {
                    placeBetButton.setEnabled(true);
                }
                else {
                    placeBetButton.setEnabled(false);
                }
            }
        });

        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoTimer = new Timer(2000, this);
                infoTimer.setRepeats(false);
                infoTimer.start();

                if (betPlaced) {
                    infoTimer.stop();
                    infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelWFR1.png"));
                }

                if (!betPlaced) {
                    player.setCurrentBet(Integer.parseInt(betAmountField.getText()));
                    player.setCurrentCash(player.getCurrentCash() - player.getCurrentBet());
                    betAmountField.setEditable(false);
                    placeBetButton.setEnabled(false);

                    infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelBP1.png"));
                    currentBetField.setText(String.valueOf(player.getCurrentBet()));
                    cashField.setText(String.valueOf(player.getCurrentCash()));
                    betPlaced = true;
                    betAmountField.setText("");
                    if (!pointTurn) {
                        rollDiceButton.setEnabled(true);
                    }
                }
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
                if (!startingCashField.getText().isBlank()
                || !startingCashField.getText().isEmpty()) {
                    continueButtonST.setEnabled(true);
                }
                else {
                    continueButtonST.setEnabled(false);
                }
            }
        });


        continueButtonST.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setCurrentCash(Integer.parseInt(startingCashField.getText()));
                cashField.setText(String.valueOf(player.getCurrentCash()));
                continueButtonST.setRolloverEnabled(false);
                timer = new Timer(90, this);
                timer.setRepeats(false);
                timer.start();

                if (i == 4) {
                    i = 0;
                }
                if (i == 0) {
                    continueButtonST.setIcon(new ImageIcon("src\\ui_resources\\buttons\\continueButton\\continueBtnHover.png"));
                }
                if (i == 1) {
                    continueButtonST.setIcon(new ImageIcon("src\\ui_resources\\buttons\\continueButton\\continueBtnHoverNoUline.png"));
                }
                if (i == 2) {
                    continueButtonST.setIcon(new ImageIcon("src\\ui_resources\\buttons\\continueButton\\continueBtnHover.png"));
                }
                if (i == 3) {
                    continueButtonST.setRolloverEnabled(true);
                    continueButtonST.setIcon(new ImageIcon("src\\ui_resources\\buttons\\continueButton\\continueBtn.png"));
                    switchPanel(gameScreen);
                    timer.stop();
                }
                i++;
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
                    settingbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\settingsButton\\settingsBtnHover.png"));
                }
                if (i == 1) {
                    settingbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\settingsButton\\settingsBtnHoverNoUline.png"));
                }
                if (i == 2) {
                    settingbutton.setIcon(new ImageIcon("src\\ui_resources\\buttons\\settingsButton\\settingsBtnHover.png"));
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
                newGameButton.setRolloverEnabled(false);
                timer = new Timer(90, this);
                timer.setRepeats(false);
                timer.start();
                if (i == 4) {
                    i = 0;
                }
                if (i == 0) {
                    newGameButton.setIcon(new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnHover.png"));
                }
                if (i == 1) {
                    newGameButton.setIcon(new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnHoverNoUline.png"));
                }
                if (i == 2) {
                    newGameButton.setIcon(new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnHover.png"));
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

    private void checkRoll() {
        if (!pointTurn) {
            if (player.getCurrentRoll() == 4
                    || player.getCurrentRoll() == 5
                    || player.getCurrentRoll() == 6
                    || player.getCurrentRoll() == 8
                    || player.getCurrentRoll() == 9
                    || player.getCurrentRoll() == 10) {
                System.out.println("rolled for point turn");
                pointTurn = true;
                placeBetButton.setEnabled(false);
                rollDiceButton.setEnabled(true);
                betAmountField.setEditable(false);
                player.setMyPoint(player.getCurrentRoll());
                pointField.setText(String.valueOf(player.getMyPoint()));
                infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelWFRP.png"));
            }
            else {

            }
        }
        else {
            rollDiceButton.setEnabled(true);
        }
    }

    private void processGameAfterRollClicked() {
        diceRolled = true;
        rollBothDice();
        syncPlayerWithCurrentRoll();
        syncDieIconsWithCurrentRoll();
        checkRoll();
        if (!pointTurn) {
            enableBetting();
        }
        betPlaced = false;
        firstDiceRolled = true;
        rollDiceButton.setRolloverEnabled(true);
        rollDiceButton.setIcon(new ImageIcon("src/ui_resources/buttons/rollDiceButton/rollDiceBtn.png"));
        if (!pointTurn && !calculationBusy) {
            infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelPYB.png"));
        }
        System.out.println("finished rolling");
    }

    private void syncPlayerWithCurrentRoll() {
        player.setCurrentRoll(dieA.diceTotal(dieB));
        currentRollField.setText(String.valueOf(player.getCurrentRoll()));
    }

    private void randomizeDiceIcons() {
        int randomA = rand.nextInt(6 - 1 + 1) + 1;
        int randomB = rand.nextInt(6 - 1 + 1) + 1;
        die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die" + randomA + ".png"));
        die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die" + randomB + ".png"));
    }

    private void setInfoViewTo(String infoViewState) {
        infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabel"
                + infoViewState + ".png"));
    }

    private void disableBetting() {
        placeBetButton.setEnabled(false);
        betAmountField.setEditable(false);
    }

    private void enableBetting() {
        placeBetButton.setEnabled(true);
        betAmountField.setEditable(true);
    }

    private void syncDieIconsWithCurrentRoll() {
        die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die"
                + dieA.getMySide() + ".png"));
        die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die"
                + dieB.getMySide() + ".png"));
    }

    private void rollBothDice() {
        dieA.rollDice();
        dieB.rollDice();
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
