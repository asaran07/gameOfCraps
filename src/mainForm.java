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
    private JLabel dieAnum;
    private JLabel dieBnum;
    private JButton rollButton;
    private JButton backButtonGMS;
    private final Dice diceA = new Dice();
    private final Dice diceB = new Dice();

    public mainForm() {
        settingbutton.addActionListener(e -> switchPanel(settingsScreen));
        backbutton.addActionListener(e -> switchPanel(titleScreen));
        newGameButton.addActionListener(e -> switchPanel(gameScreen));
        backButtonGMS.addActionListener(e -> switchPanel(titleScreen));
        rollButton.addActionListener(e -> {
            rollBothDice();
            dieAnum.setText(diceA.toString());
            dieBnum.setText(diceB.toString());
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
        int sHeight = screenSize.height/3;
        int sWidth = screenSize.width/3;
        Dimension screenDimension = new Dimension(sWidth,sHeight);
        mainForm mf = new mainForm();
        mf.setTitle("TGOS 0.2.0");
        mf.setContentPane(mf.mainPanel);
        mf.setSize(screenDimension);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.setLocationRelativeTo(null);

    }

}
