import model.Dice;

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
    private JLabel dieA;
    private JLabel dieB;
    private JButton rollButton;
    private JButton backButtonGMS;
    private JLabel bankLabel;
    private JLabel totalCashLabel;
    private JLabel tableLabel;
    private JTextField die1sideField;
    private JTextField die2sideField;
    private JTextField currentCashField;
    private JLabel totalOnDiceLabel;
    private JTextField totalDiceRollField;
    private JButton rulesButton;
    private JLabel myWinLable;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel setBankScreen;
    private JTextField startingCashField;
    private JButton continueButtonST;
    private JLabel startingCashLabel;
    private final Dice diceA = new Dice();
    private final Dice diceB = new Dice();

    public ImageIcon icon = new ImageIcon("C:\\Users\\Arsh\\Downloads\\gameOfScraps\\src\\ui_resources\\buttons\\newGameButton\\newGmBtnHover.png");
    public ImageIcon icon2 = new ImageIcon("C:\\Users\\Arsh\\Downloads\\gameOfScraps\\src\\ui_resources\\buttons\\newGameButton\\newGmBtnHoverNoUline.png");
    public ImageIcon icon3 = new ImageIcon("C:\\Users\\Arsh\\Downloads\\gameOfScraps\\src\\ui_resources\\buttons\\newGameButton\\newGmBtn.png");


    Timer timer;

    public mainForm() {
        backbutton.addActionListener(e -> switchPanel(titleScreen));
        backButtonGMS.addActionListener(e -> switchPanel(titleScreen));
        continueButtonST.addActionListener(e -> {
            currentCashField.setText(startingCashField.getText());
            switchPanel(gameScreen);
        });
        rollButton.addActionListener(e -> {
            rollBothDice();
            die1sideField.setText(diceA.toString());
            die2sideField.setText(diceB.toString());
            totalDiceRollField.setText(String.valueOf(diceA.getMySide() + diceB.getMySide()));
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
                    settingbutton.setIcon(new ImageIcon("C:\\Users\\Arsh\\Downloads\\gameOfScraps\\src\\ui_resources\\buttons\\settingsButton\\settingsBtnHover.png"));
                }
                if (i == 1) {
                    settingbutton.setIcon(new ImageIcon("C:\\Users\\Arsh\\Downloads\\gameOfScraps\\src\\ui_resources\\buttons\\settingsButton\\settingsBtnHoverNoUline.png"));
                }
                if (i == 2) {
                    settingbutton.setIcon(new ImageIcon("C:\\Users\\Arsh\\Downloads\\gameOfScraps\\src\\ui_resources\\buttons\\settingsButton\\settingsBtnHover.png"));
                }
                if (i == 3) {
                    settingbutton.setRolloverEnabled(true);
                    settingbutton.setIcon(new ImageIcon("C:\\Users\\Arsh\\Downloads\\gameOfScraps\\src\\ui_resources\\buttons\\settingsButton\\settingsBtn.png"));
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
        diceA.rollDice();
        diceB.rollDice();
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
        mf.setTitle("TGOS 0.5.0");
        mf.setContentPane(mf.mainPanel);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);

    }

}
