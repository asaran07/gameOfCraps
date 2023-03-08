import model.Die;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class mainForm extends JFrame {
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
    private JButton rollButton;
    private JButton backButtonGMS;
    private JTextField die1sideField;
    private JTextField die2sideField;
    private JTextField currentCashField;
    private JTextField totalDiceRollField;
    private JPanel setBankScreen;
    private JTextField startingCashField;
    private JButton continueButtonST;
    private JLabel startingCashLabel;
    private JLabel die1img;
    private JLabel die2img;
    private JButton rollDiceButton;
    private JLabel startButton;
    private JLabel rulesButton;
    private JLabel exitButton;
    private final Die dieA = new Die();
    private final Die dieB = new Die();

    public ImageIcon icon = new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnHover.png");
    public ImageIcon icon2 = new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtnHoverNoUline.png");
    public ImageIcon icon3 = new ImageIcon("src/ui_resources/buttons/newGameButton/newGmBtn.png");


    Timer timer;

    public mainForm() {



        rollDiceButton.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDiceButton.setText(rollDiceButton.getText());
                rollDiceButton.setRolloverEnabled(false);
                timer = new Timer(400, this);
                timer.setRepeats(false);
                timer.start();
                if (i == 4) {
                    i = 0;
                }
                if (i == 0) {
                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die3.png"));
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die4.png"));
                    rollDiceButton.setEnabled(false);
                }
                if (i == 1) {
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die3.png"));
                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die2.png"));

                }
                if (i == 2) {
                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die3.png"));
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die2.png"));

                }
                if (i == 3) {
                    rollDiceButton.setRolloverEnabled(true);
                    die1img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die1.png"));
                    die2img.setIcon(new ImageIcon("src/ui_resources/dieIcons/die1.png"));
                    rollDiceButton.setIcon(new ImageIcon("src/ui_resources/buttons/rollDiceButton/rollDiceBtn.png"));
                    timer.stop();
                    rollDiceButton.setEnabled(true);
                }
                i++;
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

        continueButtonST.addActionListener(new ActionListener() {
            int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
//                currentCashField.setText(startingCashField.getText());
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
                    newGameButton.setIcon(icon);
                }
                if (i == 1) {
                    newGameButton.setIcon(icon2);
                }
                if (i == 2) {
                    newGameButton.setIcon(icon);
                }
                if (i == 3) {
                    newGameButton.setRolloverEnabled(true);
                    newGameButton.setIcon(icon3);
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
        mf.setTitle("TGOS 0.7.2");
        mf.setContentPane(mf.mainPanel);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);
    }

}
