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
    private boolean blinked = false;
    private int startingCash = 0;
    java.util.Timer PYBTimer = new java.util.Timer();

    Timer timer;
    Timer infoTimer;
    Timer gameTimer;

    public mainForm() {
        disableBetting();
        versionLabel.setText(VERSION);
        setInfoViewTo("RDTB1");
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDiceButton.setEnabled(false);
                rollDiceButton.setRolloverEnabled(false);
                disableBetting();

                if (firstStart) {
                    PYBTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("1");
                            infoScreenBusy = true;
                            randomizeDiceIcons();
                            setInfoViewTo("RD1");
                        }
                    }, 0);
                    PYBTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("2");
                            infoScreenBusy = true;
                            randomizeDiceIcons();
                            setInfoViewTo("RD2");
                        }
                    }, 1000);
                    PYBTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("3");
                            infoScreenBusy = true;
                            randomizeDiceIcons();
                            setInfoViewTo("RD3");
                        }
                    }, 2000);
                    PYBTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            diceRolled = true;
                            timer.stop();
                            rollBothDice();
                            processCurrentState();
                        }
                    }, 3000);
                }


                else {
                    setInfoViewTo("LG1");
                    PYBTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            rollDiceButton.setRolloverEnabled(true);
                            setInfoViewTo("PYB");
                            firstStart = true;
                            betAmountField.setEditable(true);
                            timer.stop();
                        }
                    }, 3000);
                }
            }
        });

        betAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input;
                boolean alphaDetected = false;
                if (!betAmountField.getText().isBlank()
                        || !betAmountField.getText().isEmpty()) {
                    input = betAmountField.getText();
                    for (int i = 0; i < input.length(); i++) {
                        if (Character.isAlphabetic(input.charAt(i))) {
                            alphaDetected = true;
                        }
                    }
                    placeBetButton.setEnabled(!alphaDetected);
                }
                else {
                    placeBetButton.setEnabled(false);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                String input;
                boolean alphaDetected = false;
                if (!betAmountField.getText().isBlank()
                        || !betAmountField.getText().isEmpty()) {
                    input = betAmountField.getText();
                    for (int i = 0; i < input.length(); i++) {
                        if (Character.isAlphabetic(input.charAt(i))) {
                            alphaDetected = true;
                        }
                    }
                    placeBetButton.setEnabled(!alphaDetected);
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
                    betAmountField.setText("");
                    rollDiceButton.setEnabled(true);
                    infoTimer.stop();
                    setInfoViewTo("WFR1");
                }

                if (!betPlaced) {
                    rollDiceButton.setEnabled(false);
                    player.setCurrentBet(Integer.parseInt(betAmountField.getText()));
                    player.setCurrentCash(player.getCurrentCash() - player.getCurrentBet());
                    betAmountField.setEditable(false);
                    placeBetButton.setEnabled(false);
                    setInfoViewTo("BP1");
                    currentBetField.setText(String.valueOf(player.getCurrentBet()));
                    cashField.setText(String.valueOf(player.getCurrentCash()));
                    betPlaced = true;
                    betAmountField.setText("good luck");
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
//                PYBTimer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        System.out.println("r1");
//                        setInfoViewTo("RDTB1");
//                    }
//                }, 3000, 3000);
//                PYBTimer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        System.out.println("r2");
//                        setInfoViewTo("Blank");
//                    }
//                }, 5000, 3000);

                player.setCurrentCash(Integer.parseInt(startingCashField.getText()));
                startingCash = Integer.parseInt(startingCashField.getText());
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

    private void checkForPointRoll() {
        if (!pointTurn) {
            if (player.getCurrentRoll() == 4
                    || player.getCurrentRoll() == 5
                    || player.getCurrentRoll() == 6
                    || player.getCurrentRoll() == 8
                    || player.getCurrentRoll() == 9
                    || player.getCurrentRoll() == 10) {
                processPointRoll();
                syncPlayerWithCurrentPoint();
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
        setInfoViewTo("WON");
        System.out.println("won due to 7/11");
        player.setCurrentCash(player.getCurrentCash() + (player.getCurrentBet() * 2));
        cashField.setText(String.valueOf(player.getCurrentCash()));
        syncLossProfitField();
        currentBetField.setText("");
        pointField.setText("");
    }

    private void processRoundLost() {
        setInfoViewTo("Lost");
        System.out.println("lost due to 2/3/12");
        cashField.setText(String.valueOf(player.getCurrentCash()));
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
        syncDieIconsWithCurrentRoll();
        resetRollDiceButton();
        checkForPointRoll();
        if (!pointTurn) {
            PYBTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    betPlaced = false;
                    betAmountField.setEditable(true);
                    currentBetField.setText("");
                    currentRollField.setText("");
                    setInfoViewTo("PYB");
                }
            }, 3000);
        }
        else {
            rollDiceButton.setEnabled(true);
            setInfoViewTo("WFRP");
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

    private void syncPlayerWithCurrentPoint() {
        player.setMyPoint(player.getCurrentRoll());
        pointField.setText(String.valueOf(player.getMyPoint()));
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
