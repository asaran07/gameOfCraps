import model.Dice;

import javax.swing.*;
import java.awt.*;


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

    public mainForm() {
        settingbutton.addActionListener(e -> switchPanel(settingsScreen));
        backbutton.addActionListener(e -> switchPanel(titleScreen));
        newGameButton.addActionListener(e -> switchPanel(setBankScreen));
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
        mf.setTitle("TGOS 0.3.2");
        mf.setContentPane(mf.mainPanel);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);

    }

}
