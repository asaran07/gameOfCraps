import model.Die;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class mainForm extends JFrame {

    private static final String VERSION = "0.8.9";

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
    private final Die dieA = new Die();
    private final Die dieB = new Die();
    private Player player = new Player();

    private boolean firstDiceRoll = false;
    private boolean infoScreenBusy = false;
    private boolean firstStart = false;
    private boolean betPlaced = false;
    private boolean pointTurn = false;

    Timer timer;
    Timer infoTimer;
    Timer gameTimer;


    public mainForm() {

        versionLabel.setText(VERSION);

        betAmountField.setEditable(false);
        continueButtonST.setEnabled(false);

         continueButtonST.addActionListener(new ActionListener() {
             int i = 0;

             @Override
             public void actionPerformed(ActionEvent e) {
                 infoTimer = new Timer(1000, this);
                 infoTimer.setRepeats(false);
                 infoTimer.start();
                 if (i == 3) {
                     i = 0;
                 }
                 if (!firstStart) {
                     if (i == 0) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelW1.png"));
                     }
                     if (i == 2) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelRDTB1.png"));
                     }
                 }
                 if (firstDiceRoll && !infoScreenBusy && !betPlaced) {
                     if (i == 0) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelPYB.png"));
                     }
                     if (i == 1) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelBlank.png"));
                     }
                     if (i == 2) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelPYB.png"));
                     }
                 }
                 if (betPlaced && !infoScreenBusy) {
                     if (i == 0) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelWFR1.png"));
                     }
                     if (i == 1) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelBlank.png"));
                     }
                     if (i == 2) {
                         infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelWFR1.png"));
                     }
                 }

                 i++;
             }
         });

        rollDiceButton.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {

                if (i == 4) {
                    i = 0;
                }

                if (firstStart) {

                    if (!pointTurn) {
                        if (Integer.parseInt(currentRollField.getText()) == 7
                                || Integer.parseInt(currentRollField.getText()) == 11) {
                            player.setWin(true);
                        }

                        if (Integer.parseInt(currentRollField.getText()) == 2
                                || Integer.parseInt(currentRollField.getText()) == 3
                                || Integer.parseInt(currentRollField.getText()) == 12) {
                            player.setLose(true);
                        }
                    }

                    if (Integer.parseInt(currentRollField.getText()) == 4
                            || Integer.parseInt(currentRollField.getText()) == 5
                            || Integer.parseInt(currentRollField.getText()) == 6
                            || Integer.parseInt(currentRollField.getText()) == 8
                            || Integer.parseInt(currentRollField.getText()) == 9
                            || Integer.parseInt(currentRollField.getText()) == 10) {
                        pointTurn = true;

                    }



                    if (player.isWin()) {
                        player.setCurrentCash(player.getCurrentCash() + player.getCurrentBet());
                        player.setWin(false);
                    }
                    if (player.isLose()) {
                        player.setCurrentCash(player.getCurrentCash() - player.getCurrentBet());
                    }
                }

                betPlaced = false;
                infoScreenBusy = true;
                firstDiceRoll = true;
                firstStart = true;

                placeBetButton.setEnabled(false);
                betAmountField.setEditable(false);

                dieA.rollDice();
                dieB.rollDice();

                currentRollField.setText(String.valueOf(dieA.getMySide() + dieB.getMySide()));


                rollDiceButton.setRolloverEnabled(false);

                timer = new Timer(400, this);
                timer.setRepeats(false);
                timer.start();

                if (i == 0) {
                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die3.png"));
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die4.png"));
                    infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelRD1.png"));
                    rollDiceButton.setEnabled(false);
                }

                if (i == 1) {
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die3.png"));
                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die2.png"));
                    infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelRD2.png"));
                }

                if (i == 2) {
                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die3.png"));
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die2.png"));
                    infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelRD3.png"));
                }

                if (i == 3) {
                    placeBetButton.setEnabled(true);
                    betAmountField.setEditable(true);
                    rollDiceButton.setRolloverEnabled(true);
                    infoScreenBusy = false;
                    rollDiceButton.setEnabled(true);
                    betAmountField.setEditable(true);
                    betAmountField.setEditable(true);

                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die1.png"));
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die1.png"));
                    rollDiceButton.setIcon(new ImageIcon("src/ui_resources/buttons/rollDiceButton/rollDiceBtn.png"));
                    infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelPYB.png"));

                    timer.stop();
                }

                i++;

            }
        });


        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setCurrentBet(Integer.parseInt(betAmountField.getText()));
                player.setCurrentCash(player.getCurrentCash() - player.getCurrentBet());
                betAmountField.setEditable(false);
                betAmountField.setEditable(false);
                infoView.setIcon(new ImageIcon("src/ui_resources/labels/infoScreenLabelBP1.png"));
                currentBetField.setText(String.valueOf(player.getCurrentBet()));
                cashField.setText(String.valueOf(player.getCurrentCash()));
                betPlaced = true;
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
